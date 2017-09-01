package com.andrew.alarmclock.data.repository.location;

import com.andrew.alarmclock.data.entities.CustomLocation;

import io.reactivex.Single;

public interface ILocationRepository {
    Single<CustomLocation> getLocation();
}
