package com.cn.flylo.ofpet.url.inter

import com.cn.flylo.ofpet.url.api.VideoApi
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
interface VideoInterface {

    @GET(VideoApi.getPopularVideo)
    fun getPopularVideo(
        @Query("userId") userId: String?,
        @Query("type") type: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Observable<JsonElement>

    @GET(VideoApi.getVideo)
    fun getVideo(
        @Query("userId") userId: String?,
        @Query("videoId") videoId: Int
    ): Observable<JsonElement>

    @GET(VideoApi.getVideoDisList)
    fun getVideoDisList(
        @Query("userId") userId: String?,
        @Query("videoId") videoId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Observable<JsonElement>

    @FormUrlEncoded
    @POST(VideoApi.saveInfoDispra)
    fun saveInfoDispra(
        @Field("userId") userId: String?,
        @Field("discussId") discussId: Int,
        @Field("status") status: Int
    ): Observable<JsonElement>

    @FormUrlEncoded
    @POST(VideoApi.saveVideoDiscuss)
    fun saveVideoDiscuss(
        @Field("userId") userId: String?,
        @Field("videoId") videoId: Int,
        @Field("context") context: String
    ): Observable<JsonElement>

    @FormUrlEncoded
    @POST(VideoApi.saveChildDis)
    fun saveChildDis(
        @Field("userId") userId: String?,
        @Field("videoId") videoId: Int,
        @Field("discussId") discussId: Int,
        @Field("context") context: String
    ): Observable<JsonElement>

    @FormUrlEncoded
    @POST(VideoApi.videoPraise)
    fun videoPraise(
        @Field("userId") userId: String?,
        @Field("videoId") videoId: Int,
        @Field("status") status: Int
    ): Observable<JsonElement>

    @GET(VideoApi.getNewVideos)
    fun getNewVideos(
        @Query("userId") userId: String?,
        @Query("myId") myId: String?,
        @Query("type") type: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Observable<JsonElement>

    @FormUrlEncoded
    @POST(VideoApi.saveChildDispra)
    fun saveChildDispra(
            @Field("userId") userId: String?,
            @Field("childId") childId: Int,
            @Field("status") status: Int
    ): Observable<JsonElement>

}