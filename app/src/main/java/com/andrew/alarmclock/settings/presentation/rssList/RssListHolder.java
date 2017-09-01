package com.andrew.alarmclock.settings.presentation.rssList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.data.entities.Feed;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RssListHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_rss_list_rss_text_view)
    TextView urlTextView;

    @BindView(R.id.item_rss_list_delete_button)
    ImageButton deleteButton;

    @BindView(R.id.item_dialog_settings_news_separator)
    View separator;

    public RssListHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Feed feed, RssListDialog.OnDeleteClickListener listener, boolean isLast) {
        urlTextView.setText(feed.getUrl());
        deleteButton.setOnClickListener(view -> listener.onDeleteClick(feed));
        if(isLast) {
            separator.setVisibility(View.GONE);
        }
    }
}
