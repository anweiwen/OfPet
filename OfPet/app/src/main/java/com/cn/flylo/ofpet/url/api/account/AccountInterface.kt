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
interface AccountInterface {

    @FormUrlEncoded
    @POST(AccountApi.pLogin)
    fun pLogin(
        @Field("userName") userName: String,
        @Field("password") password: String,
        @Field("device") device: String,
        @Field("deviceTag") deviceTag: String?
    ): Observable<JsonElement>

    @FormUrlEncoded
    @POST(AccountApi.sendVcode)
    fun sendVcode(
        @Field("iphone") iphone: String,
        @Field("vcodeType") vcodeType: Int
    ): Observable<JsonElement>

    @FormUrlEncoded
    @POST(AccountApi.pResgist)
    fun pResgist(
        @Field("userName") userName: String,
        @Field("vercoed") vercoed: String,
        @Field("password") password: String,
        @Field("device") device: String,
        @Field("deviceTag") deviceTag: String?
    ): Observable<JsonElement>

    @FormUrlEncoded
    @POST(AccountApi.resetPwd)
    fun resetPwd(
        @Field("iphone") iphone: String,
        @Field("vercoed") vercoed: String,
        @Field("password") password: String
    ): Observable<JsonElement>

}