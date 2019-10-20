package com.cn.flylo.ofpet.mgr

import com.cn.flylo.liteav.InitTencent
import com.cn.flylo.ofpet.R
import com.cn.ql.frame.mgr.BaseApplication
import com.cn.ql.frame.tool.log.FlyLog
import com.cn.ql.frame.tool.refresh.RefreshLoadTool
import com.squareup.leakcanary.LeakCanary

/**
 * @create on 2019/4/11
 * @author axw_an
 * @refer url：
 * @description:
 * @update: axw_an:2019/4/11:
 */
class MyApplication : BaseApplication() {
    override fun Init() {
        super.Init()
        initLog(true)
        FlyLog.e("Fly")

        initLeakCanary()

        RefreshLoadTool.Init(android.R.color.transparent, R.color.color666666)
        //CrashReport.initCrashReport(getApplicationContext(), "17969df881", false)

        // todo
        //initTencent()
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    internal var licenceUrl =
        "http://license.vod2.myqcloud.com/license/v1/57642d256f1850dd6217eee6e7c6b72f/TXUgcSDK.licence"
    internal var licenseKey = "28617bed4c39a1258678bf3777f5c864"
    private fun initTencent(){
        InitTencent.Init(this, licenceUrl, licenseKey)
    }

}