package com.andrew.alarmclock.di.wake;

import com.andrew.alarmclock.business.alarmWake.AlarmWakeInteractor;
import com.andrew.alarmclock.business.alarmWake.IAlarmWakeInteractor;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class WakeModule {
    @Binds
    @WakeScope
    abstract IAlarmWakeInteractor provideWakeInteractor(AlarmWakeInteractor interactor);
}
