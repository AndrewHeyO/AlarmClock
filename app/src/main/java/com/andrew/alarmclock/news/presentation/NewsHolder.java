package com.andrew.alarmclock.news.presentation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.data.entities.NewsItem;
import com.andrew.alarmclock.utils.Constant;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_news_card_view)
    CardView cardView;

    @BindView(R.id.item_news_logo)
    ImageView logoImageView;

    @BindView(R.id.item_news_title)
    TextView titleTextView;

    @BindView(R.id.item_news_desc)
    TextView descriptionTextView;

    @BindView(R.id.item_news_category)
    TextView categoryTextView;

    public NewsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(NewsItem newsItem) {
        Glide.with(itemView.getContext())
                .load(newsItem.getLogoUrl())
                .into(logoImageView);

        cardView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.getLink()));
            itemView.getContext().startActivity(intent);
        });

        titleTextView.setText(newsItem.getTitle());
        descriptionTextView.setText(newsItem.getDescription());

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMATTER,
                Locale.getDefault());
        categoryTextView.setText(sdf.format(newsItem.getPubDate()) + " ");

        if (!(newsItem.getCategory() == null)) {
            categoryTextView.append(newsItem.getCategory());
        }
    }
}
