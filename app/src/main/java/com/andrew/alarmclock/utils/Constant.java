package com.andrew.alarmclock.utils;

public class Constant {

    public static final String WEATHER_API_KEY = "baa13f39a7fc4bd23346f7f6522a1b07";
    public static final String WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/";

    public static final String URL_PATTERN = "(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?";

    public static final String SIMPLE_DATE_FORMATTER = "HH:mm dd.MM.yy";

    public static final String TAG_FIRST_LAUNCH = "FIRST_LAUNCH";
    public static final String TAG_VIBRATION = "VIBRATION";
    public static final String TAG_VISIBILITY = "VISIBILITY";
    public static final String TAG_ALARM_ID = "ALARM_ID";
    public static final String TAG_LAYOUT_MANAGER = "LLM";
    public static final String TAG_POSITION = "POS";
    public static final String TAG_TIME_PICKER_ADD = "TP_ADD";
    public static final String TAG_TIME_PICKER_UPDATE = "TP_UPDATE";
    public static final String TAG_ALARM = "ALARM";
    public static final String TAG_REQUEST_CODE = "REQUEST_CODE";
    public static final String TAG_SHOULD_DISTURB = "SHOULD_DISTURB";
    public static final String TAG_LAST_ALARM_TIME = "LAST_ALARM_TIME";
    public static final String TAG_DARK_THEME = "DARK_THEME";
    public static final String TAG_IS_DELETED_NOTIFICATION = "IS_DELETED_NOTIFICATION";

    public static final int REQUEST_CODE_RINGTONE = 2;

    public static final int NOTIFICATION_ID = 4;
}
