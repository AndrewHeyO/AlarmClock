package com.andrew.alarmclock.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.andrew.alarmclock.R;

public class Utils {

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static String formatFeedUrl(String feed) {
        String url = feed.split("://")[1].split("/")[0];
        if(url.contains("www.")) {
            url = url.substring(4);
        }
        return url;
    }

    public static int getNextDayNum(int dayNum) {
        dayNum += 1;
        if(dayNum > 7) dayNum = 0;
        return dayNum;
    }

    public static int getDrawableIdByWeatherCode(String weatherCode) {
        switch (weatherCode) {
            case "01d":
            case "01n":
                return R.drawable.weather_clear_sky;
            case "02d":
            case "02n":
                return R.drawable.weather_few_clouds;
            case "03d":
            case "03n":
                return R.drawable.weather_scattered_clouds;
            case "04d":
            case "04n":
                return R.drawable.weather_broken_clouds;
            case "09d":
            case "09n":
                return R.drawable.weather_shower_rain;
            case "10d":
            case "10n":
                return R.drawable.weather_rain;
            case "11d":
            case "11n":
                return R.drawable.weather_thunderstorm;
            case "13d":
            case "13n":
                return R.drawable.weather_snow;
            case "50d":
            case "50n":
                return R.drawable.weather_mist;
            default:
                return 0;
        }
    }
}
