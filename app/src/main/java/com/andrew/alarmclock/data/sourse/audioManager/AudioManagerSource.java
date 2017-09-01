package com.andrew.alarmclock.data.sourse.audioManager;

import android.media.AudioManager;
import android.util.Log;

import javax.inject.Inject;

public class AudioManagerSource implements IAudioManagerSource {

    private AudioManager audioManager;

    @Inject
    public AudioManagerSource(AudioManager audioManager) {
        this.audioManager = audioManager;
    }

    @Override
    public void setMaxVolume() {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
    }
}
