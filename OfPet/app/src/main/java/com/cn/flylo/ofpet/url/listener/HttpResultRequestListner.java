package com.cn.flylo.ofpet.url.listener;

import android.app.Activity;
import com.cn.ql.frame.net.HttpResultListener;
import com.cn.ql.frame.tool.ToastTool;
import com.cn.ql.frame.tool.log.FlyLog;
import com.cn.ql.frame.utils.Utils;
import com.google.gson.JsonElement;

/**
 * @author axw_an
 * @create on 2019/1/15
 * @refer url：
 * @description:
 * @update: axw_an:2019/1/15:
 */
public class HttpResultRequestListner implements HttpResultListener {

    private HttpResultListener listener;
    private Activity activity;
    public HttpResultRequestListner(Activity activity, HttpResultListener listener){
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public void onStart(int urlId) {
        if (listener != null){
            listener.onStart(urlId);
        }
    }

    @Override
    public void onEnd(int urlId) {
        if (listener != null){
            listener.onEnd(urlId);
        }
    }

    @Override
    public void onNetWorkError(int urlId) {
        if (listener != null){
            listener.onNetWorkError(urlId);
        }
    }

    @Override
    public void onSuccess(int urlId, JsonElement value) {
        FlyLog.i(urlId+":"+value.toString());

        if (listener != null){
            listener.onSuccess(urlId, value);
        }
    }

    @Override
    public void onError(int urlId, Throwable e) {
        FlyLog.i(urlId+":"+e.toString());

        ToastTool.showToast(Utils.getContext(), e.toString());
        if (listener != null){
            listener.onError(urlId, e);
        }
    }
}
