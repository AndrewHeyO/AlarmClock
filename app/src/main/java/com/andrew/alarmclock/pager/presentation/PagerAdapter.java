package com.andrew.alarmclock.pager.presentation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andrew.alarmclock.alarm.alarmClock.presentation.AlarmClockFragment;
import com.andrew.alarmclock.news.presentation.NewsFragment;
import com.andrew.alarmclock.settings.presentation.SettingsFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private static final int PAGES_COUNT = 3;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AlarmClockFragment.getInstance();
            case 1:
                return NewsFragment.getInstance();
            case 2:
                return SettingsFragment.getInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGES_COUNT;
    }
}
