package com.andrew.alarmclock.data.repository.news;

import com.andrew.alarmclock.data.entities.NewsItem;
import com.andrew.alarmclock.data.entities.api.news.NewsResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface INewsRepository {
    Observable<NewsItem> getRssFeed(String url);

    Single<NewsResponse> refreshNewsDatabase(List<NewsItem> newsItems);

    Single<Object> removeAllNewsByFeed(String feed);

    Single<List<NewsItem>> getCachedRssFeed();
}
