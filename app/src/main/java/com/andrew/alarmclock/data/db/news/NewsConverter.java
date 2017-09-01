package com.andrew.alarmclock.data.db.news;

import android.arch.persistence.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsConverter {

    private SimpleDateFormat sdf =
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.getDefault());

    @TypeConverter
    public String toString(Date date) {
        return date == null ? null : sdf.format(date);
    }

    @TypeConverter
    public Date toDate(String date) {
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
