package com.andrew.alarmclock.di.pager;

import com.andrew.alarmclock.alarm.alarmClock.business.AlarmClockInteractor;
import com.andrew.alarmclock.alarm.alarmClock.business.IAlarmClockInteractor;
import com.andrew.alarmclock.pager.business.IPagerInteractor;
import com.andrew.alarmclock.pager.business.PagerInteractor;
import com.andrew.alarmclock.settings.business.IRssSettingsInteractor;
import com.andrew.alarmclock.settings.business.ISettingsInteractor;
import com.andrew.alarmclock.settings.business.RssSettingsInteractor;
import com.andrew.alarmclock.settings.business.SettingsInteractor;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PagerModule {
    @Binds
    @PagerScope
    abstract IAlarmClockInteractor provideAlarmClockInteractor(AlarmClockInteractor interactor);

    @Binds
    @PagerScope
    abstract ISettingsInteractor provideSettingsInteractor(SettingsInteractor interactor);

    @Binds
    @PagerScope
    abstract IRssSettingsInteractor provideRssSettingsInteractor(RssSettingsInteractor interactor);

    @Binds
    @PagerScope
    abstract IPagerInteractor providePagerInteractor(PagerInteractor interactor);
}
