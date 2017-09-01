package com.andrew.alarmclock.alarm.alarmClock.presentation;

import com.andrew.alarmclock.data.entities.Alarm;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

public interface AlarmClockView extends MvpView {

    @StateStrategyType(value = SkipStrategy.class)
    void onAlarmCreated(Alarm alarm);
    @StateStrategyType(value = SkipStrategy.class)
    void onAlarmUpdated(Alarm alarm);
    @StateStrategyType(value = SkipStrategy.class)
    void onAlarmRemoved(Alarm alarm);

    void onAlarmsGot(List<Alarm> alarms);

    void setFabDisturb(boolean shouldDisturb);
}
