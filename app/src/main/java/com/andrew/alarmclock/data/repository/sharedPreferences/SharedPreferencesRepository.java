package com.andrew.alarmclock.data.repository.sharedPreferences;

import com.andrew.alarmclock.data.sourse.sharedPreferences.ISharedPreferencesSource;

import javax.inject.Inject;

import io.reactivex.Single;

public class SharedPreferencesRepository implements ISharedPreferencesRepository {

    private ISharedPreferencesSource source;

    @Inject
    public SharedPreferencesRepository(ISharedPreferencesSource source) {
        this.source = source;
    }

    @Override
    public Single<Object> setVibrationPreference(boolean isVibrate) {
        return Single.fromCallable(() -> {
            source.setVibrationPreference(isVibrate);
            return new Object();
        });
    }

    @Override
    public Single<Boolean> getVibrationPreference() {
        return Single.just(source.getVibrationPreference());
    }

    @Override
    public Single<Object> setFirstLaunchPreference(boolean isFirstLaunch) {
        return Single.fromCallable(() -> {
            source.setFirstLaunchPreference(isFirstLaunch);
            return new Object();
        });
    }

    @Override
    public Single<Boolean> getFirstLaunchPreference() {
        return Single.fromCallable(() -> source.getFirstLaunchPreference());
    }

    @Override
    public Single<Boolean> getShouldDisturb() {
        return Single.fromCallable(() -> source.getShouldDisturb());
    }

    @Override
    public Single<Object> setShouldDisturb(boolean shouldDisturb) {
        return Single.fromCallable(() -> {
            source.setShouldDisturb(shouldDisturb);
            return new Object();
        });
    }

    @Override
    public Single<Boolean> getOppositeShouldDisturb() {
        return Single.fromCallable(() -> {
            source.setShouldDisturb(!source.getShouldDisturb());
            return source.getShouldDisturb();
        });
    }

    @Override
    public Single<String> getLastAlarmTime() {
        return Single.fromCallable(() -> source.getLastAlarmTime());
    }

    @Override
    public Single<Object> setLastAlarmTime(String lastAlarmTime) {
        return Single.fromCallable(() -> {
            source.setLastAlarmTime(lastAlarmTime);
            return new Object();
        });
    }

    @Override
    public Single<Boolean> isDarkTheme() {
        return Single.fromCallable(() -> source.isDarkTheme());
    }

    @Override
    public Single<Object> setDarkTheme(boolean isDarkTheme) {
        return Single.fromCallable(() -> {
            source.setDarkTheme(isDarkTheme);
            return new Object();
        });
    }
}
