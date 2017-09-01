package com.andrew.alarmclock.data.db.feed;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.andrew.alarmclock.data.entities.Feed;

import java.util.List;

@Dao
public interface FeedDao {

    @Query("select * from feed")
    List<Feed> getAllFeeds();

    @Insert
    void insertFeed(Feed feed);

    @Delete
    void deleteFeed(Feed feed);
}
