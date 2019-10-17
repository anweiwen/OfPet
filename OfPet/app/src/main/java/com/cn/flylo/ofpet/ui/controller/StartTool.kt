package com.cn.flylo.ofpet.ui.controller

import android.app.Activity
import android.os.Bundle
import com.cn.flylo.ofpet.R
import com.cn.ql.frame.tool.StartActTool

/**
 * @create on 2019/2/5
 * @author axw_an
 * @refer url：
 * @description:
 * @update: axw_an:2019/2/5:
 */
object StartTool {
    /**
     * 带参数跳转页面
     * @param activity
     * @param pageEnum
     */
    fun start(activity: Activity?, pageEnum: PageEnum) {
        start(activity, pageEnum, null, -1)
    }

    /**
     * 带参数跳转页面
     * @param activity
     * @param pageEnum
     */
    fun start(activity: Activity?, pageEnum: PageEnum, data: Bundle) {
        start(activity, pageEnum, data, -1)
    }

    /**
     * 带参数跳转页面
     * @param activity
     * @param pageEnum
     */
    fun start(activity: Activity?, pageEnum: PageEnum, code: Int) {
        start(activity, pageEnum, null, code)
    }

    /**
     * 跳转页面
     * @param activity
     * @param pageEnum
     */
    fun start(activity: Activity?, pageEnum: PageEnum, data: Bundle?, code: Int) {
        var data = data
        if (data == null) {
            data = Bundle()
        }
        data.putSerializable("pageType", pageEnum)
        StartActTool.Start(activity!!, ControllerActivity::class.java, data, code)

        // 跳转动画
        LeftToRight(activity)
    }

    // 右进左出
    fun LeftToRight(act: Activity) {
        Anima(act, R.anim.in_from_right, R.anim.out_from_left)
    }

    // 左进右出
    fun RightToLeft(act: Activity) {
        Anima(act, R.anim.in_from_left, R.anim.out_from_right)
    }

    fun TopToBottom(act: Activity){
        Anima(act, R.anim.in_bottom, R.anim.out_top)
    }

    fun BottomToTop(act: Activity){
        Anima(act, R.anim.in_top, R.anim.out_bottom)
    }

    fun Anima(act: Activity, into: Int, out: Int) {
        act.overridePendingTransition(into, out)
    }
}