package com.andrew.alarmclock.alarm.alarmWake.presentation;

import com.andrew.alarmclock.alarm.alarmWake.business.IAlarmWakeInteractor;
import com.andrew.alarmclock.base.BasePresenter;
import com.andrew.alarmclock.data.entities.Alarm;
import com.andrew.alarmclock.di.InjectHolder;
import com.arellomobile.mvp.InjectViewState;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class AlarmWakePresenter extends BasePresenter<AlarmWakeView> {

    private static final int MINUTES = 5;

    @Inject
    IAlarmWakeInteractor interactor;

    private Alarm alarm;

    public AlarmWakePresenter(int id) {
        InjectHolder.getInstance().buildWakeComponent().inject(this);

        interactor.getAlarmById(id)
                .subscribeOn(Schedulers.io())
                .subscribe(value -> alarm = value);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        onGetVibrationStatus();
        onSetMaxVolume();
        onWake();
    }

    @Override
    public void attachView(AlarmWakeView view) {
        super.attachView(view);
        updateTime();
        addDisposables(Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> updateTime()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        InjectHolder.getInstance().clearWakeComponent();
    }

    public void onFiveMinutesClick() {
        interactor.updateAlarm(alarm.addMinutes(MINUTES))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> getViewState().onFinishAfterAddingFiveMinutes());
    }

    private void onGetVibrationStatus() {
        interactor.getVibrationPreference()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> getViewState().handleVibration(value));
    }

    private void onWake() {
        interactor.updateAlarm(alarm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void onSetMaxVolume() {
        interactor.setMaxVolume()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void updateTime() {
        Calendar c = Calendar.getInstance();
        getViewState().onShowTime(String.format(Locale.getDefault(),
                "%02d:%02d",
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE)));
    }
}
