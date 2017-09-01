package com.andrew.alarmclock.news.business;

import com.andrew.alarmclock.data.entities.api.WeatherAndNewsResponse;
import com.andrew.alarmclock.data.entities.api.news.NewsResponse;
import com.andrew.alarmclock.data.entities.api.weather.WeatherResponse;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface INewsInteractor {
    Single<Boolean> isDarkTheme();

    Observable<WeatherResponse> getWeather();
    Observable<NewsResponse> getNews();

    Observable<WeatherAndNewsResponse> getWeatherAndNews();
}
