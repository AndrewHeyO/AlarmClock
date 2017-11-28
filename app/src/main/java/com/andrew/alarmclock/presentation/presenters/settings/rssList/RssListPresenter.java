package com.andrew.alarmclock.presentation.presenters.settings.rssList;

import com.andrew.alarmclock.data.entities.Feed;
import com.andrew.alarmclock.di.InjectHolder;
import com.andrew.alarmclock.business.settings.IRssSettingsInteractor;
import com.andrew.alarmclock.presentation.view.settings.rssList.RssListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class RssListPresenter extends MvpPresenter<RssListView> {

    @Inject
    IRssSettingsInteractor interactor;

    public RssListPresenter() {
        InjectHolder.getInstance().buildPagerComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        onGetAllFeeds();
    }

    public void onGetAllFeeds() {
        interactor.getAllFeeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedList -> getViewState().setFeedsData(feedList));
    }

    public void onDeleteFeed(Feed feed) {
        interactor.deleteFeed(feed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
