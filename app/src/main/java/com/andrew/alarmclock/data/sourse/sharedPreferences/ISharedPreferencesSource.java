package com.andrew.alarmclock.data.sourse.sharedPreferences;

public interface ISharedPreferencesSource {
    void setVibrationPreference(boolean isVibrate);
    boolean getVibrationPreference();

    void setFirstLaunchPreference(boolean isFirstLaunch);
    boolean getFirstLaunchPreference();

    boolean getShouldDisturb();
    void setShouldDisturb(boolean shouldDisturb);

    String getLastAlarmTime();
    void setLastAlarmTime(String lastAlarmTime);

    boolean isDarkTheme();
    void setDarkTheme(boolean isDarkTheme);

    boolean isDeleted();

    void setDeleted(boolean isDeleted);
}
