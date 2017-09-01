package com.andrew.alarmclock.alarm.alarmClock.presentation;

import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.data.entities.Alarm;

import java.util.List;

public class AlarmClockHolderDelegate {

    private int[] stringIds = new int[]{
            R.string.item_check_box_day1,
            R.string.item_check_box_day2,
            R.string.item_check_box_day3,
            R.string.item_check_box_day4,
            R.string.item_check_box_day5,
            R.string.item_check_box_day6,
            R.string.item_check_box_day7,
    };

    public void setupToggles(
            Alarm alarm,
            List<ToggleButton> dayToggles,
            TextView daysTextView,
            AlarmClockAdapter.OnCheckBoxCheck onCheckBoxCheckListener) {

        if (alarm.getDays().size() == 7) {
            for (ToggleButton toggle : dayToggles) {
                toggle.setChecked(true);
            }
        } else {
            for (int i = 0; i < dayToggles.size(); i++) {
                ToggleButton toggleButton = dayToggles.get(i);
                if (alarm.getDays().contains(Alarm.Day.daysForToggle(i + 1))) {
                    toggleButton.setChecked(true);
                } else {
                    toggleButton.setChecked(false);
                }
            }
        }

        for (int i = 0; i < dayToggles.size(); i++) {
            final int day = i + 1;
            dayToggles.get(i).setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    alarm.getDays().add(Alarm.Day.daysForToggle(day));
                } else {
                    alarm.getDays().remove(Alarm.Day.daysForToggle(day));
                }
                onCheckBoxCheckListener.onCheckBoxCheck(alarm);
                updateDaysTextView(alarm, daysTextView);
            });
        }
    }

    public void setupAlarmCheckBox(
            CheckBox alarmClockCheckBox,
            Alarm alarm,
            AlarmClockAdapter.OnCheckBoxCheck onCheckBoxCheckListener) {

        alarmClockCheckBox.setChecked(alarm.isOn());
        alarmClockCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            alarm.setOn(b);
            onCheckBoxCheckListener.onCheckBoxCheck(alarm);
        });
    }

    public void updateDaysTextView(Alarm alarm, TextView daysTextView) {
        daysTextView.setText("");
        if (alarm.getDays().size() == 7) {
            daysTextView.setText(daysTextView.getContext().getString(R.string.item_every_day));
            return;
        }

        if (alarm.getDays().size() == 0) daysTextView.append(daysTextView.getContext()
                .getString(R.string.item_no_day));

        for (Alarm.Day day : alarm.getDays()) {
            int dayNum = day.getDayNum();
            if (dayNum == 1) continue;
            daysTextView.append(daysTextView.getContext().getString(stringIds[dayNum - 2]) + " ");
        }

        if (alarm.getDays().contains(Alarm.Day.SUNDAY)) {
            daysTextView.append(daysTextView.getContext().getString(stringIds[6]) + " ");
        }
    }
}
