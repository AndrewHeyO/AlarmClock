package com.andrew.alarmclock.settings.presentation.addRss;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface AddRssView extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void showExistsUrlError();
    @StateStrategyType(SkipStrategy.class)
    void showTimeoutError();
    @StateStrategyType(SkipStrategy.class)
    void showBadUrlError();
    @StateStrategyType(SkipStrategy.class)
    void showParseError();
    @StateStrategyType(SkipStrategy.class)
    void showOfflineParseError();
    @StateStrategyType(SkipStrategy.class)
    void showNoInternetError();
    @StateStrategyType(SkipStrategy.class)
    void showLoading();
    @StateStrategyType(SkipStrategy.class)
    void closeDialog();
    @StateStrategyType(SkipStrategy.class)
    void showWtfException(String error);
}
