package com.andrew.alarmclock.alarm.disturb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.andrew.alarmclock.data.repository.notification.INotificationRepository;
import com.andrew.alarmclock.data.repository.sharedPreferences.ISharedPreferencesRepository;
import com.andrew.alarmclock.di.InjectHolder;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DisturbReceiver extends BroadcastReceiver {

    public static final String DISTURB_ACTION = "DISTURB";
    public static final String NO_DISTURB_ACTION = "NO_DISTURB";

    @Inject
    INotificationRepository notificationRepository;

    @Inject
    ISharedPreferencesRepository sharedPreferencesRepository;

    @Inject
    DisturbServiceListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (notificationRepository == null || sharedPreferencesRepository == null
                || listener == null) {
            InjectHolder.getInstance().getAppComponent().inject(this);
        }

        String action = intent.getAction();

        if(action == null) return;

        if (action.equals(DISTURB_ACTION)) {
            handleAction(false);
        }
        if (action.equals(NO_DISTURB_ACTION)) {
            handleAction(true);
        }
    }

    private void handleAction(boolean action) {
        notificationRepository.disturbNotificationHandle(action)
                .subscribeOn(Schedulers.io())
                .subscribe();
        sharedPreferencesRepository.setShouldDisturb(action)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
        listener.onDisturbChange(action);
    }
}
