package com.andrew.alarmclock.presentation.view.settings.rssList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.data.entities.Feed;

import java.util.List;

public class RssListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_RECYCLER = 1;
    private static final int VIEW_TYPE_EMPTY = 2;

    private List<Feed> feeds;

    private RssListDialog.OnDeleteClickListener listener;

    public RssListAdapter(RssListDialog.OnDeleteClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_RECYCLER:
                return new RssListHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_rss_list, parent, false));
            case VIEW_TYPE_EMPTY:
                return new RssListEmptyHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_rss_list_empty, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_RECYCLER:
                boolean isLast = feeds.size()-1 == position;
                ((RssListHolder) holder).bind(feeds.get(position), listener, isLast);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(feeds == null || feeds.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_RECYCLER;
        }
    }

    @Override
    public int getItemCount() {
        return (feeds == null || feeds.size() == 0) ? 1 : feeds.size();
    }

    public void setData(List<Feed> feeds) {
        this.feeds = feeds;
        notifyDataSetChanged();
    }

    public void removeItem(Feed feed) {
        feeds.remove(feed);
        notifyDataSetChanged();
    }
}
