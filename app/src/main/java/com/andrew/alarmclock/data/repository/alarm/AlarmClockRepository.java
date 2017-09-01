package com.andrew.alarmclock.data.repository.alarm;

import com.andrew.alarmclock.data.db.alarmClock.AlarmClockDao;
import com.andrew.alarmclock.data.entities.Alarm;
import com.andrew.alarmclock.data.sourse.alarmManager.IAlarmManagerSource;
import com.andrew.alarmclock.data.sourse.notification.INotificationSource;
import com.andrew.alarmclock.data.sourse.sharedPreferences.ISharedPreferencesSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class AlarmClockRepository implements IAlarmClockRepository {

    private AlarmClockDao alarmClockDao;
    private IAlarmManagerSource alarmManagerSource;
    private INotificationSource notificationSource;
    private ISharedPreferencesSource sharedPreferencesSource;

    @Inject
    public AlarmClockRepository(AlarmClockDao alarmClockDao,
                                IAlarmManagerSource alarmManagerSource,
                                INotificationSource notificationSource,
                                ISharedPreferencesSource sharedPreferencesSource) {
        this.alarmClockDao = alarmClockDao;
        this.alarmManagerSource = alarmManagerSource;
        this.notificationSource = notificationSource;
        this.sharedPreferencesSource = sharedPreferencesSource;
    }

    @Override
    public Single<List<Alarm>> getAllAlarms() {
        return Single.fromCallable(() -> alarmClockDao.getAll());
    }

    @Override
    public Single<Alarm> insertAlarm(Alarm alarm) {
        return Single.fromCallable(() -> {
            alarmClockDao.insert(alarm);
            setupNotification(alarmClockDao.getAll());
            alarmManagerSource.insertAlarm(alarm);
            return alarm;
        });
    }

    @Override
    public Single<Alarm> updateAlarm(Alarm alarm) {
        return Single.fromCallable(() -> {
            alarmClockDao.update(alarm);
            setupNotification(alarmClockDao.getAll());
            alarmManagerSource.updateAlarm(alarm);
            return alarm;
        });
    }

    @Override
    public Single<Alarm> deleteAlarm(Alarm alarm) {
        return Single.fromCallable(() -> {
            alarmClockDao.delete(alarm);
            setupNotification(alarmClockDao.getAll());
            alarmManagerSource.deleteAlarm(alarm);
            return alarm;
        });
    }

    @Override
    public Single<List<Alarm>> restoreAlarms() {
        return Single.fromCallable(() -> {
            List<Alarm> list = alarmClockDao.getAll();
            for(Alarm a : list) {
                alarmManagerSource.updateAlarm(a);
            }
            setupNotification(alarmClockDao.getAll());
            return list;
        });
    }

    @Override
    public Single<Alarm> getAlarmById(int id) {
        return Single.fromCallable(() -> alarmClockDao.getAlarmById(id));
    }

    private void setupNotification(List<Alarm> list) {
        boolean check = false;
        for(Alarm alarm : list) {
            if(alarm.isOn() && alarm.getDays().size() > 0) {
                check =  true;
                break;
            }
        }
        if(check) {
            sharedPreferencesSource.setDeleted(false);
            notificationSource.createNotification(alarmManagerSource.getNextAlarm(list),
                    sharedPreferencesSource.getShouldDisturb(), sharedPreferencesSource.isDeleted());
        } else {
            sharedPreferencesSource.setDeleted(true);
            notificationSource.removeNotification();
        }
    }
}
