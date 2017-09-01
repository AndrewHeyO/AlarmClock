package com.andrew.alarmclock.data.repository.news;

import android.util.Log;

import com.andrew.alarmclock.api.news.NewsApi;
import com.andrew.alarmclock.data.db.news.NewsDao;
import com.andrew.alarmclock.data.entities.NewsItem;
import com.andrew.alarmclock.data.entities.api.news.NewsResponse;
import com.andrew.alarmclock.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class NewsRepository implements INewsRepository {

    private NewsApi api;
    private NewsDao newsDao;

    private SimpleDateFormat sdf;

    @Inject
    public NewsRepository(NewsApi api, NewsDao newsDao) {
        this.api = api;
        this.newsDao = newsDao;
        sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    }

    @Override
    public Observable<NewsItem> getRssFeed(String url) {
        return api.getRssFeed(url)
                .filter(rss -> rss.getChannel() != null)
                .flatMap(rss -> Observable.fromIterable(rss.getChannel().getRssItems())
                        .map(rssItem -> {
                            rssItem.setLogoUrl(rss.getChannel().getLogo().getUrl());
                            String link = rss.getChannel().getLinks().get(0).getLink();
                            if(link == null) {
                                rssItem.setFeed(rss.getChannel().getLinks().get(1).getLink());
                            } else {
                                rssItem.setFeed(link);
                            }
                            return rssItem;
                        }))
                .filter(rssItem -> rssItem.getTitle() != null && !rssItem.getTitle().isEmpty())
                .filter(rssItem -> rssItem.getDescription() != null && !rssItem.getDescription().isEmpty())
                .filter(rssItem -> rssItem.getLink() != null && !rssItem.getLink().isEmpty())
                .filter(rssItem -> rssItem.getLogoUrl() != null && !rssItem.getLogoUrl().isEmpty())
                .map(val -> new NewsItem(
                        val.getTitle(),
                        val.getCategories() != null ? val.getCategories().get(0) : "",
                        val.getDescription(),
                        sdf.parse(val.getPubDate()),
                        val.getLink().get(0).getLink() != null ?
                                val.getLink().get(0).getLink() : val.getLink().get(1).getLink(),
                        val.getLogoUrl(),
                        Utils.formatFeedUrl(val.getFeed())))
                .doOnError(error -> Log.d("NewsRepository_getRss", error.getMessage()));
    }

    @Override
    public Observable<NewsResponse> refreshNewsDatabase(List<NewsItem> newsItems) {
        return Observable.fromCallable(() -> {
            newsDao.deleteAll();
            newsDao.insertAll(newsItems);
            return new NewsResponse(newsItems);
        });
    }

    @Override
    public Single<Object> removeAllNewsByFeed(String feed) {
        return Single.fromCallable(() -> {
            newsDao.deleteAllByFeed(Utils.formatFeedUrl(feed));
            return new Object();
        });
    }

    @Override
    public Observable<List<NewsItem>> getCachedRssFeed() {
        return Observable.fromCallable(() -> newsDao.getAllNews());
    }
}
