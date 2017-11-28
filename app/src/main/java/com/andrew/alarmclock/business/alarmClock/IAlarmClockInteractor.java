package com.andrew.alarmclock.business.alarmClock;

import com.andrew.alarmclock.data.entities.Alarm;

import java.util.List;

import io.reactivex.Single;

public interface IAlarmClockInteractor {
    Single<List<Alarm>> getAllAlarms();
    Single<Alarm> insertAlarm(int hours, int minutes);
    Single<Alarm> updateAlarm(Alarm alarm);
    Single<Alarm> deleteAlarm(Alarm alarm);

    Single<Boolean> getShouldDisturb();

    Single<Boolean> getOppositeShouldDisturb();

    Single<Object> updateNotification(boolean shouldDisturb);
}
