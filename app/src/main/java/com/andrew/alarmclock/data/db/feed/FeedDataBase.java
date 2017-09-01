package com.andrew.alarmclock.data.db.feed;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.andrew.alarmclock.data.entities.Feed;

@Database(entities = {Feed.class}, version = 1)
public abstract class FeedDataBase extends RoomDatabase {
    public abstract FeedDao getFeedDao();
}
