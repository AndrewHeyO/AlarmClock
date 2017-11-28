package com.andrew.alarmclock.data.service.alarmReceiver;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.andrew.alarmclock.presentation.view.alarmWake.AlarmWakeActivity;
import com.andrew.alarmclock.data.entities.Alarm;
import com.andrew.alarmclock.data.repository.alarm.IAlarmClockRepository;
import com.andrew.alarmclock.data.repository.sharedPreferences.ISharedPreferencesRepository;
import com.andrew.alarmclock.di.InjectHolder;
import com.andrew.alarmclock.utils.Constant;
import com.andrew.alarmclock.utils.comparator.AlarmComparator;

import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class AlarmClockReceiver extends WakefulBroadcastReceiver {

    @Inject
    IAlarmClockRepository alarmClockRepository;

    @Inject
    ISharedPreferencesRepository sharedPreferencesRepository;

    private int id;
    private int requestCode;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (alarmClockRepository == null || sharedPreferencesRepository == null) {
            InjectHolder.getInstance().getAppComponent().inject(this);
        }

        id = intent.getIntExtra(Constant.TAG_ALARM_ID, 0);
        requestCode = intent.getIntExtra(Constant.TAG_REQUEST_CODE, 0);

        if (id == 0) return;

        sharedPreferencesRepository.getShouldDisturb()
                .subscribe(shouldDisturb -> {
                    if (shouldDisturb) {
                        alarmClockRepository.getAlarmById(id)
                                .subscribeOn(Schedulers.io())
                                .subscribe(alarm -> showClock(context, alarm));
                    }
                });
    }

    private void showClock(Context context, Alarm alarm) {
        if (alarm == null) return;

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        Alarm tmp = new Alarm();
        tmp.setHours(hour);
        tmp.setMinutes(minute);

        if (requestCode != id - dayNum) return;
        if (hour != alarm.getHours() || minute != alarm.getMinutes()) return;

        for (Alarm.Day day : alarm.getDays()) {

            if (day.getDayNum() != dayNum) return;
            if (new AlarmComparator().compare(alarm, tmp) == -1) return;

            sharedPreferencesRepository.getLastAlarmTime()
                    .subscribe(time ->
                            checkLastDayAlarmTime(time, alarm, context, hour, minute, dayOfYear));
        }
    }

    private void checkLastDayAlarmTime(String time, Alarm alarm, Context context,
                                       int hour, int minute, int dayOfYear) {
        if (time.isEmpty()) {
            startWakeActivity(context, hour, minute, dayOfYear);
            return;
        }
        String[] parse = time.split(",");
        int parseHour = Integer.parseInt(parse[0]);
        int parseMinute = Integer.parseInt(parse[1]);
        int parseDayOfYear = Integer.parseInt(parse[2]);

        if (!(dayOfYear == parseDayOfYear && alarm.getHours() == parseHour
                && alarm.getMinutes() == parseMinute)) {
            startWakeActivity(context, hour, minute, dayOfYear);
        }
    }

    private void startWakeActivity(Context context, int hour, int minute, int dayOfYear) {
        sharedPreferencesRepository.setLastAlarmTime(hour + "," + minute + "," + dayOfYear)
                .subscribe();
        AlarmWakeActivity.start(context, id);
    }
}
