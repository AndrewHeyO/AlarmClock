package com.andrew.alarmclock.data.sourse.alarmManager;

import com.andrew.alarmclock.data.entities.Alarm;

import java.util.List;

public interface IAlarmManagerSource {
    void insertAlarm(Alarm alarm);
    void updateAlarm(Alarm alarm);

    void deleteAlarm(Alarm alarm);

    String getNextAlarm(List<Alarm> alarmList);
}
