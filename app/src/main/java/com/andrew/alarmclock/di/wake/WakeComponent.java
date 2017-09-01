package com.andrew.alarmclock.di.wake;

import com.andrew.alarmclock.alarm.alarmWake.presentation.AlarmWakeActivity;
import com.andrew.alarmclock.alarm.alarmWake.presentation.AlarmWakePresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {WakeModule.class})
@WakeScope
public interface WakeComponent {

    void inject(AlarmWakeActivity activity);
    void inject(AlarmWakePresenter presenter);

    @Subcomponent.Builder
    interface Builder {
        WakeComponent build();
    }
}
