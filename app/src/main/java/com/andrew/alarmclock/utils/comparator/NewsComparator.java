package com.andrew.alarmclock.utils.comparator;

import com.andrew.alarmclock.data.entities.NewsItem;

import java.util.Comparator;


public class NewsComparator implements Comparator<NewsItem> {
    @Override
    public int compare(NewsItem newsItem, NewsItem t1) {
        return newsItem.getPubDate().compareTo(t1.getPubDate()) * -1;
    }
}
