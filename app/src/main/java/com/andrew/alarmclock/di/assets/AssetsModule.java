package com.andrew.alarmclock.di.assets;

import android.content.Context;
import android.content.res.AssetManager;

import com.andrew.alarmclock.data.sourse.assets.AssetsSource;
import com.andrew.alarmclock.data.sourse.assets.IAssetsSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AssetsModule {

    @Provides
    @Singleton
    static AssetManager provideAssetManager(Context context) {
        return context.getAssets();
    }

    @Binds
    @Singleton
    abstract IAssetsSource provideAssetsSource(AssetsSource source);
}
