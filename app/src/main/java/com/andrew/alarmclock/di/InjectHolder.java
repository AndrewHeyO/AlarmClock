package com.andrew.alarmclock.di;

import android.content.Context;

import com.andrew.alarmclock.di.app.AppComponent;
import com.andrew.alarmclock.di.app.AppModule;
import com.andrew.alarmclock.di.app.DaggerAppComponent;
import com.andrew.alarmclock.di.pager.PagerComponent;
import com.andrew.alarmclock.di.wake.WakeComponent;

public class InjectHolder {

    private static InjectHolder injectHolder = new InjectHolder();

    private AppComponent appComponent;
    private PagerComponent pagerComponent;
    private WakeComponent wakeComponent;

    public static InjectHolder getInstance() {
        return injectHolder;
    }

    public void init(Context context) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(context))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public PagerComponent buildPagerComponent() {
        if(pagerComponent == null) {
            pagerComponent = appComponent.pagerComponentBuilder().build();
        }
        return pagerComponent;
    }

    public void clearPagerComponent() {
        pagerComponent = null;
    }

    public WakeComponent buildWakeComponent() {
        if(wakeComponent == null) {
            wakeComponent = appComponent.wakeComponentBuilder().build();
        }
        return wakeComponent;
    }

    public void clearWakeComponent() {
        wakeComponent = null;
    }
}