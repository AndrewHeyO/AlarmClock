package com.andrew.alarmclock.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.andrew.alarmclock.data.error.NetworkError;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class NetworkInterceptor implements Interceptor {

    private Context context;

    public NetworkInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if(!Utils.isInternetConnected(context)) {
            throw new NetworkError();
        }
        return chain.proceed(chain.request());
    }
}
