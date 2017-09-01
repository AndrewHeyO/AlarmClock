package com.andrew.alarmclock.di.alarm;

import android.app.AlarmManager;
import android.content.Context;

import com.andrew.alarmclock.data.sourse.alarmManager.AlarmManagerSource;
import com.andrew.alarmclock.data.sourse.alarmManager.IAlarmManagerSource;
import com.andrew.alarmclock.data.repository.alarm.AlarmClockRepository;
import com.andrew.alarmclock.data.repository.alarm.IAlarmClockRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AlarmModule {

    @Provides
    @Singleton
    static AlarmManager provideAlarmManager(Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Binds
    @Singleton
    abstract IAlarmManagerSource provideAlarmSource(AlarmManagerSource source);

    @Binds
    @Singleton
    abstract IAlarmClockRepository provideAlarmRepository(AlarmClockRepository repository);
}
