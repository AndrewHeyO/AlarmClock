package com.andrew.alarmclock.pager.business;

import com.andrew.alarmclock.data.repository.sharedPreferences.ISharedPreferencesRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class PagerInteractor implements IPagerInteractor {

    private ISharedPreferencesRepository repository;

    @Inject
    public PagerInteractor(ISharedPreferencesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<Boolean> isDarkTheme() {
        return repository.isDarkTheme();
    }
}
