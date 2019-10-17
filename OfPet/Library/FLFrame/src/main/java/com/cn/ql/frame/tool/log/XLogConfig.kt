package com.cn.ql.frame.tool.log


import android.text.TextUtils

class XLogConfig {

    private var showThreadInfo = true
    private var debug = false
    private var tag = ""


    fun setTag(tag: String): XLogConfig {
        if (!TextUtils.isEmpty(tag)) {
            this.tag = tag
        }
        return this
    }

    fun setShowThreadInfo(showThreadInfo: Boolean): XLogConfig {
        this.showThreadInfo = showThreadInfo
        return this
    }


    fun setDebug(debug: Boolean): XLogConfig {
        this.debug = debug
        return this
    }

    fun getTag(): String {
        return tag
    }

    fun isDebug(): Boolean {
        return debug
    }

    fun isShowThreadInfo(): Boolean {
        return showThreadInfo
    }
}
