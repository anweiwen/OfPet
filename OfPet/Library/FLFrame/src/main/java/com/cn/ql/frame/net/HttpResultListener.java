package com.cn.ql.frame.net;

import com.google.gson.JsonElement;

/**
 * create by AXW on 2018/11/15
 * 注：
 */
public interface HttpResultListener {
    void onStart(int urlId);
    void onEnd(int urlId);
    void onNetWorkError(int urlId);
    void onSuccess(int urlId, JsonElement value);
    void onError(int urlId, Throwable e);
}
