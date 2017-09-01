package com.andrew.alarmclock.data.repository.location;

import com.andrew.alarmclock.data.entities.CustomLocation;
import com.andrew.alarmclock.data.error.LocationError;
import com.andrew.alarmclock.data.sourse.location.ILocationSource;

import javax.inject.Inject;

import io.reactivex.Single;

public class LocationRepository implements ILocationRepository {

    private ILocationSource source;

    @Inject
    public LocationRepository(ILocationSource source) {
        this.source = source;
    }

    @Override
    public Single<CustomLocation> getLocation() {
        return source.getLocation()
                .flatMap(value -> Single.just(value == null ?
                        new CustomLocation(new LocationError()) :
                        new CustomLocation(value.getLatitude(), value.getLongitude())))
                .onErrorReturn(CustomLocation::new);
    }
}
