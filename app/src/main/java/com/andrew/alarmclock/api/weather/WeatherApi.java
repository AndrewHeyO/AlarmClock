package com.andrew.alarmclock.api.weather;

import com.andrew.alarmclock.data.entities.api.weather.Forecast;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("daily")
    Single<Forecast> getWeatherByCoordinates(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("cnt") int count,
            @Query("units") String unitsTemp,
            @Query("APPID") String appId);
}
