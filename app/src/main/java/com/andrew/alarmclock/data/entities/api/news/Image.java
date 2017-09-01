package com.andrew.alarmclock.data.entities.api.news;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "image", strict = false)
public class Image {
    @Element(name = "url")
    private String url;

    public String getUrl ()
    {
        return url;
    }
}
