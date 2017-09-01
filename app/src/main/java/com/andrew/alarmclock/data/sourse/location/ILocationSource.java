package com.andrew.alarmclock.data.sourse.location;

import android.location.Location;

import io.reactivex.Single;

public interface ILocationSource {
    Single<Location> getLocation();
}
