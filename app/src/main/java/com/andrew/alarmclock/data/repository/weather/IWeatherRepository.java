package com.andrew.alarmclock.data.repository.weather;

import com.andrew.alarmclock.data.entities.CustomLocation;
import com.andrew.alarmclock.data.entities.api.weather.WeatherResponse;

import io.reactivex.Single;

public interface IWeatherRepository {
   Single<WeatherResponse> getWeather(CustomLocation location);
}
