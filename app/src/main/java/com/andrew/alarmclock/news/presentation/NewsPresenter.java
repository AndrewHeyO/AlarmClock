package com.andrew.alarmclock.news.presentation;

import android.util.Log;

import com.andrew.alarmclock.base.BasePresenter;
import com.andrew.alarmclock.data.entities.api.WeatherAndNewsResponse;
import com.andrew.alarmclock.data.entities.api.news.NewsResponse;
import com.andrew.alarmclock.data.entities.api.weather.WeatherResponse;
import com.andrew.alarmclock.data.error.LocationError;
import com.andrew.alarmclock.data.error.NetworkError;
import com.andrew.alarmclock.di.InjectHolder;
import com.andrew.alarmclock.news.business.INewsInteractor;
import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;
import javax.net.ssl.SSLHandshakeException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsPresenter extends BasePresenter<NewsView> {

    @Inject
    INewsInteractor interactor;

    public NewsPresenter() {
        InjectHolder.getInstance().getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().onShowProgressBar();
        onGetNewsAndWeather();
    }

    @Override
    public void attachView(NewsView view) {
        super.attachView(view);
        onHandleRefreshColor();
    }

    public void onGetWeather() {
        addDisposables(interactor.getWeather()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleWeatherSuccess,
                        error -> Log.d("NewsPresenter_W", error.getMessage())));
    }

    public void onGetNewsAndWeather() {
        interactor.getWeatherAndNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleSuccess,
                        error -> Log.d("NewsPresenter_NandW", error.getMessage()));
    }

    private void onHandleRefreshColor() {
        interactor.isDarkTheme()
                .subscribe(val -> getViewState().setRefreshCircleColor(val));
    }

    private void handleWeatherSuccess(WeatherResponse value) {
        if (value.getForecast() != null) {
            getViewState().showWeather(value);
        } else {
            Throwable error = value.getError();
            if (error instanceof SecurityException) {
                getViewState().showPermissionsError();
                return;
            }
            if (error instanceof LocationError) {
                getViewState().showLocationError();
            }
        }
    }

    private void handleSuccess(WeatherAndNewsResponse val) {
        NewsResponse newsResponse = val.getNewsResponse();
        WeatherResponse weatherResponse = val.getWeatherResponse();
        boolean isNetworkError = false;

        for (Throwable error : newsResponse.getErrors()) {
            Log.d("NewsPresenter", error.getMessage());
            if ((error instanceof NetworkError || error instanceof SSLHandshakeException) && !isNetworkError) {
                getViewState().showNetworkError();
                isNetworkError = true;
            }
        }

        if(isNetworkError) {
            if(newsResponse.getNewsItems().size() != 0) {
                getViewState().showCachedNews(newsResponse);
            } else {
                getViewState().showNetworkError();
            }
        }
        if (newsResponse.getErrors().size() == 0) {
            getViewState().showNews(newsResponse);
        }

        if(isNetworkError) return;

        if (weatherResponse.getForecast() != null) {
            getViewState().showWeather(weatherResponse);
        } else {
            Throwable error = weatherResponse.getError();
            if (error instanceof SecurityException) {
                getViewState().showPermissionsError();
                return;
            }
            if (error instanceof LocationError) {
                getViewState().showLocationError();
            }
        }
    }
}
