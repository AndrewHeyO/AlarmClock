package com.andrew.alarmclock.presentation.view.settings.rssList;

import com.andrew.alarmclock.data.entities.Feed;
import com.arellomobile.mvp.MvpView;

import java.util.List;

public interface RssListView extends MvpView {
    void setFeedsData(List<Feed> feedList);
}
