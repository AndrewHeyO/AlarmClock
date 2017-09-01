package com.andrew.alarmclock.data.entities.api.news;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "item", strict = false)
public class RssItem {

    @Element(name = "title")
    private String title;

    @ElementList(name = "description", required = false, inline = true)
    private List<Description> descriptions;

    @ElementList(name = "link", inline = true)
    private List<Link> link;

    @ElementList(name = "pubDate", inline=true)
    private List<PubDate> pubDate;

    @ElementList(name = "category", inline = true, required = false)
    private List<String> categories;

    public String getPubDate ()
    {
        return pubDate.get(0).getPubDate();
    }

    public String getTitle ()
    {
        return title;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getDescription () {
        return descriptions.get(0).getDescription();
    }

    public List<Link> getLink () {
        return link;
    }



    private String logoUrl;

    private String feed;

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }
}
