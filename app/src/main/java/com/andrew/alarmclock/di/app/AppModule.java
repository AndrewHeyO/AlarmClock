package com.andrew.alarmclock.di.app;

import android.content.Context;

import com.andrew.alarmclock.data.service.disturb.DisturbServiceListener;
import com.andrew.alarmclock.network.api.news.NewsApi;
import com.andrew.alarmclock.data.db.feed.FeedDao;
import com.andrew.alarmclock.data.repository.feed.FeedRepository;
import com.andrew.alarmclock.data.repository.feed.IFeedRepository;
import com.andrew.alarmclock.data.sourse.assets.AssetsSource;
import com.andrew.alarmclock.utils.stringManager.IStringManager;
import com.andrew.alarmclock.utils.stringManager.StringManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    static IStringManager provideStringManager(Context context) {
        return new StringManager(context);
    }

    @Provides
    @Singleton
    static IFeedRepository provideFeedRepository(NewsApi api, FeedDao dao, AssetsSource source) {
        return new FeedRepository(api, dao, source);
    }

    @Provides
    @Singleton
    static DisturbServiceListener provideDisturbServiceListener() {
        return new DisturbServiceListener();
    }
}
