package com.cn.ql.frame.tool

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.IBinder
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.telephony.TelephonyManager
import com.cn.ql.frame.utils.StringUtils
import java.util.*


object SystemTool {

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    fun hideKeyboard(activity: Activity) {
        val v = activity.currentFocus ?: return
        val token = v.windowToken
        if (token != null) {
            val im = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    fun getSystemLanguage(): String {
        return Locale.getDefault().getLanguage()
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    fun getSystemLanguageList(): Array<Locale> {
        return Locale.getAvailableLocales()
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    fun getSystemVersion(): String {
        return android.os.Build.VERSION.RELEASE
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    fun getSystemModel(): String {
        return android.os.Build.MODEL
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    fun getDeviceBrand(): String {
        return android.os.Build.BRAND
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI
     */
    @SuppressLint("MissingPermission")
    fun getIMEI(ctx: Context): String? {
        val tm = ctx.getSystemService(Activity.TELEPHONY_SERVICE) as TelephonyManager
        return tm.deviceId
    }

    /**
     * 获取本地软件版本号
     *
     * @param ctx
     * @return
     */
    fun getLocalVersion(ctx: Context): Int {
        var localVersion = 0
        try {
            val packageInfo = ctx.applicationContext
                    .packageManager
                    .getPackageInfo(ctx.packageName, 0)
            localVersion = packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return localVersion
    }



    /**
     * 是否安装对应包名的应用
     *
     * @param context
     * @param packagePath
     * @return
     */
    fun isAvailable(context: Context, packagePath: String): Boolean {
        val packageManager = context.packageManager
        val pinfo = packageManager.getInstalledPackages(0)
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName
                if (pn == packagePath) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 跳转appStore
     *
     * @param context
     */
    fun toAppStore(context: Context) {
        try {
            val uri = Uri.parse("market://details?id=" + context.packageName)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
        }

    }

    /**
     * 复制文字
     *
     * @param context
     * @param text
     * @return
     */
    fun copy(context: Context, text: String): Boolean {
        if (StringUtils.isEmpty(text)) {
            return false
        }
        var cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建普通字符型ClipData
        var mClipData = ClipData.newPlainText("Label", text)
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData)
        return true
    }
}
