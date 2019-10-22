package com.cn.flylo.ofpet.url.tool

import com.cn.flylo.ofpet.url.api.UrlId
import com.cn.flylo.ofpet.url.inter.AccountInterface
import com.cn.flylo.ofpet.url.listener.HttpRequestListener
import com.cn.ql.frame.tool.SystemTool
import com.cn.ql.frame.utils.Utils

/**
 * @author axw_an
 * @create on 2019/5/26
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/18:
 */
class AccountTool {

    var inter: AccountInterface? = null
    var listener: HttpRequestListener? = null

    private fun AccountTool(){}

    constructor(accountInter: AccountInterface, listener: HttpRequestListener){
        this.inter = accountInter
        this.listener = listener
    }

    fun pLogin(userName: String, password: String) : AccountTool {
        var device = "android"
        var deviceTag = SystemTool.getIMEI(Utils.getContext())
        var observable = inter?.pLogin(userName, password, device, deviceTag)
        listener?.doPost(UrlId.pLogin, observable!!)
        return this
    }

    /**
     * @param vcodeType （1注册 4找回密码 8绑定手机）
     */
    fun sendVcode(iphone: String, vcodeType: Int) : AccountTool {
        var observable = inter?.sendVcode(iphone, vcodeType)
        listener?.doPost(UrlId.sendVcode, observable!!)
        return this
    }

    fun pResgist(userName: String, vercoed: String, password: String) : AccountTool {
        var device = "android"
        var deviceTag = SystemTool.getIMEI(Utils.getContext())
        var observable = inter?.pResgist(userName, vercoed, password, device, deviceTag)
        listener?.doPost(UrlId.pResgist, observable!!)
        return this
    }

    fun resetPwd(userName: String, vercoed: String, password: String) : AccountTool {
        var observable = inter?.resetPwd(userName, vercoed, password)
        listener?.doPost(UrlId.resetPwd, observable!!)
        return this
    }

}