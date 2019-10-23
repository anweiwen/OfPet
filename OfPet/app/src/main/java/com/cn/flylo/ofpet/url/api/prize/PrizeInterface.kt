package com.cn.flylo.ofpet.url.inter

import com.cn.flylo.ofpet.url.api.*
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
interface PrizeInterface {

    @GET(PrizeApi.getPrizeClassifyList)
    fun getPrizeClassifyList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Observable<JsonElement>

    @GET(PrizeApi.getPrizeList)
    fun getPrizeList(
        @Query("classifyId") classifyId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Observable<JsonElement>


}