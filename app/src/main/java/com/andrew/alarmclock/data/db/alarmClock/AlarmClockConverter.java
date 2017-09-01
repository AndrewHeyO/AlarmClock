package com.andrew.alarmclock.data.db.alarmClock;

import android.arch.persistence.room.TypeConverter;

import com.andrew.alarmclock.data.entities.Alarm;

import java.util.Set;
import java.util.TreeSet;

public class AlarmClockConverter {

    private final String separator = ",";

    @TypeConverter
    public String toString(Set<Alarm.Day> days) {
        String out = "";
        for(Alarm.Day day : days) {
            out += day.getDayNum() + separator;
        }
        return out;
    }

    @TypeConverter
    public Set<Alarm.Day> toList(String days) {
        if(days.isEmpty()) return new TreeSet<>();
        String[] daysArray = days.split(separator);
        Set<Alarm.Day> out = new TreeSet<>();
        for(String s : daysArray) {
            if(s.isEmpty()) continue;
            Alarm.Day day = Alarm.Day.days(Integer.parseInt(s));
            out.add(day);
        }
        return out;
    }
}
