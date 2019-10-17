package com.cn.ql.frame.net;

import com.cn.ql.frame.utils.Utils;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * create by AXW on 2018/11/15
 * 注：
 */
public class RetrofitTool {

    /**
     * 线程调度
     */
    protected static  <T> ObservableTransformer<T, T> compose(final int urlId, final LifecycleTransformer<T> lifecycle, final HttpResultListener listener) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                // 可添加网络连接判断等
                                if (!Utils.isNetworkAvailable(Utils.getContext())) {
                                    if (listener != null){
                                        listener.onNetWorkError(urlId);
                                    }
                                    return ;
                                }
                                if (listener != null){
                                    listener.onStart(urlId);
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(lifecycle);
            }
        };
    }

    public static void POST(int urlId, Observable<JsonElement> observable, LifecycleProvider rxLifecycle, final HttpResultListener listener) {
        ObservableTransformer transformer = compose(urlId, rxLifecycle.bindToLifecycle(), listener);
        observable.compose(transformer).subscribe(new BaseObserver(urlId) {

            @Override
            protected void onHandlerError(Throwable e, int urlId) {
                if (listener != null){
                    listener.onEnd(urlId);
                    listener.onError(urlId, e);
                }
            }

            @Override
            protected void onHandlerSuccess(JsonElement value, int urlId) {
                if (listener != null){
                    listener.onEnd(urlId);
                    listener.onSuccess(urlId, value);
                }
            }
        });
    }
}
