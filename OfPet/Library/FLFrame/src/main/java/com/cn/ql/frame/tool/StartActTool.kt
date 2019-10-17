package com.cn.ql.frame.tool

import android.app.Activity
import android.content.Intent
import android.os.Bundle

/**
 * @author axw_an
 * @create on 2018/12/11
 * @refer url：
 * @description:
 * @update: axw_an:2018/12/11:
 */
object StartActTool {

    /**
     * 界面跳转
     * @param act 本界面
     * @param cls 跳转的Activity
     * @param data 参数
     * @param code 带参数的跳转 -1 为无参数
     */
    fun Start(act: Activity, cls: Class<*>, data: Bundle?, code: Int) {
        val intent = Intent()
        intent.setClass(act, cls)
        if (data != null) {
            intent.putExtras(data)
        }
        if (code != -1) {
            act.startActivityForResult(intent, code)
        } else {
            act.startActivity(intent)
        }
    }

    /**
     * 界面跳转
     * @param act 本界面
     * @param cls 跳转的Activity
     */
    fun Start(act: Activity, cls: Class<*>, top: Boolean) {
        val intent = Intent()
        intent.setClass(act, cls)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        act.startActivity(intent)
    }

}
