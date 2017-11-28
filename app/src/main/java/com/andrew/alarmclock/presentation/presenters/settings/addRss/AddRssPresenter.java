package com.andrew.alarmclock.presentation.presenters.settings.addRss;

import com.andrew.alarmclock.data.entities.Feed;
import com.andrew.alarmclock.data.error.ExistUrlError;
import com.andrew.alarmclock.data.error.NetworkError;
import com.andrew.alarmclock.di.InjectHolder;
import com.andrew.alarmclock.business.settings.IRssSettingsInteractor;
import com.andrew.alarmclock.presentation.view.settings.addRss.AddRssView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.simpleframework.xml.core.PersistenceException;
import org.xmlpull.v1.XmlPullParserException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

@InjectViewState
public class AddRssPresenter extends MvpPresenter<AddRssView> {

    @Inject
    IRssSettingsInteractor interactor;

    public AddRssPresenter() {
        InjectHolder.getInstance().buildPagerComponent().inject(this);
    }

    public void onSaveNewFeed(String url) {
        interactor.offlineValidateUrl(url)
                .subscribe(val -> {
                    if(val) {
                        getViewState().showLoading();
                        onSaveNewFeedValidateOfflinePassed(url);
                    } else {
                        getViewState().showOfflineParseError();
                    }
                });
    }

    private void onSaveNewFeedValidateOfflinePassed(String url) {
        interactor.insertFeed(new Feed(url))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(val -> getViewState().closeDialog(),
                        error -> {
                            if (error instanceof SocketTimeoutException) {
                                getViewState().showTimeoutError();
                                return;
                            }
                            if (error instanceof HttpException ||
                                    error instanceof UnknownHostException) {
                                getViewState().showBadUrlError();
                                return;
                            }
                            if (error.getCause() instanceof PersistenceException ||
                                    error.getCause() instanceof XmlPullParserException) {
                                getViewState().showParseError();
                                return;
                            }
                            if (error instanceof NetworkError) {
                                getViewState().showNoInternetError();
                                return;
                            }
                            if (error instanceof ExistUrlError) {
                                getViewState().showExistsUrlError();
                                return;
                            }
                            getViewState().showWtfException(error.getMessage());
                        });
    }
}
