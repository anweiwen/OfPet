package com.cn.flylo.ofpet.url.tool

import com.cn.flylo.ofpet.url.Result
import com.cn.flylo.ofpet.url.api.UrlId
import com.cn.flylo.ofpet.url.inter.*
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
class PrizeTool {

    var inter: PrizeInterface? = null
    var listener: HttpRequestListener? = null

    private fun MineTool(){}

    constructor(inter: PrizeInterface, listener: HttpRequestListener){
        this.inter = inter
        this.listener = listener
    }

    fun getPrizeClassifyList(page: Int) : PrizeTool {
        getPrizeClassifyList(page, Result.pageSize)
        return this
    }

    fun getPrizeClassifyList(page: Int, size: Int) : PrizeTool {
        var observable = inter?.getPrizeClassifyList(page, size)
        listener?.doPost(UrlId.getPrizeClassifyList, observable!!)
        return this
    }

    fun getPrizeList(classifyId: Int, page: Int) : PrizeTool {
        var observable = inter?.getPrizeList(classifyId, page, Result.pageSize)
        listener?.doPost(UrlId.getPrizeList, observable!!)
        return this
    }

}