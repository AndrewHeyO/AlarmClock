package com.andrew.alarmclock.data.service.alarmReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.andrew.alarmclock.data.repository.alarm.IAlarmClockRepository;
import com.andrew.alarmclock.di.InjectHolder;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class TimeChangedReceiver extends BroadcastReceiver {

    @Inject
    IAlarmClockRepository alarmClockRepository;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (alarmClockRepository == null) {
            InjectHolder.getInstance().getAppComponent().inject(this);
        }

        if (intent.getAction() != null) {
            if (intent.getAction().equals("android.intent.action.TIME_SET")) {
                alarmClockRepository.restoreAlarms()
                        .subscribeOn(Schedulers.io())
                        .subscribe();
            }
        }
    }
}
