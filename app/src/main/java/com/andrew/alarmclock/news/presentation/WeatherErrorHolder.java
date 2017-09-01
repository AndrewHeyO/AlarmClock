package com.andrew.alarmclock.news.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andrew.alarmclock.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherErrorHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_weather_error_text_view)
    TextView errorTextView;

    @BindView(R.id.item_weather_error_button)
    Button errorButton;

    public WeatherErrorHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(String errorMsg) {
        errorTextView.setText(errorMsg);
        errorButton.setVisibility(View.GONE);
    }

    public void bind(String errorMsg, NewsAdapter.OnPermissionsListener listener) {
        errorTextView.setText(errorMsg);
        errorButton.setOnClickListener(v -> listener.onPermissions());
        errorButton.setVisibility(View.VISIBLE);
    }
}
