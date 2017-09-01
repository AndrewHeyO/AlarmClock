package com.andrew.alarmclock.settings.business;

import com.andrew.alarmclock.data.entities.Feed;

import java.util.List;

import io.reactivex.Single;

public interface IRssSettingsInteractor {
    Single<List<Feed>> getAllFeeds();
    Single<Object> insertFeed(Feed feed);
    Single<Object> deleteFeed(Feed feed);

    Single<Boolean> offlineValidateUrl(String url);
}
