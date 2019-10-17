package com.cn.ql.frame.tool

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Created by AXW on 2018/3/28.
 * 注：屏膜尺寸
 */

object DisplayTool {

    /**
     * 获取屏膜的宽度
     * @param context
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        return getDM(context).widthPixels
    }

    /**
     * 获取屏膜的高度
     * @param context
     * @return
     */
    fun getScreenHeight(context: Context): Int {
        return getDM(context).heightPixels
    }

    fun getDM(context: Context): DisplayMetrics {
        val dm = DisplayMetrics()
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)
        return dm
    }
}
