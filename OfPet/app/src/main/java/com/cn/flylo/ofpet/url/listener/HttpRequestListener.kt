package com.cn.flylo.ofpet.url.listener

import com.google.gson.JsonElement
import io.reactivex.Observable

/**
 * @author axw_an
 * @create on 2019/5/27
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/18:
 */
interface HttpRequestListener {
    fun doPost(urlId: Int, observable: Observable<JsonElement>)
}