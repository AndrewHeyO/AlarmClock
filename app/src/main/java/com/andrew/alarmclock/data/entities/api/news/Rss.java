package com.andrew.alarmclock.data.entities.api.news;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Rss {
    @Element
    private Channel channel;

    @Attribute
    private String version;

    public Channel getChannel () {
        return channel;
    }
}
