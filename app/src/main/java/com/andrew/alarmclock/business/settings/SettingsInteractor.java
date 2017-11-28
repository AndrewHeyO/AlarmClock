package com.andrew.alarmclock.business.settings;

import com.andrew.alarmclock.data.repository.sharedPreferences.ISharedPreferencesRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class SettingsInteractor implements ISettingsInteractor {

    private ISharedPreferencesRepository sharedPreferencesRepository;

    @Inject
    public SettingsInteractor(ISharedPreferencesRepository sharedPreferencesRepository) {
        this.sharedPreferencesRepository = sharedPreferencesRepository;
    }

    @Override
    public Single<Object> setVibrationPreference(boolean isVibrate) {
        return sharedPreferencesRepository.setVibrationPreference(isVibrate);
    }

    @Override
    public Single<Boolean> getVibration() {
        return sharedPreferencesRepository.getVibrationPreference();
    }

    @Override
    public Single<Boolean> isDarkTheme() {
        return sharedPreferencesRepository.isDarkTheme();
    }

    @Override
    public Single<Object> setDarkTheme(boolean isDarkTheme) {
        return sharedPreferencesRepository.setDarkTheme(isDarkTheme);
    }
}
