package com.andrew.alarmclock.data.repository.feed;

import com.andrew.alarmclock.api.news.NewsApi;
import com.andrew.alarmclock.data.db.feed.FeedDao;
import com.andrew.alarmclock.data.entities.Feed;
import com.andrew.alarmclock.data.error.ExistUrlError;
import com.andrew.alarmclock.data.sourse.assets.AssetsSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class FeedRepository implements IFeedRepository {

    private NewsApi newsApi;
    private FeedDao feedDao;
    private AssetsSource assetsSource;

    @Inject
    public FeedRepository(NewsApi newsApi, FeedDao feedDao, AssetsSource assetsSource) {
        this.newsApi = newsApi;
        this.feedDao = feedDao;
        this.assetsSource = assetsSource;
    }

    @Override
    public Single<List<Feed>> getAllFeeds() {
        return Single.fromCallable(() -> feedDao.getAllFeeds());
    }

    @Override
    public Observable<List<Feed>> getFeedsConsistently() {
        return Observable.fromCallable(() -> feedDao.getAllFeeds());
    }

    @Override
    public Single<Object> insertFeed(Feed feed) {
        return newsApi.validateFeed(feed.getUrl())
                .map(val -> {
                    for (Feed f : feedDao.getAllFeeds()) {
                        if (f.getUrl().equals(feed.getUrl()))
                            return Single.error(new ExistUrlError());
                    }

                    feedDao.insertFeed(feed);
                    return new Object();
                });
    }

    @Override
    public Single<Object> deleteFeed(Feed feed) {
        return Single.fromCallable(() -> {
            feedDao.deleteFeed(feed);
            return new Object();
        });
    }

    @Override
    public Single<Object> writeFeedsFromAssets(String assets) {
        return Single.fromCallable(() ->
                assetsSource.getFeedsUrls(assets)
                        .map(url -> {
                            feedDao.insertFeed(new Feed(url));
                            return new Object();
                        })
                        .subscribe());
    }
}
