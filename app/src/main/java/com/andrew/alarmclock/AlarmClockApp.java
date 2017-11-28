package com.andrew.alarmclock;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.data.repository.feed.IFeedRepository;
import com.andrew.alarmclock.data.repository.sharedPreferences.ISharedPreferencesRepository;
import com.andrew.alarmclock.di.InjectHolder;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class AlarmClockApp extends Application {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Inject
    IFeedRepository feedRepository;

    @Inject
    ISharedPreferencesRepository sharedPreferencesRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        InjectHolder.getInstance().init(this);
        InjectHolder.getInstance().getAppComponent().inject(this);

        sharedPreferencesRepository.getFirstLaunchPreference()
                .flatMap(isFirstLaunch -> {
                    if (isFirstLaunch) {
                        feedRepository.writeFeedsFromAssets(getResources().getString(R.string.feeds_url))
                                .subscribeOn(Schedulers.io())
                                .subscribe();
                        sharedPreferencesRepository.setFirstLaunchPreference(false)
                                .subscribe();
                    }
                    return Single.just(isFirstLaunch);
                })
                .subscribe();
    }
}