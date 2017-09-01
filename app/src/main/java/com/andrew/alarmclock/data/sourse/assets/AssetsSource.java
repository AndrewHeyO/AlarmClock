package com.andrew.alarmclock.data.sourse.assets;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class AssetsSource implements IAssetsSource {

    private AssetManager assetManager;

    @Inject
    public AssetsSource(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public Flowable<String> getFeedsUrls(String assets) {
        return Flowable.create(e -> {
            String str;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(assetManager.open(assets), "UTF-8"))) {
                while ((str = in.readLine()) != null) {
                    e.onNext(str);
                }
            } catch (IOException ex) {
                e.onError(ex);
            } finally {
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
    }
}
