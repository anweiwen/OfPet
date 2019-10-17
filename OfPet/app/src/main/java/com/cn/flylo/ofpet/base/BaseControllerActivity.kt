package com.cn.flylo.ofpet.base

import android.os.Build
import com.cn.ql.base.leaks.IMMLeaks
import com.cn.ql.frame.base.BaseActivity
import com.cn.ql.frame.tool.BarTool

/**
 * @create on 2019/4/11
 * @author axw_an
 * @refer url：
 * @description: Activity基类
 * @update: axw_an:2019/4/11:
 */
abstract class BaseControllerActivity : BaseActivity() {

    override fun InitSetting() {
        super.InitSetting()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            IMMLeaks.fixFocusedViewLeak(this.application)
        }

        BarTool.setStatusBarColor(this,0x00000000)
        BarTool.setStatusBarMode(this, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        IMMLeaks.fixInputMethodManagerLeak(this)
    }

}