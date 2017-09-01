package com.andrew.alarmclock.data.repository.location;

import com.andrew.alarmclock.data.entities.CustomLocation;

import io.reactivex.Observable;

public interface ILocationRepository {
    Observable<CustomLocation> getLocation();
}
