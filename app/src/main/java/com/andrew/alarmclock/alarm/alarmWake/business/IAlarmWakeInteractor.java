package com.andrew.alarmclock.alarm.alarmWake.business;

import com.andrew.alarmclock.data.entities.Alarm;

import io.reactivex.Single;

public interface IAlarmWakeInteractor {
    Single<Alarm> getAlarmById(int id);
    Single<Alarm> updateAlarm(Alarm alarm);

    Single<Boolean> getVibrationPreference();

    Single<Object> setMaxVolume();
}
