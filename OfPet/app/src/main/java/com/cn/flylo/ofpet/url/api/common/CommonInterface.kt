package com.cn.flylo.ofpet.url.inter

import com.cn.flylo.ofpet.url.api.AccountApi
import com.cn.flylo.ofpet.url.api.CommonApi
import com.google.gson.JsonElement
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @author axw_an
 * @create on 2019/5/26
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/18:
 */
interface CommonInterface {

    @GET(CommonApi.getAgree)
    fun getAgree(
        @Query("agreeId") agreeId: Int
    ): Observable<JsonElement>


}