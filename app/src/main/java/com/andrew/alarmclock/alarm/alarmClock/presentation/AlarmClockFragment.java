package com.andrew.alarmclock.alarm.alarmClock.presentation;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.alarm.disturb.DisturbServiceListener;
import com.andrew.alarmclock.data.entities.Alarm;
import com.andrew.alarmclock.di.InjectHolder;
import com.andrew.alarmclock.utils.Constant;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class AlarmClockFragment extends MvpAppCompatFragment implements AlarmClockView {

    private final static String TAG_TIME_PICKER_DIALOG_ADD = "TPDA";
    private final static String TAG_TIME_PICKER_DIALOG_UPDATE = "TPDU";

    @InjectPresenter
    AlarmClockPresenter presenter;

    @Inject
    DisturbServiceListener listener;

    @BindView(R.id.alarm_clock_nested_scroll_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.alarm_clock_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.alarm_clock_fab_disturb)
    FloatingActionButton fabDisturb;

    private AlarmClockAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private Parcelable layoutManagerState;

    private TimePickerDialog timePickerDialogAdd;
    private TimePickerDialog timePickerDialogUpdate;

    private Alarm savedAlarm;

    private boolean isTimePickerDialogAddShown;
    private boolean isTimePickerDialogUpdateShown;

    private Disposable listenerDisposable;

    public static AlarmClockFragment getInstance() {
        return new AlarmClockFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_clock, container, false);
        ButterKnife.bind(this, view);

        InjectHolder.getInstance().getAppComponent().inject(this);

        setupRecyclerView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        listenerDisposable = listener.observeDisturbChanges()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setFabDisturb);
    }

    @Override
    public void onStop() {
        super.onStop();
        listenerDisposable.dispose();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onSetupFabDisturb();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        layoutManagerState = linearLayoutManager.onSaveInstanceState();
        outState.putParcelable(Constant.TAG_LAYOUT_MANAGER, layoutManagerState);

        if (timePickerDialogAdd != null) {
            outState.putBundle(TAG_TIME_PICKER_DIALOG_ADD, timePickerDialogAdd.onSaveInstanceState());
        }
        if (timePickerDialogUpdate != null) {
            outState.putBundle(TAG_TIME_PICKER_DIALOG_UPDATE, timePickerDialogUpdate.onSaveInstanceState());
        }
        outState.putIntArray(Constant.TAG_POSITION,
                new int[]{nestedScrollView.getScrollX(), nestedScrollView.getScrollY()});
        outState.putSerializable(Constant.TAG_ALARM, savedAlarm);
        outState.putBoolean(Constant.TAG_TIME_PICKER_ADD, isTimePickerDialogAddShown);
        outState.putBoolean(Constant.TAG_TIME_PICKER_UPDATE, isTimePickerDialogUpdateShown);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            layoutManagerState = savedInstanceState.getParcelable(Constant.TAG_LAYOUT_MANAGER);
            linearLayoutManager.onRestoreInstanceState(layoutManagerState);

            final int[] position = savedInstanceState.getIntArray(Constant.TAG_POSITION);
            if (position != null)
                nestedScrollView.post(() -> nestedScrollView.scrollTo(position[0], position[1]));

            if (savedInstanceState.getBoolean(Constant.TAG_TIME_PICKER_ADD)) {
                showAddTimePickerDialog();
                timePickerDialogAdd.onRestoreInstanceState(
                        savedInstanceState.getBundle(TAG_TIME_PICKER_DIALOG_ADD));
            }

            if (savedInstanceState.getBoolean(Constant.TAG_TIME_PICKER_UPDATE)) {
                showUpdateTimePickerDialog((Alarm) savedInstanceState
                        .getSerializable(Constant.TAG_ALARM));
                timePickerDialogUpdate.onRestoreInstanceState(
                        savedInstanceState.getBundle(TAG_TIME_PICKER_DIALOG_UPDATE));

            }
        }
    }

    @OnClick(R.id.alarm_clock_fab)
    public void onFabClick() {
        showAddTimePickerDialog();
    }

    @OnClick(R.id.alarm_clock_fab_disturb)
    public void onFabDisturbClick() {
        presenter.onFabDisturbClick();
    }

    @Override
    public void onAlarmCreated(Alarm alarm) {
        adapter.addAlarm(alarm);
        recyclerView.smoothScrollToPosition(adapter.getPositionByValue(alarm));
    }

    @Override
    public void onAlarmUpdated(Alarm alarm) {
        adapter.updateAlarm(alarm);
    }

    @Override
    public void onAlarmRemoved(Alarm alarm) {
        adapter.deleteAlarm(alarm);
    }

    @Override
    public void onAlarmsGot(List<Alarm> alarms) {
        adapter.setData(alarms);
    }

    @Override
    public void setFabDisturb(boolean shouldDisturb) {
        if (shouldDisturb) {
            fabDisturb.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fab_bell_on));
        } else {
            fabDisturb.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fab_bell_off));
        }
    }

    private void setupRecyclerView() {
        adapter = new AlarmClockAdapter();
        adapter.setOnTimeTextViewClick(this::showUpdateTimePickerDialog);
        adapter.setOnCheckBoxCheck(alarm -> presenter.onUpdateAlarm(alarm));
        adapter.setOnDeleteClick(alarm -> presenter.onDeleteAlarm(alarm));
        adapter.setOnOpenedItemListener((pos) -> recyclerView.smoothScrollToPosition(pos));

        linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void showAddTimePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int hoursBefore = c.get(Calendar.HOUR_OF_DAY);
        int minutesBefore = c.get(Calendar.MINUTE);

        timePickerDialogAdd = new TimePickerDialog(
                getActivity(),
                (timePicker, hours, minutes) -> {
                    if (timePicker.isShown()) {
                        presenter.onInsertAlarm(hours, minutes);
                        isTimePickerDialogAddShown = false;
                    }
                },
                hoursBefore,
                minutesBefore,
                DateFormat.is24HourFormat(getActivity()));

        timePickerDialogAdd.setOnShowListener(dialogInterface -> isTimePickerDialogAddShown = true);
        timePickerDialogAdd.setOnCancelListener(dialogInterface -> isTimePickerDialogAddShown = false);
        timePickerDialogAdd.setOnDismissListener(dialogInterface -> isTimePickerDialogAddShown = false);
        timePickerDialogAdd.show();
    }

    private void showUpdateTimePickerDialog(Alarm alarm) {
        savedAlarm = alarm;

        timePickerDialogUpdate = new TimePickerDialog(
                getContext(),
                (timePicker, hours, minutes) -> {
                    if (timePicker.isShown()) {
                        alarm.setHours(hours);
                        alarm.setMinutes(minutes);
                        presenter.onUpdateAlarm(alarm);
                    }
                    isTimePickerDialogUpdateShown = false;
                },
                alarm.getHours(),
                alarm.getMinutes(),
                DateFormat.is24HourFormat(getContext()));

        timePickerDialogUpdate.setOnShowListener(dialogInterface -> isTimePickerDialogUpdateShown = true);
        timePickerDialogUpdate.setOnCancelListener(dialogInterface -> isTimePickerDialogUpdateShown = false);
        timePickerDialogUpdate.setOnDismissListener(dialogInterface -> isTimePickerDialogUpdateShown = false);
        timePickerDialogUpdate.show();
    }
}
