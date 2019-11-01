package com.cn.flylo.ofpet.url.api

/**
 * @author axw_an
 * @create on 2019/5/26
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/18:
 */
interface TaskApi {
    companion object {
        /**
         * 获取一条协议信息
         */
        const val getTaskList = "pets/task/getTaskList"

        /**
         * 获取任务详情信息
         */
        const val getTaskInfo = "pets/task/getTaskInfo"

        /**
         * 保存报名表信息
         */
        const val saveTaskUserInfo = "pets/task/saveTaskUserInfo"


    }
}