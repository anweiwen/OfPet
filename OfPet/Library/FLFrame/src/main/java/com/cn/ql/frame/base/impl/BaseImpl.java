package com.cn.ql.frame.base.impl;

import android.util.Log;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author axw_an
 * @create on 2019/3/5
 * @refer url：
 * @description:
 * @update: axw_an:2019/3/5:
 */
public class BaseImpl {

    public void initBindToLifecycle(LifecycleProvider lifecycleProvider){
        // 当执行onDestory()时， 自动解除订阅
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.i("QA", "Unsubscribing subscription from onCreate()");
                    }
                })
                .compose(lifecycleProvider.<Long>bindToLifecycle())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long num) throws Exception {
                        Log.i("QA", "Started in onCreate(), running until onDestory(): " + num);
                    }
                });
    }

    public void initBindUntilEvent(LifecycleProvider lifecycleProvider){
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.i("QA", "Unsubscribing subscription from onResume()");
                    }
                })
                //bindUntilEvent()，内部传入指定生命周期参数
                .compose(lifecycleProvider.<Long>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long num) throws Exception {
                        Log.i("QA", "Started in onResume(), running until in onDestroy(): " + num);
                    }
                });
    }
}
