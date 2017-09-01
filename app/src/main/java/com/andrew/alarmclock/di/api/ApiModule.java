package com.andrew.alarmclock.di.api;

import android.content.Context;

import com.andrew.alarmclock.api.news.NewsApi;
import com.andrew.alarmclock.api.qualifier.Json;
import com.andrew.alarmclock.api.qualifier.Xml;
import com.andrew.alarmclock.api.weather.WeatherApi;
import com.andrew.alarmclock.utils.Constant;
import com.andrew.alarmclock.utils.NetworkInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public abstract class ApiModule {

    @Provides
    @Singleton
    static Gson provideGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Provides
    @Singleton
    static WeatherApi provideWeatherApi(@Json Retrofit retrofit) {
        return retrofit.create(WeatherApi.class);
    }

    @Provides
    @Singleton
    static NewsApi provideNewsApi(@Xml Retrofit retrofit) {
        return retrofit.create(NewsApi.class);
    }

    @Provides
    @Singleton
    @Json
    static Retrofit provideJsonRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(Constant.WEATHER_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.newThread()))
                .build();
    }

    @Provides
    @Singleton
    @Xml
    static Retrofit provideXmlRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constant.WEATHER_BASE_URL)
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.newThread()))
                .build();
    }

    @Provides
    @Singleton
    static OkHttpClient provideClient(NetworkInterceptor interceptor) {

        return new OkHttpClient.Builder()
                //.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(interceptor)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    static NetworkInterceptor provideNetworkInterceptor(Context context) {
        return new NetworkInterceptor(context);
    }
}
