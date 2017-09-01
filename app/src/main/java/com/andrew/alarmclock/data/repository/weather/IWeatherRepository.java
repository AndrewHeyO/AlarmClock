package com.andrew.alarmclock.data.repository.weather;

import com.andrew.alarmclock.data.entities.CustomLocation;
import com.andrew.alarmclock.data.entities.api.weather.WeatherResponse;

import io.reactivex.Observable;

public interface IWeatherRepository {
   Observable<WeatherResponse> getWeather(CustomLocation location);
}
