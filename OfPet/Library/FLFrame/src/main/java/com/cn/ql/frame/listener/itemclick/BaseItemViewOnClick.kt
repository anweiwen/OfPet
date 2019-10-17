package com.cn.ql.frame.listener.itemclick

import android.view.View

/**
 * @create on 2019/3/21
 * @author axw_an
 * @refer url：
 * @description:
 * @update: axw_an:2019/3/21:
 */
class BaseItemViewOnClick<T> : View.OnClickListener {

    private var t: T
    private var childerPosition: Int = 0
    private var position: Int = 0
    private var type: Int = 0
    private var listener: ItemViewOnClickListener<T>? = null

    constructor(listener: ItemViewOnClickListener<T>, t: T, position: Int) {
        this.t = t
        this.position = position
        this.listener = listener
    }

    constructor(listener: ItemViewOnClickListener<T>, t: T, position: Int, type: Int) {
        this.t = t
        this.position = position
        this.listener = listener
        this.type = type
    }

    override fun onClick(view: View) {
        if (listener != null) {
            listener!!.onClick(view, t, position)
        }
    }
}