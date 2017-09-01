package com.andrew.alarmclock.data.repository.feed;

import com.andrew.alarmclock.data.entities.Feed;

import java.util.List;

import io.reactivex.Single;

public interface IFeedRepository {
    Single<List<Feed>> getAllFeeds();
    Single<Object> insertFeed(Feed feed);
    Single<Object> deleteFeed(Feed feed);
    Single<Object> writeFeedsFromAssets(String assets);
}
