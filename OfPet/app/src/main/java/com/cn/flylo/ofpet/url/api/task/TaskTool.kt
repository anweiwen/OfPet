package com.cn.flylo.ofpet.url.tool

import com.cn.flylo.ofpet.bean.Account
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
class TaskTool {

    var inter: TaskInterface? = null
    var listener: HttpRequestListener? = null

    private fun TaskTool(){}

    constructor(inter: TaskInterface, listener: HttpRequestListener){
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


    fun getTaskList(type: Int, page: Int) : TaskTool {
        var observable = inter?.getTaskList(type, page, Result.pageSize)
        listener?.doPost(UrlId.getTaskList, observable!!)
        return this
    }

    fun getTaskInfo(taskId: Int) : TaskTool {
        var observable = inter?.getTaskInfo(getUserId(), taskId)
        listener?.doPost(UrlId.getTaskInfo, observable!!)
        return this
    }

    fun saveTaskUserInfo(taskId: Int, name: String, phone: String, wechat: String?, remark: String?) : TaskTool {
        var params: MutableMap<String, String?> = HashMap<String, String?>()

        params.put("userId", getUserId())
        params.put("taskId", "${taskId}")
        params.put("name", name)
        params.put("phone", phone)
        params.put("wechat", wechat)
        params.put("remark", remark)

        var observable = inter?.saveTaskUserInfo(params)
        listener?.doPost(UrlId.saveTaskUserInfo, observable!!)
        return this
    }


}