package com.andrew.alarmclock.utils.stringManager;

import android.content.Context;

import com.andrew.alarmclock.R;

import javax.inject.Inject;

public class StringManager implements IStringManager {

    private Context context;

    @Inject
    public StringManager(Context context) {
        this.context = context;
    }

    @Override
    public String getDayOfTheWeekById(int dayId) {
        switch (dayId) {
            case 1:
                return context.getString(R.string.sunday);
            case 2:
                return context.getString(R.string.monday);
            case 3:
                return context.getString(R.string.tuesday);
            case 4:
                return context.getString(R.string.wednesday);
            case 5:
                return context.getString(R.string.thursday);
            case 6:
                return context.getString(R.string.friday);
            case 7:
                return context.getString(R.string.saturday);
            case 8:
                return context.getString(R.string.sunday);
            default:
                return null;
        }
    }
}
