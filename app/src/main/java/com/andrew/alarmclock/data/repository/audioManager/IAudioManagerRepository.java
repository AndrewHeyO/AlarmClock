package com.andrew.alarmclock.data.repository.audioManager;

import io.reactivex.Single;

public interface IAudioManagerRepository {
    Single<Object> setMaxVolume();
}
