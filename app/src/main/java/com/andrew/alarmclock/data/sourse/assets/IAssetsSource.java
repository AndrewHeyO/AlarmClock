package com.andrew.alarmclock.data.sourse.assets;

import io.reactivex.Flowable;

public interface IAssetsSource {
    Flowable<String> getFeedsUrls(String assets);
}
