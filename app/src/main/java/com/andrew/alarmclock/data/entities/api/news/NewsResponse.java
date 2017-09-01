package com.andrew.alarmclock.data.entities.api.news;

import com.andrew.alarmclock.data.entities.NewsItem;
import com.andrew.alarmclock.utils.comparator.NewsComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewsResponse {
    private List<NewsItem> newsItems;
    private Set<Throwable> errors;

    public NewsResponse(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
        Collections.sort(newsItems, new NewsComparator());
        errors = new HashSet<>();
    }

    public NewsResponse(Throwable errors) {
        this.errors = Collections.singleton(errors);
        newsItems = new ArrayList<>();
    }

    public NewsResponse(List<NewsItem> newsItems, Set<Throwable> errors) {
        this.newsItems = newsItems;
        Collections.sort(newsItems, new NewsComparator());
        this.errors = errors;
    }

    public NewsResponse(Set<Throwable> listExceptions) {
        this.errors = listExceptions;
        newsItems = new ArrayList<>();
    }

    public List<NewsItem> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
    }

    public Set<Throwable> getErrors() {
        return errors;
    }

    public void setErrors(Set<Throwable> errors) {
        this.errors = errors;
    }
}
