package com.andrew.alarmclock.di.wake;

import com.andrew.alarmclock.alarm.alarmWake.business.AlarmWakeInteractor;
import com.andrew.alarmclock.alarm.alarmWake.business.IAlarmWakeInteractor;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class WakeModule {
    @Binds
    @WakeScope
    abstract IAlarmWakeInteractor provideWakeInteractor(AlarmWakeInteractor interactor);
}
