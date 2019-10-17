package com.cn.ql.frame.tool.event

import android.os.Bundle

import org.greenrobot.eventbus.EventBus

/**
 * Created by AXWAN on 2017/12/28.
 */

class EventBusTool private constructor() {

    fun Send(type: Int) {
        val data = Bundle()
        data.putInt("type", type)
        val event = EventBusBean(data)
        EventBus.getDefault().post(event)
    }

    fun Send(type: Int, data: Bundle) {
        data.putInt("type", type)
        val event = EventBusBean(data)
        EventBus.getDefault().post(event)
    }

    companion object {

        private var instance: EventBusTool? = null

        fun getInstance(): EventBusTool {
            if (instance == null) {
                instance = EventBusTool()
            }
            return instance!!
        }
    }
}
