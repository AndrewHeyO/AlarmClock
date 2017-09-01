package com.andrew.alarmclock.data.db.news;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.andrew.alarmclock.data.entities.NewsItem;

@Database(entities = {NewsItem.class}, version = 3)
@TypeConverters(value = {NewsConverter.class})
public abstract class NewsDataBase extends RoomDatabase {
    public abstract NewsDao getNewsDao();
}
