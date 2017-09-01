package com.andrew.alarmclock.data.repository.sharedPreferences;

import io.reactivex.Single;

public interface ISharedPreferencesRepository {
    Single<Object> setVibrationPreference(boolean isVibrate);
    Single<Boolean> getVibrationPreference();

    Single<Object> setFirstLaunchPreference(boolean isFirstLaunch);
    Single<Boolean> getFirstLaunchPreference();

    Single<Boolean> getShouldDisturb();
    Single<Object> setShouldDisturb(boolean shouldDisturb);
    Single<Boolean> getOppositeShouldDisturb();

    Single<String> getLastAlarmTime();
    Single<Object> setLastAlarmTime(String lastAlarmTime);

    Single<Boolean> isDarkTheme();
    Single<Object> setDarkTheme(boolean isDarkTheme);
}
