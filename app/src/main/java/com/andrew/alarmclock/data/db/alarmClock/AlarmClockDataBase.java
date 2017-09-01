package com.andrew.alarmclock.data.db.alarmClock;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.andrew.alarmclock.data.entities.Alarm;

@Database(entities = {Alarm.class}, version = 8)
@TypeConverters(value = {AlarmClockConverter.class})
public abstract class AlarmClockDataBase extends RoomDatabase {
    public abstract AlarmClockDao getAlarmClockDao();
}
