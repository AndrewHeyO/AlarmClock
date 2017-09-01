package com.andrew.alarmclock.di.location;

import android.content.Context;
import android.location.LocationManager;

import com.andrew.alarmclock.data.repository.location.ILocationRepository;
import com.andrew.alarmclock.data.repository.location.LocationRepository;
import com.andrew.alarmclock.data.sourse.location.ILocationSource;
import com.andrew.alarmclock.data.sourse.location.LocationSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class LocationModule {

    @Provides
    @Singleton
    static LocationManager provideLocationManager(Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Binds
    @Singleton
    abstract ILocationSource provideLocationSource(LocationSource source);

    @Binds
    @Singleton
    abstract ILocationRepository provideLocationRepository(LocationRepository repository);
}
