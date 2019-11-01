package com.cn.flylo.ofpet.url.http

import androidx.fragment.app.FragmentActivity
import com.cn.flylo.ofpet.bean.Account
import com.cn.flylo.ofpet.url.inter.*
import com.cn.flylo.ofpet.url.listener.HttpRequestListener
import com.cn.flylo.ofpet.url.listener.HttpResultRequestListner
import com.cn.flylo.ofpet.url.tool.*
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
        var BaseUrl =
            "http://129.204.108.149:8080/"
//        "http://192.168.31.185:8080/"

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
        RetrofitFactory.header.put("token", "${getToken()}")
        //RetrofitFactory.header.put("userId", "${getUserId()}")
    }

    fun getToken(): String?{
        var account = Account.instance
        if(account == null){
            return ""
        }
        var token = account.token
        return token
    }

    fun getUserId(): Int?{
        var account = Account.instance
        var userId = account.userId
        return userId
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

    fun getCommon(): CommonTool {
        var inter = RetrofitFactory.getInstance(BaseUrl, CommonInterface::class.java)
        var tool = CommonTool(inter!!, this)
        return tool
    }

    fun getVideo(): VideoTool {
        var inter = RetrofitFactory.getInstance(BaseUrl, VideoInterface::class.java)
        var tool = VideoTool(inter!!, this)
        return tool
    }

    fun getMine(): MineTool {
        var inter = RetrofitFactory.getInstance(BaseUrl, MineInterface::class.java)
        var tool = MineTool(inter!!, this)
        return tool
    }

    fun getPrize(): PrizeTool {
        var inter = RetrofitFactory.getInstance(BaseUrl, PrizeInterface::class.java)
        var tool = PrizeTool(inter!!, this)
        return tool
    }

    fun getTask(): TaskTool {
        var inter = RetrofitFactory.getInstance(BaseUrl, TaskInterface::class.java)
        var tool = TaskTool(inter!!, this)
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