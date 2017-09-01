package com.andrew.alarmclock.di.pager;

import com.andrew.alarmclock.alarm.alarmClock.presentation.AlarmClockPresenter;
import com.andrew.alarmclock.pager.presentation.PagerActivity;
import com.andrew.alarmclock.settings.presentation.SettingsFragment;
import com.andrew.alarmclock.settings.presentation.SettingsPresenter;
import com.andrew.alarmclock.settings.presentation.addRss.AddRssPresenter;
import com.andrew.alarmclock.settings.presentation.rssList.RssListPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {PagerModule.class})
@PagerScope
public interface PagerComponent {
    void inject(PagerActivity activity);
    void inject(SettingsFragment fragment);
    void inject(AlarmClockPresenter presenter);
    void inject(SettingsPresenter presenter);
    void inject(RssListPresenter presenter);
    void inject(AddRssPresenter presenter);

    @Subcomponent.Builder
    interface Builder {
        PagerComponent build();
    }
}
