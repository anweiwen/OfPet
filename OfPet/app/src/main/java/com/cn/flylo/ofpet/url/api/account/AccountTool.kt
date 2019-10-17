package com.cn.flylo.ofpet.url.tool

import com.cn.flylo.ofpet.url.api.UrlId
import com.cn.flylo.ofpet.url.inter.AccountInterface
import com.cn.flylo.ofpet.url.listener.HttpRequestListener
import com.cn.ql.frame.tool.SystemTool

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

    fun login(mobile: String, password: String, code: String, type: Int) : AccountTool {
        var mobile_type = SystemTool.getSystemModel();
        var observable = inter?.login(mobile, password, code, type, mobile_type)
        listener?.doPost(UrlId.login, observable!!)
        return this
    }

    /**
     * @param code 1表示注册 2找回密码 3验证码登录 4绑定手机号码
     */
    fun getCode(mobile: String, type: Int) : AccountTool {
        var observable = inter?.getCode(mobile, type)
        listener?.doPost(UrlId.getCode, observable!!)
        return this
    }

    fun register(mobile: String, code: String, password: String, nickname: String) : AccountTool {
        var observable = inter?.register(1, mobile, code, password, password, nickname)
        listener?.doPost(UrlId.register, observable!!)
        return this
    }

}