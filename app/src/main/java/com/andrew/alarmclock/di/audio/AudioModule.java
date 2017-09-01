package com.andrew.alarmclock.di.audio;

import android.content.Context;
import android.media.AudioManager;

import com.andrew.alarmclock.data.repository.audioManager.AudioManagerRepository;
import com.andrew.alarmclock.data.repository.audioManager.IAudioManagerRepository;
import com.andrew.alarmclock.data.sourse.audioManager.AudioManagerSource;
import com.andrew.alarmclock.data.sourse.audioManager.IAudioManagerSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AudioModule {

    @Provides
    @Singleton
    static AudioManager provideAudioManager(Context context) {
        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    @Binds
    @Singleton
    abstract IAudioManagerSource provideAudioManagerSource(AudioManagerSource source);

    @Binds
    @Singleton
    abstract IAudioManagerRepository provideAudioManagerRepository(AudioManagerRepository repository);

}
