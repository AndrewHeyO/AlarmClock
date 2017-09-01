package com.andrew.alarmclock.data.sourse.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class WeatherLocationListener implements LocationListener {
    private final PublishSubject<Location> subject;

    public WeatherLocationListener() {
        this.subject = PublishSubject.create();
    }

    @Override
    public void onLocationChanged(Location location) {
        subject.onNext(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public Observable<Location> observeLocationChange() {
        return subject;
    }
}
