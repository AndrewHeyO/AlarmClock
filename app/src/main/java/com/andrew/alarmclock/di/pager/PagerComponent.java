package com.andrew.alarmclock.di.pager;

import com.andrew.alarmclock.presentation.presenters.alarmClock.AlarmClockPresenter;
import com.andrew.alarmclock.presentation.view.pager.PagerActivity;
import com.andrew.alarmclock.presentation.view.settings.SettingsFragment;
import com.andrew.alarmclock.presentation.presenters.settings.SettingsPresenter;
import com.andrew.alarmclock.presentation.presenters.settings.addRss.AddRssPresenter;
import com.andrew.alarmclock.presentation.presenters.settings.rssList.RssListPresenter;

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
