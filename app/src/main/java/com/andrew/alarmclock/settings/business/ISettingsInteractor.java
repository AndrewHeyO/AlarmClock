package com.andrew.alarmclock.settings.business;

import io.reactivex.Single;

public interface ISettingsInteractor {
    Single<Object> setVibrationPreference(boolean isVibrate);
    Single<Boolean> getVibration();

    Single<Boolean> isDarkTheme();
    Single<Object> setDarkTheme(boolean isDarkTheme);
}
