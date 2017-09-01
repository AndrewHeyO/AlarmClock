package com.andrew.alarmclock.data.db.news;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.andrew.alarmclock.data.entities.NewsItem;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert
    void insertAll(List<NewsItem> newsItems);

    @Query("select * from news")
    List<NewsItem> getAllNews();

    @Query("delete from news")
    void deleteAll();

    @Query("delete from news where feed = :feed")
    void deleteAllByFeed(String feed);
}
