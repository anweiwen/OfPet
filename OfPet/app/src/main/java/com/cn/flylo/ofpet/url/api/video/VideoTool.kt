package com.cn.flylo.ofpet.url.tool

import com.cn.flylo.ofpet.bean.Account
import com.cn.flylo.ofpet.url.Result
import com.cn.flylo.ofpet.url.api.UrlId
import com.cn.flylo.ofpet.url.inter.AccountInterface
import com.cn.flylo.ofpet.url.inter.CommonInterface
import com.cn.flylo.ofpet.url.inter.VideoInterface
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
class VideoTool {

    var inter: VideoInterface? = null
    var listener: HttpRequestListener? = null

    private fun VideoTool(){}

    constructor(inter: VideoInterface, listener: HttpRequestListener){
        this.inter = inter
        this.listener = listener
    }

    fun getToken(): String?{
        var account = Account.instance
        if(account == null){
            return ""
        }
        var token = account.token
        return token
    }

    fun getUserId(): String?{
        var account = Account.instance
        if(account == null){
            return ""
        }
        var userId = account.userId
        return "${userId}"
    }

    fun getPopularVideo(type: Int, page: Int) : VideoTool {
        var observable = inter?.getPopularVideo(getUserId(), type, page, Result.pageSize)
        listener?.doPost(UrlId.getPopularVideo, observable!!)
        return this
    }

    fun getVideo(videoId: Int) : VideoTool {
        var observable = inter?.getVideo(getUserId(), videoId)
        listener?.doPost(UrlId.getVideo, observable!!)
        return this
    }

    fun getVideoDisList(videoId: Int, page: Int) : VideoTool {
        var observable = inter?.getVideoDisList(getUserId(), videoId, page, Result.pageSize)
        listener?.doPost(UrlId.getVideoDisList, observable!!)
        return this
    }

    fun saveInfoDispra(discussId: Int, status: Int) : VideoTool {
        var observable = inter?.saveInfoDispra(getUserId(), discussId, status)
        listener?.doPost(UrlId.saveInfoDispra, observable!!)
        return this
    }

    fun saveVideoDiscuss(videoId: Int, context: String) : VideoTool {
        var observable = inter?.saveVideoDiscuss(getUserId(), videoId, context)
        listener?.doPost(UrlId.saveVideoDiscuss, observable!!)
        return this
    }

    fun saveChildDis(videoId: Int, discussId: Int, context: String) : VideoTool {
        var observable = inter?.saveChildDis(getUserId(), videoId, discussId, context)
        listener?.doPost(UrlId.saveChildDis, observable!!)
        return this
    }

    fun videoPraise(videoId: Int, status: Int) : VideoTool {
        var observable = inter?.videoPraise(getUserId(), videoId, status)
        listener?.doPost(UrlId.videoPraise, observable!!)
        return this
    }

    fun getNewVideos(myId: String?, type: Int, page: Int) : VideoTool {
        var observable = inter?.getNewVideos(getUserId(), myId, type, page, Result.pageSize)
        listener?.doPost(UrlId.getNewVideos, observable!!)
        return this
    }

    fun saveChildDispra(childId: Int, status: Int) : VideoTool {
        var observable = inter?.saveChildDispra(getUserId(), childId, status)
        listener?.doPost(UrlId.saveChildDispra, observable!!)
        return this
    }



}