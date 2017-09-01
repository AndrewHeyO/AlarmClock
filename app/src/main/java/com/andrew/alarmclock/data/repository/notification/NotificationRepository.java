package com.andrew.alarmclock.data.repository.notification;

import com.andrew.alarmclock.data.db.alarmClock.AlarmClockDao;
import com.andrew.alarmclock.data.sourse.alarmManager.IAlarmManagerSource;
import com.andrew.alarmclock.data.sourse.notification.INotificationSource;
import com.andrew.alarmclock.data.sourse.sharedPreferences.ISharedPreferencesSource;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class NotificationRepository implements INotificationRepository {

    private INotificationSource notificationSource;
    private ISharedPreferencesSource sharedPreferencesSource;
    private IAlarmManagerSource alarmManagerSource;
    private AlarmClockDao dao;

    @Inject
    public NotificationRepository(INotificationSource notificationSource,
                                  ISharedPreferencesSource sharedPreferencesSource,
                                  IAlarmManagerSource alarmManagerSource,
                                  AlarmClockDao dao) {
        this.notificationSource = notificationSource;
        this.sharedPreferencesSource = sharedPreferencesSource;
        this.alarmManagerSource = alarmManagerSource;
        this.dao = dao;
    }

    @Override
    public Single<Object> disturbNotificationHandle(boolean shouldDisturb) {
        return Single.fromCallable(() -> dao.getAll())
                .observeOn(AndroidSchedulers.mainThread())
                .map(val -> {
                    notificationSource.createNotification(alarmManagerSource.getNextAlarm(val),
                            sharedPreferencesSource.getShouldDisturb(),
                            sharedPreferencesSource.isDeleted());
                    return new Object();
                });
    }
}
