package com.andrew.alarmclock.presentation.view.alarmClock;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.data.entities.Alarm;
import com.andrew.alarmclock.utils.comparator.AlarmComparator;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.Utils;

import java.util.Collections;
import java.util.List;


public class AlarmClockAdapter extends RecyclerView.Adapter<AlarmClockHolder> {

    private List<Alarm> alarms;

    private OnDeleteClick onDeleteClick;
    private OnTimeTextViewClick onTimeTextViewClick;
    private OnCheckBoxCheck onCheckBoxCheck;
    private OnOpenedItemListener onOpenedItemListener;

    public int getPositionByValue(Alarm alarm) {
        return alarms.indexOf(alarm);
    }

    public void setOnDeleteClick(OnDeleteClick onDeleteClick) {
        this.onDeleteClick = onDeleteClick;
    }

    public void setOnTimeTextViewClick(OnTimeTextViewClick onTimeTextViewClick) {
        this.onTimeTextViewClick = onTimeTextViewClick;
    }

    public void setOnCheckBoxCheck(OnCheckBoxCheck onCheckBoxCheck) {
        this.onCheckBoxCheck = onCheckBoxCheck;
    }

    public void setOnOpenedItemListener(OnOpenedItemListener onOpenedItemListener) {
        this.onOpenedItemListener = onOpenedItemListener;
    }

    @Override
    public AlarmClockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlarmClockHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alarm_clock, parent, false));
    }

    @Override
    public void onBindViewHolder(AlarmClockHolder holder, int position) {
        holder.bind(alarms.get(position),
                onTimeTextViewClick,
                onCheckBoxCheck,
                onDeleteClick);

        holder.innerLayout.setListener(new ExpandableLayoutListenerAdapter() {

            @Override
            public void onPreOpen() {
                createRotateAnimator(holder.openImageView, 0f, 180f).start();
                alarms.get(position).setExpanded(true);
            }

            @Override
            public void onOpened() {
                onOpenedItemListener.onOpenedItemListener(position);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.openImageView, 180f, 0f).start();
                alarms.get(position).setExpanded(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alarms == null ? 0 : alarms.size();
    }

    public void setData(List<Alarm> alarms) {
        this.alarms = alarms;
        Collections.sort(alarms, new AlarmComparator());
        notifyDataSetChanged();
    }

    public void addAlarm(Alarm alarm) {
        alarms.add(alarm);
        Collections.sort(alarms, new AlarmComparator());
        notifyItemInserted(alarms.indexOf(alarm));
    }

    public void updateAlarm(Alarm alarm) {
        alarms.set(alarms.indexOf(alarm), alarm);
        Collections.sort(alarms, new AlarmComparator());
        notifyDataSetChanged();
    }

    public void deleteAlarm(Alarm alarm) {
        alarms.remove(alarm);
        notifyDataSetChanged();
    }

    public interface OnTimeTextViewClick {
        void onTimeTextViewClick(Alarm alarm);
    }

    public interface OnDeleteClick {
        void onDeleteButtonClick(Alarm alarm);
    }

    public interface OnCheckBoxCheck {
        void onCheckBoxCheck(Alarm alarm);
    }

    public interface OnOpenedItemListener {
        void onOpenedItemListener(int pos);
    }

    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(150);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}
