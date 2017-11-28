package com.andrew.alarmclock.di.news;

import com.andrew.alarmclock.data.repository.news.INewsRepository;
import com.andrew.alarmclock.data.repository.news.NewsRepository;
import com.andrew.alarmclock.data.repository.weather.IWeatherRepository;
import com.andrew.alarmclock.data.repository.weather.WeatherRepository;
import com.andrew.alarmclock.business.news.INewsInteractor;
import com.andrew.alarmclock.business.news.NewsInteractor;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class NewsModule {

    @Binds
    @Singleton
    abstract INewsInteractor provideNewsInteractor(NewsInteractor interactor);

    @Binds
    @Singleton
    abstract INewsRepository provideNewsRepository(NewsRepository repository);

    @Binds
    @Singleton
    abstract IWeatherRepository provideWeatherRepository(WeatherRepository repository);
}
