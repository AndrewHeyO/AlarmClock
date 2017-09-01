package com.andrew.alarmclock.settings.presentation;

import com.arellomobile.mvp.MvpView;

public interface SettingsView extends MvpView {
    void setVibrationCheckBox(boolean isChecked);
}
