package com.andrew.alarmclock.data.entities.api.news;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "pubDate", strict = false)
public class PubDate {
    @Text
    private String pubDate;

    public String getPubDate() {
        return pubDate;
    }
}
