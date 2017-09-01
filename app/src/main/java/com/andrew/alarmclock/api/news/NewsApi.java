package com.andrew.alarmclock.api.news;

import com.andrew.alarmclock.data.entities.api.news.Rss;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsApi {
    @GET
    Observable<Rss> getRssFeed(@Url String url);

    @GET
    Single<Rss> validateFeed(@Url String url);
}
