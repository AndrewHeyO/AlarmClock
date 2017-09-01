package com.andrew.alarmclock.data.sourse.alarmManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.andrew.alarmclock.alarm.alarmReceiver.AlarmClockReceiver;
import com.andrew.alarmclock.data.entities.Alarm;
import com.andrew.alarmclock.utils.Constant;
import com.andrew.alarmclock.utils.Utils;
import com.andrew.alarmclock.utils.stringManager.StringManager;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class AlarmManagerSource implements IAlarmManagerSource {

    private Context context;
    private AlarmManager alarmManager;
    private StringManager stringManager;

    @Inject
    public AlarmManagerSource(AlarmManager alarmManager,
                              StringManager stringManager,
                              Context context) {
        this.alarmManager = alarmManager;
        this.stringManager = stringManager;
        this.context = context;
    }

    @Override
    public void insertAlarm(Alarm alarm) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHours());
        calendar.set(Calendar.MINUTE, alarm.getMinutes());
        calendar.set(Calendar.SECOND, 0);

        int day = alarm.getDays().iterator().next().getDayNum();
        calendar.set(Calendar.DAY_OF_WEEK, day);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                setupIntent(alarm, day));
    }

    @Override
    public void updateAlarm(Alarm alarm) {
        if (!alarm.isOn() || alarm.getDays().size() == 0) {
            deleteAlarm(alarm);
            return;
        }

        cancelAllAlarms(alarm);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHours());
        calendar.set(Calendar.MINUTE, alarm.getMinutes());
        calendar.set(Calendar.SECOND, 0);

        for(Alarm.Day day : alarm.getDays()) {
            int dayNum = day.getDayNum();
            calendar.set(Calendar.DAY_OF_WEEK, dayNum);
            setupIntent(alarm, dayNum);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    setupIntent(alarm, dayNum));
        }
    }

    @Override
    public void deleteAlarm(Alarm alarm) {
        cancelAllAlarms(alarm);
    }

    @Override
    public String getNextAlarm(List<Alarm> alarmList) {
        Calendar now = Calendar.getInstance();
        int dayNum = now.get(Calendar.DAY_OF_WEEK);
        int hours = now.get(Calendar.HOUR_OF_DAY);
        int minutes = now.get(Calendar.MINUTE);

        return getAlarm(alarmList, dayNum, hours, minutes);
    }

    private PendingIntent setupIntent(Alarm alarm, int day) {
        Intent intent = new Intent(context, AlarmClockReceiver.class);
        int alarmId = alarm.getId();
        int requestCode = alarmId - day;
        intent.putExtra(Constant.TAG_ALARM_ID, alarmId);
        intent.putExtra(Constant.TAG_REQUEST_CODE, requestCode);
        return PendingIntent.getBroadcast(context, requestCode, intent, 0);
    }

    private void cancelAllAlarms(Alarm alarm) {
        for(int i = 1; i <= 7; i++) {
            alarmManager.cancel(setupIntent(alarm, i));
        }
    }

    private String getAlarm(List<Alarm> alarmList, int dayNum, int hours, int minutes) {
        if(alarmList.size() == 0) return "";

        int foundHours = Integer.MAX_VALUE;
        int foundMinutes = Integer.MAX_VALUE;

        for(Alarm alarm : alarmList) {
            if(!alarm.isOn()) continue;
            for(Alarm.Day day : alarm.getDays()) {
                if(dayNum != day.getDayNum()) continue;

                int alarmHours = alarm.getHours();
                if (hours > alarmHours) continue;

                int alarmMinutes = alarm.getMinutes();
                if (hours == alarmHours && minutes >= alarmMinutes) continue;


                if(foundHours > alarmHours) {
                    foundHours = alarmHours;
                    foundMinutes = alarmMinutes;
                }

                if(foundHours == alarmHours) {
                    if(foundMinutes > alarmMinutes) {
                        foundMinutes = alarmMinutes;
                    }
                }
            }
        }

        if(foundHours != Integer.MAX_VALUE && foundMinutes != Integer.MAX_VALUE) {
            return stringManager.getDayOfTheWeekById(dayNum) + " " +
                    String.format(Locale.getDefault(), "%02d:%02d", foundHours, foundMinutes);
        }

        dayNum = Utils.getNextDayNum(dayNum);

        return getAlarm(alarmList, dayNum, 0, 0);
    }
}
