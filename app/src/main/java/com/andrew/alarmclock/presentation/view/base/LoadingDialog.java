package com.andrew.alarmclock.presentation.view.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.andrew.alarmclock.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingDialog extends DialogFragment {

    @BindView(R.id.dialog_loading_progress_bar)
    ProgressBar progressBar;

    public static LoadingDialog getInstance() {
        return new LoadingDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (isInLayout())
            return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_loading, null);

        builder.setView(view);

        ButterKnife.bind(this, view);

        progressBar.setIndeterminate(true);
        setCancelable(false);

        return builder.create();
    }
}
