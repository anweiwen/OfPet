package com.cn.ql.frame.net;

import com.google.gson.JsonElement;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * BaseObserver
 * Created by jaycee on 2017/6/23.
 */
public abstract class BaseObserver implements Observer<JsonElement> {

    private int urlId;

    protected BaseObserver(int urlId) {
        this.urlId = urlId;
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(JsonElement value) {
        onHandlerSuccess(value, urlId);
    }

    @Override
    public void onError(Throwable e) {
        onHandlerError(e, urlId);
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onHandlerSuccess(JsonElement t, int urlId);
    protected abstract void onHandlerError(Throwable e, int urlId);
}
