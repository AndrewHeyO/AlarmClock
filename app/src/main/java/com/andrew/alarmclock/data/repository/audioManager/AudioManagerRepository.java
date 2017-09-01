package com.andrew.alarmclock.data.repository.audioManager;

import com.andrew.alarmclock.data.sourse.audioManager.AudioManagerSource;

import javax.inject.Inject;

import io.reactivex.Single;

public class AudioManagerRepository implements IAudioManagerRepository {

    private AudioManagerSource source;

    @Inject
    public AudioManagerRepository(AudioManagerSource source) {
        this.source = source;
    }

    @Override
    public Single<Object> setMaxVolume() {
        return Single.fromCallable(() -> {
            source.setMaxVolume();
            return new Object();
        });
    }
}
