package com.andrew.alarmclock.di.db;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.andrew.alarmclock.data.db.alarmClock.AlarmClockDao;
import com.andrew.alarmclock.data.db.alarmClock.AlarmClockDataBase;
import com.andrew.alarmclock.data.db.feed.FeedDao;
import com.andrew.alarmclock.data.db.feed.FeedDataBase;
import com.andrew.alarmclock.data.db.news.NewsDao;
import com.andrew.alarmclock.data.db.news.NewsDataBase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DataBaseModule {
    @Provides
    @Singleton
    static AlarmClockDataBase provideAlarmDB(Context context) {
        return Room.databaseBuilder(context, AlarmClockDataBase.class, "alarm_clock_db").build();
    }

    @Provides
    @Singleton
    static AlarmClockDao provideAlarmDao(AlarmClockDataBase db) {
        return db.getAlarmClockDao();
    }

    @Provides
    @Singleton
    static NewsDataBase provideNewsDB(Context context) {
        return Room.databaseBuilder(context, NewsDataBase.class, "news_db").build();
    }

    @Provides
    @Singleton
    static NewsDao provideNewsDao(NewsDataBase db) {
        return db.getNewsDao();
    }

    @Provides
    @Singleton
    static FeedDataBase provideFeedDB(Context context) {
        return Room.databaseBuilder(context, FeedDataBase.class, "feed_db").build();
    }

    @Provides
    @Singleton
    static FeedDao provideFeedDao(FeedDataBase db) {
        return db.getFeedDao();
    }
}
