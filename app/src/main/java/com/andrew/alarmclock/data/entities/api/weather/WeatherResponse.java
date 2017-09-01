package com.andrew.alarmclock.data.entities.api.weather;

public class WeatherResponse {

    private Forecast forecast;

    private Throwable error;

    public WeatherResponse(Forecast forecast) {
        this.forecast = forecast;
    }

    public WeatherResponse(Throwable error) {
        this.error = error;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
