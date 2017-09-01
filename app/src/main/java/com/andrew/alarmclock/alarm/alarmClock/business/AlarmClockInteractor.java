package com.andrew.alarmclock.alarm.alarmClock.business;

import com.andrew.alarmclock.data.entities.Alarm;
import com.andrew.alarmclock.data.repository.alarm.IAlarmClockRepository;
import com.andrew.alarmclock.data.repository.notification.INotificationRepository;
import com.andrew.alarmclock.data.repository.sharedPreferences.ISharedPreferencesRepository;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import io.reactivex.Single;

public class AlarmClockInteractor implements IAlarmClockInteractor {

    private IAlarmClockRepository alarmClockRepository;
    private ISharedPreferencesRepository sharedPreferencesRepository;
    private INotificationRepository notificationRepository;

    @Inject
    public AlarmClockInteractor(IAlarmClockRepository alarmClockRepository,
                                ISharedPreferencesRepository sharedPreferencesRepository,
                                INotificationRepository notificationRepository) {
        this.alarmClockRepository = alarmClockRepository;
        this.sharedPreferencesRepository = sharedPreferencesRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Single<List<Alarm>> getAllAlarms() {
        return alarmClockRepository.getAllAlarms();
    }

    @Override
    public Single<Alarm> insertAlarm(int hours, int minutes) {
        final Calendar c = Calendar.getInstance();
        int hoursBefore = c.get(Calendar.HOUR_OF_DAY);
        int minutesBefore = c.get(Calendar.MINUTE);

        boolean isToday = false;
        if (hoursBefore <= hours && !(hoursBefore == hours && minutesBefore >= minutes)) {
            isToday = true;

        }
        return alarmClockRepository.insertAlarm(new Alarm(hours, minutes, true, getAlarmDayNum(isToday)));
    }

    @Override
    public Single<Alarm> updateAlarm(Alarm alarm) {
        return alarmClockRepository.updateAlarm(alarm);
    }

    @Override
    public Single<Alarm> deleteAlarm(Alarm alarm) {
        return alarmClockRepository.deleteAlarm(alarm);
    }

    @Override
    public Single<Boolean> getShouldDisturb() {
        return sharedPreferencesRepository.getShouldDisturb();
    }

    @Override
    public Single<Boolean> getOppositeShouldDisturb() {
        return sharedPreferencesRepository.getOppositeShouldDisturb();
    }

    @Override
    public Single<Object> updateNotification(boolean shouldDisturb) {
        return notificationRepository.disturbNotificationHandle(shouldDisturb);
    }

    private Set<Alarm.Day> getAlarmDayNum(boolean isToday) {
        Calendar calendar = Calendar.getInstance();
        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
        Set<Alarm.Day> days = new TreeSet<>();
        days.add(Alarm.Day.days(dayNum + (isToday ? 0 : 1)));
        return days;
    }
}
