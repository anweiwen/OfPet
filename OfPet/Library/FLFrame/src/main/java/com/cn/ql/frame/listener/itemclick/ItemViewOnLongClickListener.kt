package com.cn.ql.frame.listener.itemclick

import android.view.View

/**
 * @create on 2019/3/21
 * @author axw_an
 * @refer url：
 * @description:
 * @update: axw_an:2019/3/21:
 */
interface ItemViewOnLongClickListener<T> {
    fun onClick(v: View, data: T, position: Int)
}