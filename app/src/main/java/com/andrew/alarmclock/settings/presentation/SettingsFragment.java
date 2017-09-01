package com.andrew.alarmclock.settings.presentation;


import android.app.AlertDialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrew.alarmclock.BuildConfig;
import com.andrew.alarmclock.R;
import com.andrew.alarmclock.data.repository.sharedPreferences.ISharedPreferencesRepository;
import com.andrew.alarmclock.di.InjectHolder;
import com.andrew.alarmclock.settings.presentation.rssList.RssListDialog;
import com.andrew.alarmclock.utils.Constant;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.andrew.alarmclock.utils.Constant.REQUEST_CODE_RINGTONE;

public class SettingsFragment extends MvpAppCompatFragment implements SettingsView {

    @InjectPresenter
    SettingsPresenter presenter;

    @Inject
    ISharedPreferencesRepository repository;

    @BindView(R.id.settings_sound_text_view)
    TextView soundTextView;

    @BindView(R.id.settings_vibration_layout)
    LinearLayout vibrationLayout;

    @BindView(R.id.settings_vibration_check_box)
    CheckBox vibrationCheckBox;

    @BindView(R.id.settings_rss_text_view)
    TextView rssTextView;

    @BindView(R.id.settings_dark_theme_switch)
    SwitchCompat darkThemeSwitch;

    @BindView(R.id.settings_dark_theme_layout)
    LinearLayout currentThemeLayout;

    private boolean isRingtoneManagerOpened;

    public static SettingsFragment getInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        InjectHolder.getInstance().buildPagerComponent().inject(this);

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ButterKnife.bind(this, view);

        repository.isDarkTheme()
                .subscribe(val -> darkThemeSwitch.setChecked(val));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isRingtoneManagerOpened = false;
        if (requestCode == REQUEST_CODE_RINGTONE) {
            if (resultCode == RESULT_OK) {
                RingtoneManager.setActualDefaultRingtoneUri(getContext(),
                        RingtoneManager.TYPE_ALARM,
                        data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI));
            }
        }
    }


    @OnClick(R.id.settings_sound_text_view)
    public void onSoundClick() {
        if(isRingtoneManagerOpened) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(getContext())) {
                openRingtoneSettings();
            } else {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setMessage(getString(R.string.write_settings_info))
                        .setNegativeButton(getString(R.string.dialog_cancel),
                                (dialogInterface, i) -> dialogInterface.dismiss())
                        .setPositiveButton(getString(R.string.dialog_go), (dialogInterface, i) -> {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                                    Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                            startActivity(intent);
                        })
                        .create();
                dialog.show();
            }
        } else {
            openRingtoneSettings();
        }
    }

    @OnClick(R.id.settings_vibration_layout)
    public void onVibrationClick() {
        boolean isVibrate = !vibrationCheckBox.isChecked();
        presenter.onWriteVibrationToPreferences(isVibrate);
        vibrationCheckBox.setChecked(isVibrate);
    }

    @OnClick(R.id.settings_vibration_check_box)
    public void onVibrationCheckBoxChecked() {
        presenter.onWriteVibrationToPreferences(vibrationCheckBox.isChecked());
    }

    @OnClick(R.id.settings_rss_text_view)
    public void onRssTextViewClick() {
        RssListDialog rssListDialog = RssListDialog.getInstance();
        rssListDialog.show(getActivity().getFragmentManager(), "rssList");
    }

    @OnClick(R.id.settings_dark_theme_layout)
    public void onDarkThemeClick() {
        darkThemeSwitch.setChecked(!darkThemeSwitch.isChecked());
        handleSwitchThemeAction();
    }

    @OnClick(R.id.settings_dark_theme_switch)
    public void onDarkThemeSwitchCheck() {
        handleSwitchThemeAction();
    }

    @Override
    public void setVibrationCheckBox(boolean isChecked) {
        vibrationCheckBox.setChecked(isChecked);
    }

    private void openRingtoneSettings() {
        isRingtoneManagerOpened = true;
        final Uri currentTone = RingtoneManager
                .getActualDefaultRingtoneUri(getContext(), RingtoneManager.TYPE_ALARM);

        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE,
                getString(R.string.settings_sound_ringtone_pick));
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentTone);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);

        startActivityForResult(intent, Constant.REQUEST_CODE_RINGTONE);
    }

    private void handleSwitchThemeAction() {
        presenter.onWriteDarkThemeToPreferences(darkThemeSwitch.isChecked());

        darkThemeSwitch.postDelayed(() -> getActivity().recreate(), 200);
    }
}
