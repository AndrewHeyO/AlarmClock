package com.andrew.alarmclock.alarm.alarmWake.business;

import com.andrew.alarmclock.data.entities.Alarm;
import com.andrew.alarmclock.data.repository.alarm.IAlarmClockRepository;
import com.andrew.alarmclock.data.repository.audioManager.IAudioManagerRepository;
import com.andrew.alarmclock.data.repository.sharedPreferences.ISharedPreferencesRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class AlarmWakeInteractor implements IAlarmWakeInteractor {

    private IAlarmClockRepository alarmClockRepository;
    private ISharedPreferencesRepository sharedPreferencesRepository;
    private IAudioManagerRepository audioManagerRepository;

    @Inject
    public AlarmWakeInteractor(IAlarmClockRepository alarmClockRepository,
                               ISharedPreferencesRepository sharedPreferencesRepository,
                               IAudioManagerRepository audioManagerRepository) {
        this.alarmClockRepository = alarmClockRepository;
        this.sharedPreferencesRepository = sharedPreferencesRepository;
        this.audioManagerRepository = audioManagerRepository;
    }

    @Override
    public Single<Alarm> getAlarmById(int id) {
        return alarmClockRepository.getAlarmById(id);
    }

    @Override
    public Single<Alarm> updateAlarm(Alarm alarm) {
        return alarmClockRepository.updateAlarm(alarm);
    }

    @Override
    public Single<Boolean> getVibrationPreference() {
        return sharedPreferencesRepository.getVibrationPreference();
    }

    @Override
    public Single<Object> setMaxVolume() {
        return audioManagerRepository.setMaxVolume();
    }
}
