package com.andrew.alarmclock.data.entities.api.news;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "link", strict = false)
public class Link {
    @Text(required = false)
    public String link;

    public String getLink() {
        return link;
    }
}
