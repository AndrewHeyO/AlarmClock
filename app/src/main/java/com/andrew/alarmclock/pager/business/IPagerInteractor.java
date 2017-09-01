package com.andrew.alarmclock.pager.business;

import io.reactivex.Single;

public interface IPagerInteractor {
    Single<Boolean> isDarkTheme();
}
