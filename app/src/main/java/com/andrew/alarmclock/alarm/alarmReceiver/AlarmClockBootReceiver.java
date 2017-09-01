package com.andrew.alarmclock.alarm.alarmReceiver;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.andrew.alarmclock.data.repository.alarm.IAlarmClockRepository;
import com.andrew.alarmclock.di.InjectHolder;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class AlarmClockBootReceiver extends WakefulBroadcastReceiver {

    @Inject
    IAlarmClockRepository alarmClockRepository;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (alarmClockRepository == null) {
            InjectHolder.getInstance().getAppComponent().inject(this);
        }

        if (intent.getAction() != null) {
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                alarmClockRepository.restoreAlarms()
                        .subscribeOn(Schedulers.io())
                        .subscribe();
            }
        }
    }
}
