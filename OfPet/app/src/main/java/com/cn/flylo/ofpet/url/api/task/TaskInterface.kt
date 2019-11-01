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
interface TaskInterface {

    @GET(TaskApi.getTaskList)
    fun getTaskList(
        @Query("type") type: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Observable<JsonElement>

    @FormUrlEncoded
    @POST(TaskApi.getTaskInfo)
    fun getTaskInfo(
            @Field("userId") userId: String?,
            @Field("taskId") taskId: Int
    ): Observable<JsonElement>

    @FormUrlEncoded
    @POST(TaskApi.saveTaskUserInfo)
    fun saveTaskUserInfo(
            @FieldMap map: Map<String, String?>?
    ): Observable<JsonElement>


}