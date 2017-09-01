package com.andrew.alarmclock.data.repository.weather;

import com.andrew.alarmclock.api.weather.WeatherApi;
import com.andrew.alarmclock.data.entities.CustomLocation;
import com.andrew.alarmclock.data.entities.api.weather.WeatherResponse;
import com.andrew.alarmclock.utils.Constant;

import javax.inject.Inject;

import io.reactivex.Single;

public class WeatherRepository implements IWeatherRepository {

    private static final int NUM_OF_WEATHER_FORECASTS = 8;
    private static final String MEASURE_UNITS = "metric";

    private WeatherApi api;

    @Inject
    public WeatherRepository(WeatherApi api) {
        this.api = api;
    }

    @Override
    public Single<WeatherResponse> getWeather(CustomLocation location) {
        if (location.getError() != null) {
            return Single.just(new WeatherResponse(location.getError()));
        } else {
            return api.getWeatherByCoordinates(location.getLatitude(), location.getLongitude(),
                        NUM_OF_WEATHER_FORECASTS, MEASURE_UNITS, Constant.WEATHER_API_KEY)
                    .map(WeatherResponse::new)
                    .onErrorReturn(WeatherResponse::new);
        }
    }
}
