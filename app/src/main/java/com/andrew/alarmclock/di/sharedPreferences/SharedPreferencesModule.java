package com.andrew.alarmclock.di.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.andrew.alarmclock.data.repository.sharedPreferences.ISharedPreferencesRepository;
import com.andrew.alarmclock.data.repository.sharedPreferences.SharedPreferencesRepository;
import com.andrew.alarmclock.data.sourse.sharedPreferences.ISharedPreferencesSource;
import com.andrew.alarmclock.data.sourse.sharedPreferences.SharedPreferencesSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class SharedPreferencesModule {

    @Provides
    @Singleton
    static SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("sharedPref" ,Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    static SharedPreferences.Editor provideEditor(SharedPreferences sharedPreferences) {
        return sharedPreferences.edit();
    }

    @Binds
    @Singleton
    abstract ISharedPreferencesSource provideSharedPreferencesSource(SharedPreferencesSource source);

    @Binds
    @Singleton
    abstract ISharedPreferencesRepository provideSharedPreferencesRepository(SharedPreferencesRepository repository);
}
