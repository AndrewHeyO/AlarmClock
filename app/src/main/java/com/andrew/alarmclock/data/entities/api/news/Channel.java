package com.andrew.alarmclock.data.entities.api.news;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

import java.util.List;

@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom")
})
@Root(strict = false)
public class Channel {
    @ElementList(name = "link", inline = true)
    private List<Link> links;

    @ElementList(name = "rssItems", inline = true)
    private List<RssItem> rssItems;

    @Element(name = "image")
    private Image logo;

    public List<RssItem> getRssItems() {
        return rssItems;
    }

    public Image getLogo()
    {
        return logo;
    }

    public List<Link> getLinks() {
        return links;
    }
}
