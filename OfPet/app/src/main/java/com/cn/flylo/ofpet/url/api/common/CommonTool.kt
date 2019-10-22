package com.cn.flylo.ofpet.url.tool

import com.cn.flylo.ofpet.url.api.UrlId
import com.cn.flylo.ofpet.url.inter.AccountInterface
import com.cn.flylo.ofpet.url.inter.CommonInterface
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
class CommonTool {

    var inter: CommonInterface? = null
    var listener: HttpRequestListener? = null

    private fun AccountTool(){}

    constructor(inter: CommonInterface, listener: HttpRequestListener){
        this.inter = inter
        this.listener = listener
    }

    fun getAgree(agreeId: Int) : CommonTool {
        var observable = inter?.getAgree(agreeId)
        listener?.doPost(UrlId.pLogin, observable!!)
        return this
    }



}