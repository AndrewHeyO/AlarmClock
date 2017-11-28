package com.andrew.alarmclock.business.settings;

import com.andrew.alarmclock.data.entities.Feed;
import com.andrew.alarmclock.data.repository.feed.IFeedRepository;
import com.andrew.alarmclock.data.repository.news.INewsRepository;
import com.andrew.alarmclock.utils.Constant;

import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.Single;

public class RssSettingsInteractor implements IRssSettingsInteractor {

    private IFeedRepository feedRepository;
    private INewsRepository newsRepository;

    @Inject
    public RssSettingsInteractor(IFeedRepository feedRepository,
                                 INewsRepository newsRepository) {
        this.feedRepository = feedRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public Single<List<Feed>> getAllFeeds() {
        return feedRepository.getAllFeeds();
    }

    @Override
    public Single<Object> insertFeed(Feed feed) {
        return feedRepository.insertFeed(feed);
    }

    @Override
    public Single<Object> deleteFeed(Feed feed) {
        return Single.fromCallable(() -> {
            feedRepository.deleteFeed(feed).subscribe();
            newsRepository.removeAllNewsByFeed(feed.getUrl()).subscribe();
            return new Object();
        });
    }

    @Override
    public Single<Boolean> offlineValidateUrl(String url) {
        return Single.fromCallable(() -> Pattern.compile(Constant.URL_PATTERN).matcher(url).find());
    }
}
