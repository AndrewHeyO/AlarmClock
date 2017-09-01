package com.andrew.alarmclock.data.sourse.sharedPreferences;

import android.content.SharedPreferences;

import com.andrew.alarmclock.utils.Constant;

import javax.inject.Inject;

import static com.andrew.alarmclock.utils.Constant.TAG_VIBRATION;


public class SharedPreferencesSource implements ISharedPreferencesSource {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Inject
    public SharedPreferencesSource(SharedPreferences sharedPreferences,
                                   SharedPreferences.Editor editor) {
        this.sharedPreferences = sharedPreferences;
        this.editor = editor;
    }

    @Override
    public void setVibrationPreference(boolean isVibrate) {
        editor.putBoolean(TAG_VIBRATION, isVibrate);
        editor.apply();
    }

    @Override
    public boolean getVibrationPreference() {
        return sharedPreferences.getBoolean(TAG_VIBRATION, false);
    }

    @Override
    public void setFirstLaunchPreference(boolean isFirstLaunch) {
        editor.putBoolean(Constant.TAG_FIRST_LAUNCH, isFirstLaunch);
        editor.apply();
    }

    @Override
    public boolean getFirstLaunchPreference() {
        return sharedPreferences.getBoolean(Constant.TAG_FIRST_LAUNCH, true);
    }

    @Override
    public boolean getShouldDisturb() {
        return sharedPreferences.getBoolean(Constant.TAG_SHOULD_DISTURB, true);
    }

    @Override
    public void setShouldDisturb(boolean shouldDisturb) {
        editor.putBoolean(Constant.TAG_SHOULD_DISTURB, shouldDisturb);
        editor.apply();
    }

    @Override
    public String getLastAlarmTime() {
        return sharedPreferences.getString(Constant.TAG_LAST_ALARM_TIME, "");
    }

    @Override
    public void setLastAlarmTime(String lastAlarmTime) {
        editor.putString(Constant.TAG_LAST_ALARM_TIME, lastAlarmTime);
        editor.apply();
    }

    @Override
    public boolean isDarkTheme() {
        return sharedPreferences.getBoolean(Constant.TAG_DARK_THEME, false);
    }

    @Override
    public void setDarkTheme(boolean isDarkTheme) {
        editor.putBoolean(Constant.TAG_DARK_THEME, isDarkTheme);
        editor.apply();
    }

    @Override
    public boolean isDeleted() {
        return sharedPreferences.getBoolean(Constant.TAG_IS_DELETED_NOTIFICATION, false);
    }

    @Override
    public void setDeleted(boolean isDeleted) {
        editor.putBoolean(Constant.TAG_IS_DELETED_NOTIFICATION, isDeleted);
        editor.apply();
    }
}
