package com.andrew.alarmclock.business.news;

import com.andrew.alarmclock.data.entities.api.WeatherAndNewsResponse;
import com.andrew.alarmclock.data.entities.api.news.NewsResponse;
import com.andrew.alarmclock.data.entities.api.weather.WeatherResponse;

import io.reactivex.Single;

public interface INewsInteractor {
    Single<Boolean> isDarkTheme();

    Single<WeatherResponse> getWeather();
    Single<NewsResponse> getNews();

    Single<WeatherAndNewsResponse> getWeatherAndNews();
}
