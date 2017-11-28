package com.andrew.alarmclock.data.service.disturb;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class DisturbServiceListener {
    private final PublishSubject<Boolean> subject;

    public DisturbServiceListener() {
        subject = PublishSubject.create();
    }

    void onDisturbChange(boolean shouldDisturb) {
        subject.onNext(shouldDisturb);
    }

    public Observable<Boolean> observeDisturbChanges() {
        return subject;
    }
}
