package com.andrew.alarmclock.utils.comparator;

import com.andrew.alarmclock.data.entities.Alarm;

import java.util.Comparator;

public class AlarmComparator implements Comparator<Alarm> {
    @Override
    public int compare(Alarm alarm, Alarm t1) {
        if(alarm.getHours() > t1.getHours()) {
            return 1;
        } else {
            if(alarm.getHours() == t1.getHours()) {
                if(alarm.getMinutes() > t1.getMinutes()) {
                    return 1;
                } else if(alarm.getMinutes() == t1.getMinutes()) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        }
    }
}
