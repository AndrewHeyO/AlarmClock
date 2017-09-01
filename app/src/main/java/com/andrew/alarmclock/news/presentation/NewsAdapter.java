package com.andrew.alarmclock.news.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.data.entities.NewsItem;
import com.andrew.alarmclock.data.entities.api.weather.Forecast;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_WEATHER = 0;
    private static final int VIEW_TYPE_NEWS = 1;
    private static final int VIEW_TYPE_ERROR = 2;

    private List<NewsItem> newsItems;

    private boolean isError;
    private boolean isPermissionsError;
    private OnPermissionsListener askPermissionsListener;
    private String errorMsg;

    private Forecast forecast;

    public void setWeather(Forecast forecast) {
        this.forecast = forecast;
        notifyDataSetChanged();
    }

    public void setPermissionsListener(OnPermissionsListener askPermissionsListener) {
        this.askPermissionsListener = askPermissionsListener;
    }

    public void removeError() {
        isError = false;
        notifyDataSetChanged();
    }

    public void setError(String msg) {
        isPermissionsError = false;
        isError = true;
        errorMsg = msg;
        notifyDataSetChanged();
    }

    public void setPermissionsError(String msg) {
        isPermissionsError = true;
        isError = true;
        errorMsg = msg;
        notifyDataSetChanged();
    }

    public NewsAdapter() {
        newsItems = new ArrayList<>();
    }

    public void setNewsItems(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_WEATHER:
                return new WeatherHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_weather, parent, false));
            case VIEW_TYPE_NEWS:
                return new NewsHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_news, parent, false));
            case VIEW_TYPE_ERROR:
                return new WeatherErrorHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_weather_error, parent, false));
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_WEATHER:
                ((WeatherHolder) holder).bind(forecast);
                break;
            case VIEW_TYPE_NEWS:
                ((NewsHolder) holder).bind(newsItems.get(position - 1));
                break;
            case VIEW_TYPE_ERROR:
                if(!isPermissionsError) {
                    ((WeatherErrorHolder) holder).bind(errorMsg);
                } else {
                    ((WeatherErrorHolder) holder).bind(errorMsg, askPermissionsListener);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return newsItems != null
                ? newsItems.size() + (forecast != null || isError ? 1 : 0)
                : (forecast != null || isError ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            if (isError) {
                return VIEW_TYPE_ERROR;
            } else {
                return VIEW_TYPE_WEATHER;
            }
        } else {
            return VIEW_TYPE_NEWS;
        }
    }

    public interface OnPermissionsListener {
        void onPermissions();
    }
}
