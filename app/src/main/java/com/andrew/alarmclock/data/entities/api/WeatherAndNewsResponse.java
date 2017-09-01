package com.andrew.alarmclock.data.entities.api;

import com.andrew.alarmclock.data.entities.api.news.NewsResponse;
import com.andrew.alarmclock.data.entities.api.weather.WeatherResponse;

public class WeatherAndNewsResponse {

    private WeatherResponse weatherResponse;

    private NewsResponse newsResponse;

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }

    public NewsResponse getNewsResponse() {
        return newsResponse;
    }

    public void setNewsResponse(NewsResponse newsResponse) {
        this.newsResponse = newsResponse;
    }
}
