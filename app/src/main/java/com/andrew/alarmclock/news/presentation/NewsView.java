package com.andrew.alarmclock.news.presentation;

import com.andrew.alarmclock.data.entities.api.news.NewsResponse;
import com.andrew.alarmclock.data.entities.api.weather.WeatherResponse;
import com.arellomobile.mvp.MvpView;

public interface NewsView extends MvpView {
    void setRefreshCircleColor(boolean isDarkTheme);
    void onShowProgressBar();

    void showWeather(WeatherResponse response);
    void showNews(NewsResponse response);
    void showCachedNews(NewsResponse response);

    void showLocationError();
    void showNetworkError();
    void showNetworkErrorWeather();
    void showPermissionsError();
}
