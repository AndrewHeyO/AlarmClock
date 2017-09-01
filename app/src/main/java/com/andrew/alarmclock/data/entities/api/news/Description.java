package com.andrew.alarmclock.data.entities.api.news;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "description", strict = false)
public class Description {
    @Text(required = false)
    public String description;

    public String getDescription() {
        return description;
    }
}
