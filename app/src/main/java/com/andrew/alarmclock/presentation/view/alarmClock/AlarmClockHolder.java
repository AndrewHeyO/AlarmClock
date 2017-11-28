package com.andrew.alarmclock.presentation.view.alarmClock;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.data.entities.Alarm;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlarmClockHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_alarm_clock_time)
    TextView timeTextView;

    @BindView(R.id.item_alarm_clock_days)
    TextView daysTextView;

    @BindView(R.id.item_alarm_clock_check_box)
    CheckBox alarmClockCheckBox;

    @BindView(R.id.item_alarm_clock_inner)
    ExpandableLinearLayout innerLayout;

    @BindView(R.id.item_outer_layout)
    RelativeLayout outerLayout;

    @BindView(R.id.item_alarm_clock_delete_button)
    ImageButton deleteButton;

    @BindView(R.id.item_alarm_image_view)
    ImageView openImageView;

    @BindView(R.id.item_alarm_clock_day_1)
    ToggleButton day1Toggle;

    @BindView(R.id.item_alarm_clock_day_2)
    ToggleButton day2Toggle;

    @BindView(R.id.item_alarm_clock_day_3)
    ToggleButton day3Toggle;

    @BindView(R.id.item_alarm_clock_day_4)
    ToggleButton day4Toggle;

    @BindView(R.id.item_alarm_clock_day_5)
    ToggleButton day5Toggle;

    @BindView(R.id.item_alarm_clock_day_6)
    ToggleButton day6Toggle;

    @BindView(R.id.item_alarm_clock_day_7)
    ToggleButton day7Toggle;

    private List<ToggleButton> dayToggles;

    private AlarmClockHolderDelegate delegate;

    public AlarmClockHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        delegate = new AlarmClockHolderDelegate();

        dayToggles = Arrays.asList(
                day1Toggle,
                day2Toggle,
                day3Toggle,
                day4Toggle,
                day5Toggle,
                day6Toggle,
                day7Toggle
        );
    }

    public void bind(Alarm alarm,
                     AlarmClockAdapter.OnTimeTextViewClick onTimeTextViewClick,
                     AlarmClockAdapter.OnCheckBoxCheck onCheckBoxCheck,
                     AlarmClockAdapter.OnDeleteClick onDeleteClick) {

        setIsRecyclable(false);

        innerLayout.setInRecyclerView(true);
        innerLayout.setInterpolator(new AccelerateDecelerateInterpolator());
        innerLayout.setExpanded(alarm.isExpanded());

        openImageView.setRotation(alarm.isExpanded() ? 180f : 0f);
        outerLayout.setOnClickListener(view -> innerLayout.toggle());

        deleteButton.setOnClickListener(v -> onDeleteClick.onDeleteButtonClick(alarm));

        timeTextView.setText(String.format("%02d:%02d", alarm.getHours(), alarm.getMinutes()));
        timeTextView.setOnClickListener(v -> onTimeTextViewClick.onTimeTextViewClick(alarm));

        alarmClockCheckBox.setChecked(alarm.isOn());
        alarmClockCheckBox.setOnCheckedChangeListener((compoundButton, b) -> alarm.setOn(b));

        delegate.setupToggles(alarm, dayToggles, daysTextView, onCheckBoxCheck);

        delegate.setupAlarmCheckBox(alarmClockCheckBox, alarm, onCheckBoxCheck);

        delegate.updateDaysTextView(alarm, daysTextView);
    }
}
