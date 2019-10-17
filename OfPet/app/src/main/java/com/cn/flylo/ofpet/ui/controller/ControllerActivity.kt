package com.cn.flylo.ofpet.ui.controller

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cn.flylo.ofpet.R
import com.cn.flylo.ofpet.base.BaseControllerActivity
import com.cn.flylo.ofpet.base.BaseControllerFragment
import java.lang.ref.WeakReference

/**
 * @create on 2019/2/5
 * @author axw_an
 * @refer url：
 * @description:
 * @update: axw_an:2019/2/5:
 */
class ControllerActivity : BaseControllerActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_controller
    }

    protected var mFgm: WeakReference<Fragment>? = null
    private var pageEnum: PageEnum? = null

    override fun InitData(bundle: Bundle) {
        pageEnum = bundle!!["pageType"] as PageEnum
    }

    override fun InitView() {
        if (pageEnum == null) {
            finish()
            return
        }
        val fgmCls = pageEnum!!.value
        try {
            val fgm = fgmCls.newInstance() as Fragment
            if (fgm == null) {
                finish()
                return
            }

            val intent = intent
            if (intent != null) {
                val data = intent.extras
                if (data != null) {
                    fgm.arguments = data
                }
            }
            val trans = supportFragmentManager
                    .beginTransaction()
            trans.replace(R.id.frame_layout, fgm)
            trans.commitAllowingStateLoss()
            mFgm = WeakReference(fgm)
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        var fgm = mFgm?.get()
        if (fgm is BaseControllerFragment) {
            var controllerFragment = fgm as BaseControllerFragment
            if (controllerFragment != null) {
                var back = controllerFragment.onBackPressed() // todo 返回true就不退出
                if (back) {
                    return
                }
            }
            if (pageEnum == PageEnum.Main) {
                toHome()
                return
            } else {
                super.onBackPressed()
            }
        }
    }

    fun toHome() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun finish() {
        super.finish()
        // 退出动画
        StartTool.RightToLeft(this)
    }

    override fun EventMessage(type: Int, data: Bundle?) {
        super.EventMessage(type, data)
    }

}