package com.cn.flylo.ofpet.base

import android.os.Build
import com.cn.flylo.ofpet.bean.base.BaseBean
import com.cn.flylo.ofpet.tool.SaveTool
import com.cn.flylo.ofpet.url.Result
import com.cn.flylo.ofpet.url.http.HttpTool
import com.cn.ql.base.leaks.IMMLeaks
import com.cn.ql.frame.base.BaseActivity
import com.cn.ql.frame.net.HttpResultListener
import com.cn.ql.frame.tool.BarTool
import com.cn.ql.frame.tool.gson.GsonTool
import com.google.gson.JsonElement

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