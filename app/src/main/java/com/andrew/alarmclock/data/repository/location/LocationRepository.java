package com.andrew.alarmclock.data.repository.location;

import com.andrew.alarmclock.data.entities.CustomLocation;
import com.andrew.alarmclock.data.error.LocationError;
import com.andrew.alarmclock.data.sourse.location.ILocationSource;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LocationRepository implements ILocationRepository {

    private ILocationSource source;

    @Inject
    public LocationRepository(ILocationSource source) {
        this.source = source;
    }

    @Override
    public Observable<CustomLocation> getLocation() {
        return source.getLocation()
                .toObservable()
                .flatMap(value -> {
                    if (value == null)
                        return Observable.just(new CustomLocation(new LocationError()));

                    return Observable.just(new CustomLocation(value.getLatitude(), value.getLongitude()));
                })
                .onErrorReturn(CustomLocation::new);
    }
}
