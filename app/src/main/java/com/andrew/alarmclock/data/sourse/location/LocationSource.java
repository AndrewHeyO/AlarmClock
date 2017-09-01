package com.andrew.alarmclock.data.sourse.location;

import android.location.Location;
import android.location.LocationManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;

public class LocationSource implements ILocationSource {

    private LocationManager locationManager;

    @Inject
    public LocationSource(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    @Override
    public Single<Location> getLocation() {
        try {
            WeatherLocationListener listener = new WeatherLocationListener();
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, listener, null);
            return listener.observeLocationChange()
                    .take(1)
                    .singleOrError()
                    .timeout(7, TimeUnit.SECONDS,
                            observer -> observer.onSuccess(locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)));
        } catch (SecurityException ex) {
            return Single.error(ex);
        }
    }
}
