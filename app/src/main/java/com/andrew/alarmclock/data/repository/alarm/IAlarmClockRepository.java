package com.andrew.alarmclock.data.repository.alarm;

import com.andrew.alarmclock.data.entities.Alarm;

import java.util.List;

import io.reactivex.Single;

public interface IAlarmClockRepository {
    Single<List<Alarm>> getAllAlarms();

    Single<Alarm> insertAlarm(Alarm alarm);
    Single<Alarm> updateAlarm(Alarm alarm);
    Single<Alarm> deleteAlarm(Alarm alarm);

    Single<List<Alarm>> restoreAlarms();

    Single<Alarm> getAlarmById(int id);
}
