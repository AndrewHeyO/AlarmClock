package com.andrew.alarmclock.presentation.presenters.base;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<T extends MvpView> extends MvpPresenter<T> {
    private CompositeDisposable disposables;

    public void addDisposables(Disposable disposable) {
        if(disposables == null) {
            disposables = new CompositeDisposable();
        }

        disposables.add(disposable);
    }

    public void clearDisposables() {
        if(disposables != null) disposables.clear();
    }

    @Override
    public void onDestroy() {
        clearDisposables();
    }
}
