package com.andrew.alarmclock.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "news")
public class NewsItem {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;

    private String category;

    private String description;

    private Date pubDate;

    private String link;

    private String logoUrl;

    private String feed;

    public NewsItem(String title, String category, String description,
                    Date pubDate, String link, String logoUrl, String feed) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
        this.logoUrl = logoUrl;
        this.feed = feed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public String getLink() {
        return link;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getFeed() {
        return feed;
    }
}
