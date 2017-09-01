package com.andrew.alarmclock.alarm.alarmWake.presentation;

import com.arellomobile.mvp.MvpView;

public interface AlarmWakeView extends MvpView {
    void onShowTime(String time);
    void onFinishAfterAddingFiveMinutes();
    void handleVibration(boolean isVibrate);
}
