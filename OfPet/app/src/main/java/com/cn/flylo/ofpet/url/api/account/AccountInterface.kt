package com.cn.flylo.ofpet.url.inter

import com.cn.flylo.ofpet.url.api.AccountApi
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
interface AccountInterface{

    @FormUrlEncoded
    @POST(AccountApi.login)
    fun login(
        @Field("mobile") mobile: String,
        @Field("password") password: String,
        @Field("code") code: String,
        @Field("type") type: Int,
        @Field("phone_type") phone_type: String
    ): Observable<JsonElement>

    @GET(AccountApi.getCode)
    fun getCode(
            @Query("mobile") mobile: String,
            @Query("type") type: Int
    ): Observable<JsonElement>

    @FormUrlEncoded
    @POST(AccountApi.register)
    fun register(
            @Field("type") type: Int,
            @Field("mobile") mobile: String,
            @Field("code") code: String,
            @Field("password") password: String,
            @Field("repwd") repwd: String,
            @Field("nickname") nickname: String
    ): Observable<JsonElement>

}