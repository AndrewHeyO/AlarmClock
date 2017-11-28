package com.andrew.alarmclock.di.pager;

import com.andrew.alarmclock.business.alarmClock.AlarmClockInteractor;
import com.andrew.alarmclock.business.alarmClock.IAlarmClockInteractor;
import com.andrew.alarmclock.business.pager.IPagerInteractor;
import com.andrew.alarmclock.business.pager.PagerInteractor;
import com.andrew.alarmclock.business.settings.IRssSettingsInteractor;
import com.andrew.alarmclock.business.settings.ISettingsInteractor;
import com.andrew.alarmclock.business.settings.RssSettingsInteractor;
import com.andrew.alarmclock.business.settings.SettingsInteractor;

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
