package com.andrew.alarmclock.data.repository.news;

import com.andrew.alarmclock.network.api.news.NewsApi;
import com.andrew.alarmclock.data.db.news.NewsDao;
import com.andrew.alarmclock.data.entities.NewsItem;
import com.andrew.alarmclock.data.entities.api.news.NewsResponse;
import com.andrew.alarmclock.data.entities.api.news.Rss;
import com.andrew.alarmclock.data.entities.api.news.RssItem;
import com.andrew.alarmclock.utils.Utils;

import java.text.ParseException;
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
                .flatMap(this::setupRssItems)
                .filter(rssItem -> rssItem.getTitle() != null && !rssItem.getTitle().isEmpty())
                .filter(rssItem -> rssItem.getDescription() != null && !rssItem.getDescription().isEmpty())
                .filter(rssItem -> rssItem.getLink() != null && !rssItem.getLink().isEmpty())
                .filter(rssItem -> rssItem.getLogoUrl() != null && !rssItem.getLogoUrl().isEmpty())
                .map(this::setupNewsItemFromRssItem);
    }

    @Override
    public Single<NewsResponse> refreshNewsDatabase(List<NewsItem> newsItems) {
        return Single.fromCallable(() -> {
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
    public Single<List<NewsItem>> getCachedRssFeed() {
        return Single.fromCallable(() -> newsDao.getAllNews());
    }

    private Observable<RssItem> setupRssItems(Rss rss) {
        return Observable.fromIterable(rss.getChannel().getRssItems())
                .map(rssItem -> {
                    rssItem.setLogoUrl(rss.getChannel().getLogo().getUrl());
                    String link = rss.getChannel().getLinks().get(0).getLink();
                    if(link == null) {
                        rssItem.setFeed(rss.getChannel().getLinks().get(1).getLink());
                    } else {
                        rssItem.setFeed(link);
                    }
                    return rssItem;
                });
    }

    private NewsItem setupNewsItemFromRssItem(RssItem val) throws ParseException {
        return new NewsItem(
                val.getTitle(),
                val.getCategories() != null ? val.getCategories().get(0) : "",
                val.getDescription(),
                sdf.parse(val.getPubDate()),
                val.getLink().get(0).getLink() != null ?
                        val.getLink().get(0).getLink() : val.getLink().get(1).getLink(),
                val.getLogoUrl(),
                Utils.formatFeedUrl(val.getFeed()));
    }
}
