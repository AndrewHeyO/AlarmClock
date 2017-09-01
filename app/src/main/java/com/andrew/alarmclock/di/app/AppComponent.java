package com.andrew.alarmclock.di.app;

import com.andrew.alarmclock.alarm.alarmClock.presentation.AlarmClockFragment;
import com.andrew.alarmclock.alarm.alarmReceiver.AlarmClockBootReceiver;
import com.andrew.alarmclock.alarm.alarmReceiver.AlarmClockReceiver;
import com.andrew.alarmclock.alarm.alarmReceiver.TimeChangedReceiver;
import com.andrew.alarmclock.alarm.disturb.DisturbReceiver;
import com.andrew.alarmclock.app.AlarmClockApp;
import com.andrew.alarmclock.di.alarm.AlarmModule;
import com.andrew.alarmclock.di.api.ApiModule;
import com.andrew.alarmclock.di.assets.AssetsModule;
import com.andrew.alarmclock.di.audio.AudioModule;
import com.andrew.alarmclock.di.db.DataBaseModule;
import com.andrew.alarmclock.di.location.LocationModule;
import com.andrew.alarmclock.di.news.NewsModule;
import com.andrew.alarmclock.di.notification.NotificationModule;
import com.andrew.alarmclock.di.pager.PagerComponent;
import com.andrew.alarmclock.di.sharedPreferences.SharedPreferencesModule;
import com.andrew.alarmclock.di.wake.WakeComponent;
import com.andrew.alarmclock.news.presentation.NewsPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        AppModule.class,
        DataBaseModule.class,
        AlarmModule.class,
        ApiModule.class,
        SharedPreferencesModule.class,
        AssetsModule.class,
        AudioModule.class,
        NewsModule.class,
        LocationModule.class,
        NotificationModule.class})
@Singleton
public interface AppComponent {
    void inject(AlarmClockApp app);
    void inject(AlarmClockFragment fragment);
    void inject(NewsPresenter presenter);
    void inject(AlarmClockReceiver receiver);
    void inject(AlarmClockBootReceiver receiver);
    void inject(TimeChangedReceiver receiver);
    void inject(DisturbReceiver receiver);

    PagerComponent.Builder pagerComponentBuilder();
    WakeComponent.Builder wakeComponentBuilder();

}
