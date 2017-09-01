package com.andrew.alarmclock.alarm.alarmWake.presentation;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.data.repository.sharedPreferences.ISharedPreferencesRepository;
import com.andrew.alarmclock.di.InjectHolder;
import com.andrew.alarmclock.news.presentation.NewsFragment;
import com.andrew.alarmclock.utils.Constant;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AlarmWakeActivity extends MvpAppCompatActivity implements AlarmWakeView {

    @InjectPresenter
    AlarmWakePresenter presenter;

    @Inject
    ISharedPreferencesRepository repository;

    @BindView(R.id.wake_relative_layout)
    RelativeLayout relativeLayout;

    @BindView(R.id.wake_frame_layout)
    FrameLayout frameLayout;

    @BindView(R.id.wake_text_view)
    TextView timeTextView;

    @BindView(R.id.wake_wake_up_button)
    Button wakeUpButton;

    @BindView(R.id.wake_five_minutes_button)
    Button fiveMinutesButton;

    @BindView(R.id.wake_exit_button)
    Button exitButton;

    private Vibrator vibrator;
    private MediaPlayer player;

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, AlarmWakeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.TAG_ALARM_ID, id);
        context.startActivity(intent);
    }

    @ProvidePresenter
    AlarmWakePresenter providePresenter() {
        return new AlarmWakePresenter(getIntent().getIntExtra(Constant.TAG_ALARM_ID, 0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InjectHolder.getInstance().buildWakeComponent().inject(this);
        repository.isDarkTheme().subscribe(val -> {
            if(val) {
                setTheme(R.style.AppTheme_Dark);
            } else {
                setTheme(R.style.AppTheme_Light);
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_wake);
        ButterKnife.bind(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        player = MediaPlayer.create(this, notification);
        player.setLooping(true);
        player.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constant.TAG_VISIBILITY, wakeUpButton.getVisibility());
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null) {
            if(savedInstanceState.getInt(Constant.TAG_VISIBILITY) == View.VISIBLE)
                return;

            wakeUpButton.setVisibility(View.GONE);
            fiveMinutesButton.setVisibility(View.GONE);
            exitButton.setVisibility(View.VISIBLE);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, (int) getResources()
                    .getDimension(R.dimen.alarm_wake_time_text_view_margin_top_after_anim), 0, 0);
            frameLayout.setLayoutParams(layoutParams);
        }
    }

    @OnClick(R.id.wake_wake_up_button)
    public void onWakeUpButtonClick(View view) {
        vibrator.cancel();
        player.stop();

        onWakeButtonClickAnimation();

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in, 0)
                .replace(R.id.wake_news_fragment, NewsFragment.getInstance()).commit();
    }

    @OnClick(R.id.wake_five_minutes_button)
    public void onFiveMinutesButtonClick(View view) {
        player.stop();
        presenter.onFiveMinutesClick();
    }

    @OnClick(R.id.wake_exit_button)
    public void onExitClick() {
        finish();
    }

    @Override
    public void onShowTime(String time) {
        timeTextView.setText(time);
    }

    @Override
    public void onFinishAfterAddingFiveMinutes() {
        vibrator.cancel();
        finish();
    }

    @Override
    public void handleVibration(boolean isVibrate) {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if(isVibrate) {
            long[] pattern = {0, 100, 100};

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                VibrationEffect effect = VibrationEffect.createWaveform(pattern, 0);
                vibrator.vibrate(effect);
            } else {
                vibrator.vibrate(pattern, 0);
            }
        }
    }

    private void onWakeButtonClickAnimation() {
        int[] location = new int[2];
        frameLayout.getLocationOnScreen(location);

        float distance = -location[1] + getResources()
                .getDimension(R.dimen.alarm_wake_time_text_view_margin_top_after_anim);

        frameLayout.animate()
                .translationY(distance)
                .setDuration(700)
                .start();

        exitButton.setVisibility(View.VISIBLE);

        AnimationSet alphaExitButton = new AnimationSet(false);
        alphaExitButton.addAnimation(new AlphaAnimation(0.0f, 1.0f));
        alphaExitButton.setDuration(400);

        exitButton.startAnimation(alphaExitButton);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(new TranslateAnimation(0, 0, 0, distance));
        set.addAnimation(new AlphaAnimation(1.0f, 0.0f));
        set.setDuration(400);
        wakeUpButton.startAnimation(set);
        fiveMinutesButton.startAnimation(set);

        wakeUpButton.setVisibility(View.GONE);
        fiveMinutesButton.setVisibility(View.GONE);
    }
}
