package com.andrew.alarmclock.settings.presentation;

import com.andrew.alarmclock.base.BasePresenter;
import com.andrew.alarmclock.di.InjectHolder;
import com.andrew.alarmclock.settings.business.ISettingsInteractor;
import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class SettingsPresenter extends BasePresenter<SettingsView> {

    @Inject
    ISettingsInteractor interactor;

    public SettingsPresenter() {
        InjectHolder.getInstance().buildPagerComponent().inject(this);
    }

    @Override
    public void attachView(SettingsView view) {
        super.attachView(view);
        onGetVibration();
    }

    public void onWriteVibrationToPreferences(boolean isVibrate) {
        interactor.setVibrationPreference(isVibrate)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void onWriteDarkThemeToPreferences(boolean isDarkTheme) {
        interactor.setDarkTheme(isDarkTheme)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void onGetVibration() {
        interactor.getVibration()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(isChecked -> getViewState().setVibrationCheckBox(isChecked));
    }
}
