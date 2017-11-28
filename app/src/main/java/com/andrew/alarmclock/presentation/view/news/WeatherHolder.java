package com.andrew.alarmclock.presentation.view.news;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.data.entities.api.weather.Forecast;
import com.andrew.alarmclock.data.entities.api.weather.WeatherList;
import com.andrew.alarmclock.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_weather_image1)
    ImageView imageView1;

    @BindView(R.id.item_weather_date1)
    TextView dateTextView1;

    @BindView(R.id.item_weather_temp_day1)
    TextView dayTempTextView1;

    @BindView(R.id.item_weather_temp_night1)
    TextView nightTempTextView1;



    @BindView(R.id.item_weather_image2)
    ImageView imageView2;

    @BindView(R.id.item_weather_date2)
    TextView dateTextView2;

    @BindView(R.id.item_weather_temp_day2)
    TextView dayTempTextView2;

    @BindView(R.id.item_weather_temp_night2)
    TextView nightTempTextView2;



    @BindView(R.id.item_weather_image3)
    ImageView imageView3;

    @BindView(R.id.item_weather_date3)
    TextView dateTextView3;

    @BindView(R.id.item_weather_temp_day3)
    TextView dayTempTextView3;

    @BindView(R.id.item_weather_temp_night3)
    TextView nightTempTextView3;



    @BindView(R.id.item_weather_image4)
    ImageView imageView4;

    @BindView(R.id.item_weather_date4)
    TextView dateTextView4;

    @BindView(R.id.item_weather_temp_day4)
    TextView dayTempTextView4;

    @BindView(R.id.item_weather_temp_night4)
    TextView nightTempTextView4;



    @BindView(R.id.item_weather_image5)
    ImageView imageView5;

    @BindView(R.id.item_weather_date5)
    TextView dateTextView5;

    @BindView(R.id.item_weather_temp_day5)
    TextView dayTempTextView5;

    @BindView(R.id.item_weather_temp_night5)
    TextView nightTempTextView5;



    @BindView(R.id.item_weather_image6)
    ImageView imageView6;

    @BindView(R.id.item_weather_date6)
    TextView dateTextView6;

    @BindView(R.id.item_weather_temp_day6)
    TextView dayTempTextView6;

    @BindView(R.id.item_weather_temp_night6)
    TextView nightTempTextView6;



    @BindView(R.id.item_weather_image7)
    ImageView imageView7;

    @BindView(R.id.item_weather_date7)
    TextView dateTextView7;

    @BindView(R.id.item_weather_temp_day7)
    TextView dayTempTextView7;

    @BindView(R.id.item_weather_temp_night7)
    TextView nightTempTextView7;



    @BindView(R.id.item_weather_image8)
    ImageView imageView8;

    @BindView(R.id.item_weather_date8)
    TextView dateTextView8;

    @BindView(R.id.item_weather_temp_day8)
    TextView dayTempTextView8;

    @BindView(R.id.item_weather_temp_night8)
    TextView nightTempTextView8;

    public WeatherHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Forecast forecast) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM", Locale.getDefault());

        WeatherList weather1 = forecast.getList().get(0);

        imageView1.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(),
                Utils.getDrawableIdByWeatherCode(weather1.getWeather().get(0).getIcon())));

        dateTextView1.setText(simpleDateFormat.format(new Date((long) weather1.getDt() * 1000)));
        dayTempTextView1.setText(String.format(Locale.getDefault(), "%.0f °C", weather1.getTemp().getDay()));
        nightTempTextView1.setText(String.format(Locale.getDefault(), "%.0f °C", weather1.getTemp().getNight()));

        WeatherList weather2 = forecast.getList().get(1);

        imageView2.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(),
                Utils.getDrawableIdByWeatherCode(weather2.getWeather().get(0).getIcon())));

        dateTextView2.setText(simpleDateFormat.format(new Date((long) weather2.getDt() * 1000)));
        dayTempTextView2.setText(String.format(Locale.getDefault(), "%.0f °C", weather2.getTemp().getDay()));
        nightTempTextView2.setText(String.format(Locale.getDefault(), "%.0f °C", weather2.getTemp().getNight()));

        WeatherList weather3 = forecast.getList().get(2);

        imageView3.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(),
                Utils.getDrawableIdByWeatherCode(weather3.getWeather().get(0).getIcon())));

        dateTextView3.setText(simpleDateFormat.format(new Date((long) weather3.getDt() * 1000)));
        dayTempTextView3.setText(String.format(Locale.getDefault(), "%.0f °C", weather3.getTemp().getDay()));
        nightTempTextView3.setText(String.format(Locale.getDefault(), "%.0f °C", weather3.getTemp().getNight()));

        WeatherList weather4 = forecast.getList().get(3);

        imageView4.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(),
                Utils.getDrawableIdByWeatherCode(weather4.getWeather().get(0).getIcon())));

        dateTextView4.setText(simpleDateFormat.format(new Date((long) weather4.getDt() * 1000)));
        dayTempTextView4.setText(String.format(Locale.getDefault(), "%.0f °C", weather4.getTemp().getDay()));
        nightTempTextView4.setText(String.format(Locale.getDefault(), "%.0f °C", weather4.getTemp().getNight()));

        WeatherList weather5 = forecast.getList().get(4);

        imageView5.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(),
                Utils.getDrawableIdByWeatherCode(weather5.getWeather().get(0).getIcon())));

        dateTextView5.setText(simpleDateFormat.format(new Date((long) weather5.getDt() * 1000)));
        dayTempTextView5.setText(String.format(Locale.getDefault(), "%.0f °C", weather5.getTemp().getDay()));
        nightTempTextView5.setText(String.format(Locale.getDefault(), "%.0f °C", weather5.getTemp().getNight()));

        WeatherList weather6 = forecast.getList().get(5);

        imageView6.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(),
                Utils.getDrawableIdByWeatherCode(weather6.getWeather().get(0).getIcon())));

        dateTextView6.setText(simpleDateFormat.format(new Date((long) weather6.getDt() * 1000)));
        dayTempTextView6.setText(String.format(Locale.getDefault(), "%.0f °C", weather6.getTemp().getDay()));
        nightTempTextView6.setText(String.format(Locale.getDefault(), "%.0f °C", weather6.getTemp().getNight()));

        WeatherList weather7 = forecast.getList().get(6);

        imageView7.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(),
                Utils.getDrawableIdByWeatherCode(weather7.getWeather().get(0).getIcon())));

        dateTextView7.setText(simpleDateFormat.format(new Date((long) weather7.getDt() * 1000)));
        dayTempTextView7.setText(String.format(Locale.getDefault(), "%.0f °C", weather7.getTemp().getDay()));
        nightTempTextView7.setText(String.format(Locale.getDefault(), "%.0f °C", weather7.getTemp().getNight()));

        WeatherList weather8 = forecast.getList().get(7);

        imageView8.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(),
                Utils.getDrawableIdByWeatherCode(weather8.getWeather().get(0).getIcon())));

        dateTextView8.setText(simpleDateFormat.format(new Date((long) weather8.getDt() * 1000)));
        dayTempTextView8.setText(String.format(Locale.getDefault(), "%.0f °C", weather8.getTemp().getDay()));
        nightTempTextView8.setText(String.format(Locale.getDefault(), "%.0f °C", weather8.getTemp().getNight()));
    }
}
