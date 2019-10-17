package com.cn.flylo.ofpet.url.http

import androidx.fragment.app.FragmentActivity
import com.cn.flylo.ofpet.url.inter.AccountInterface
import com.cn.flylo.ofpet.url.listener.HttpRequestListener
import com.cn.flylo.ofpet.url.listener.HttpResultRequestListner
import com.cn.flylo.ofpet.url.tool.AccountTool
import com.cn.ql.frame.net.HttpResultListener
import com.cn.ql.frame.net.RetrofitFactory
import com.cn.ql.frame.net.RetrofitTool.POST
import com.google.gson.JsonElement
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.Observable
import io.reactivex.annotations.NonNull

/**
 * @author axw_an
 * @create on 2019/5/26
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/18:
 */
class HttpTool : HttpRequestListener {

    companion object {
        var BaseUrl = "http://huayanchuang.xyz/aywz/" // 新https://huayanchuang.xyz/aywz/ 旧：https://kf.gdpinganyaoye.com/aywz/
    }

    private var activity: FragmentActivity? = null
    private var rxLifecycle: LifecycleProvider<*>? = null
    private var listener: HttpResultListener? = null

    private var bean: ApiBean? = ApiBean()

    private fun HttpTool(){}

    /**
     *
     * @param rxLifecycle 对应的组件 RxFragment
     * @param listener 请求回调接口
     */
    constructor(@NonNull rxLifecycle: RxFragment, @NonNull listener: HttpResultListener) {
        this.rxLifecycle = rxLifecycle
        this.listener = listener
        activity = rxLifecycle.activity
        addHeader()
    }

    /**
     *
     * @param rxLifecycle 对应的组件 RxAppCompatActivity
     * @param listener 请求回调接口
     */
    constructor(@NonNull rxLifecycle: RxAppCompatActivity, @NonNull listener: HttpResultListener){
        this.rxLifecycle = rxLifecycle
        this.listener = listener
        activity = rxLifecycle
        addHeader()
    }

    fun addHeader(){
        RetrofitFactory.header.put("token", "")
    }

    /**
     * 开始请求
     * @param urlId
     * @param observable
     */
    override
    fun doPost(urlId: Int, observable: Observable<JsonElement>) {
        POST(urlId, observable, rxLifecycle, HttpResultRequestListner(activity, listener))
    }

    fun getAccount(): AccountTool {
        var inter = RetrofitFactory.getInstance(BaseUrl, AccountInterface::class.java)
        var tool = AccountTool(inter!!, this)
        return tool
    }
}

class ApiBean{
    var observable: Observable<JsonElement>? = null
    var urlId: Int = 0

    fun setValue(observable: Observable<JsonElement>,
                urlId: Int){
        this.observable = observable
        this.urlId = urlId
    }
}