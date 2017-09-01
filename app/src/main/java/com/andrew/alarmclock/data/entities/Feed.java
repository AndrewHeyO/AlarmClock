package com.andrew.alarmclock.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Feed {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String url;

    public Feed(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }
}
