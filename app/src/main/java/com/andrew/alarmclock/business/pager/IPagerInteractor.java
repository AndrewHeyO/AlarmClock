package com.andrew.alarmclock.business.pager;

import io.reactivex.Single;

public interface IPagerInteractor {
    Single<Boolean> isDarkTheme();
}
