package com.andrew.alarmclock.alarm.alarmClock.presentation;

import com.andrew.alarmclock.alarm.alarmClock.business.IAlarmClockInteractor;
import com.andrew.alarmclock.di.InjectHolder;
import com.andrew.alarmclock.base.BasePresenter;
import com.andrew.alarmclock.data.entities.Alarm;
import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class AlarmClockPresenter extends BasePresenter<AlarmClockView> {

    @Inject
    IAlarmClockInteractor interactor;

    public AlarmClockPresenter() {
        InjectHolder.getInstance().buildPagerComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        getAlarms();
    }

    public void onInsertAlarm(int hours, int minutes) {
        interactor.insertAlarm(hours, minutes)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(value -> getViewState().onAlarmCreated(value));
    }

    public void onUpdateAlarm(Alarm alarm) {
        interactor.updateAlarm(alarm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(value -> getViewState().onAlarmUpdated(value));
    }

    public void onDeleteAlarm(Alarm alarm) {
        interactor.deleteAlarm(alarm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(value -> getViewState().onAlarmRemoved(value));
    }

    public void onSetupFabDisturb() {
        interactor.getShouldDisturb()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(val -> getViewState().setFabDisturb(val));
    }

    public void onFabDisturbClick() {
        interactor.getOppositeShouldDisturb()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(val -> {
                    getViewState().setFabDisturb(val);
                    interactor.updateNotification(val)
                            .subscribeOn(Schedulers.io())
                            .subscribe();
                });
    }

    private void getAlarms() {
        interactor.getAllAlarms()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(value -> getViewState().onAlarmsGot(value));
    }
}
