package com.andrew.alarmclock.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

@Entity
public class Alarm implements Serializable {

    @PrimaryKey
    private int id;

    private int hours;

    private int minutes;

    private boolean isOn;

    private Set<Day> days;

    @Ignore
    private boolean isExpanded;

    public Alarm() {
        id = (int) java.util.UUID.randomUUID().getMostSignificantBits();
    }

    @Ignore
    public Alarm(int hours, int minutes, boolean isOn, Set<Day> days) {
        id = (int) java.util.UUID.randomUUID().getMostSignificantBits();
        this.hours = hours;
        this.minutes = minutes;
        this.isOn = isOn;
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public Set<Day> getDays() {
        return days;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public Alarm addMinutes(int minutes) {
        Calendar calendar = Calendar.getInstance();
        this.hours = calendar.get(Calendar.HOUR_OF_DAY);
        this.minutes = calendar.get(Calendar.MINUTE) + minutes;
        if(this.minutes >= 60) {
            this.hours += 1;
            this.minutes -= (60 - minutes);
        }
        return this;
    }

    public enum Day {
        SUNDAY(1),
        MONDAY(2),
        TUESDAY(3),
        WEDNESDAY(4),
        THURSDAY(5),
        FRIDAY(6),
        SATURDAY(7);

        int dayNum;

        Day(int dayNum) {
            this.dayNum = dayNum;
        }

        public int getDayNum() {
            return dayNum;
        }

        public static Day days(int dayNum) {
            switch (dayNum) {
                case 1:
                    return SUNDAY;
                case 2:
                    return MONDAY;
                case 3:
                    return TUESDAY;
                case 4:
                    return WEDNESDAY;
                case 5:
                    return THURSDAY;
                case 6:
                    return FRIDAY;
                case 7:
                    return SATURDAY;
                case 8:
                    return SUNDAY;
                default:
                    return null;
            }
        }

        public static Day daysForToggle(int dayNum) {
            switch (dayNum) {
                case 1:
                    return MONDAY;
                case 2:
                    return TUESDAY;
                case 3:
                    return WEDNESDAY;
                case 4:
                    return THURSDAY;
                case 5:
                    return FRIDAY;
                case 6:
                    return SATURDAY;
                case 7:
                    return SUNDAY;
                default:
                    return null;
            }
        }
    }
}
