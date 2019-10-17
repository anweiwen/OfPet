package com.cn.flylo.ofpet.base

import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.cn.flylo.ofpet.R
import com.cn.flylo.ofpet.bean.base.BaseBean
import com.cn.flylo.ofpet.tool.SaveTool
import com.cn.flylo.ofpet.url.Result
import com.cn.flylo.ofpet.url.http.HttpTool
import com.cn.ql.frame.base.BaseFragment
import com.cn.ql.frame.net.HttpResultListener
import com.cn.ql.frame.tool.BarTool
import com.cn.ql.frame.tool.gson.GsonTool
import com.cn.ql.frame.tool.refresh.RefreshLoadTool
import com.cn.ql.frame.utils.StringUtils
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.JsonElement
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.include_top.*

/**
 * @create on 2019/2/5
 * @author axw_an
 * @refer url：
 * @description: Fragment基类
 * @update: axw_an:2019/2/5:
 */
abstract class BaseControllerFragment : BaseFragment() {

    override fun Init() {
        InitHttp()
        InitLoad()
    }

    open fun finishRefresh(){
        var refresh_view = view.findViewById<View>(R.id.refresh_layout)
        if (refresh_view != null){
            var refresh_layout = refresh_view as SmartRefreshLayout
            if (refresh_layout != null){
                RefreshLoadTool.finishRefreshAndLoad(refresh_layout)
            }
        }
    }

    open fun setTopLayout(layout_tab_top: LinearLayout){
        val lp = layout_tab_top.getLayoutParams() as LinearLayout.LayoutParams
        lp.topMargin = BarTool.getStatusBarHeight(act)
    }


    open fun setTopLayoutFrameLayout(layout_tab_top: LinearLayout){
        val lp = layout_tab_top.getLayoutParams() as FrameLayout.LayoutParams
        lp.topMargin = BarTool.getStatusBarHeight(act)
    }

    open fun setTopFrameLayout(layout_tab_top: LinearLayout){
        val lp = layout_tab_top.getLayoutParams() as AppBarLayout.LayoutParams
        lp.topMargin = BarTool.getStatusBarHeight(act)
    }

    open fun setTopLayout(layout_tab_top: FrameLayout){
        val lp = layout_tab_top.getLayoutParams() as LinearLayout.LayoutParams
        lp.topMargin = BarTool.getStatusBarHeight(act)
    }

    // todo 网络请求
    var httpTool : HttpTool? = null
    open fun InitHttp(){
        httpTool = HttpTool(this, getHttpListener())
    }

    open fun getHttpListener(): HttpResultListener{
        return httpResultListener
    }

    open fun InitLoad(){}

    val httpResultListener = object : HttpResultListener {

        override fun onStart(urlId: Int) {
            onNetStart(urlId)
        }

        override fun onEnd(urlId: Int) {
            finishRefresh()
            onNetEnd(urlId)
        }

        override fun onNetWorkError(urlId: Int) {

        }

        override fun onError(urlId: Int, e: Throwable) {

        }

        override fun onSuccess(urlId: Int, value: JsonElement) {
            var baseBean = GsonTool.getBean(value.toString(), BaseBean::class.java)
            var code = 0
            if (baseBean != null){
                code = baseBean.code
            }
            // todo 其他地方登陆了
            if (code == 2){
                showNoLogin()
                return
            }
            onNetSuccess(urlId, value, value.toString(), baseBean, code == Result.success)
        }
    }

    open fun showNoLogin(){

    }


    open fun logoutToLogin() {
        SaveTool(act).putUser("")
        finish()
    }

    open fun onNetStart(urlId: Int){
        showLoading()
    }

    open fun onNetEnd(urlId: Int){
        hideLoading()
    }

    open fun onNetSuccess(urlId: Int, jsonElement: JsonElement, value: String, baseBean: BaseBean, success: Boolean){}

    // todo

    open fun getText(view: View): String{
        var result = ""
        if (view is TextView){
            var text_view = view as TextView
            result = text_view.text.toString()
        }
        if (view is EditText){
            var edit_view = view as EditText
            result = edit_view.text.toString()
        }
        if (view is Button){
            var button = view as Button
            result = button.text.toString()
        }
        return result.trim()
    }

    /**
     * 2018/6/7 gson的解析
     * @param json
     * @param cls
     * @param <T>
     * @return
    </T> */
    protected fun <T> getBean(json: String, cls: Class<T>): T? {
        return GsonTool.getGson(json, cls, Any::class.java, 0)
    }

    protected fun <T> getBean(json: String, parentClass: Class<T>, childClass: Class<*>): T? {
        try {
            return GsonTool.getGson(json, parentClass, childClass, 0)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    protected fun <T> getListBean(json: String, parentClass: Class<T>, childClass: Class<*>): T? {
        return GsonTool.getGson(json, parentClass, childClass, 1)
    }

    protected fun <T> getList(json: String, parentClass: Class<T>): List<T> {
        return GsonTool.getList(json, parentClass)
    }

    protected fun toJson(cls: Any): String {
        return GsonTool.toJSONString(cls)
    }


    protected fun setShowTop(view: View, color: Int){
        var lp = view.layoutParams
        lp.height = BarTool.getStatusBarHeight(activity)
        view.layoutParams = lp
        view.setBackgroundResource(color)
    }

    protected fun setTitle(title: String, right: String, exit : Boolean){
        text_bar_title.text = getStr(title, "")
        text_title_right.text = getStr(right, "")
        frame_title_right.visibility = View.GONE
        if (!TextUtils.isEmpty(right)){
            frame_title_right.visibility = View.VISIBLE
            frame_title_right.setOnClickListener {
                onRightClick()
            }
        }else{
        }
        if (exit){
            image_title_back.visibility = View.VISIBLE
            image_title_back.setOnClickListener {
                hideKeyboard()
                finish()
            }
        }else{
            image_title_back.visibility = View.GONE
        }
    }

    protected fun setTitle(title: String, right: Int, exit : Boolean){
        text_bar_title.text = getStr(title, "")
        image_title_right.setImageResource(right)
        frame_title_right.visibility = View.GONE
        if (right != 0){
            frame_title_right.visibility = View.VISIBLE
            frame_title_right.setOnClickListener {
                onRightClick()
            }
        }
        if (exit){
            image_title_back.visibility = View.VISIBLE
        }else{
            image_title_back.visibility = View.GONE
        }

        image_title_back.setOnClickListener {
            hideKeyboard()
            finish()
        }
    }

    open fun onRightClick(){}

    protected fun finish(){
        activity!!.finish()
    }

    open fun getStr(str: String?): String{
        if (StringUtils.isEmpty(str)){
            return ""
        }
        return str!!
    }

    open fun getStr(string: String?, text: String): String{
        if (TextUtils.isEmpty(string)){
            return text
        }else{
            return string!!
        }
    }

    var loading: ConstraintLayout? = null
    protected fun showLoading(){
        loading = activity!!.findViewById(R.id.include_loading)
        loading?.visibility = View.VISIBLE
    }

    protected fun hideLoading(){
        loading?.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        hideLoading()
    }

    open fun onBackPressed(): Boolean{
        if (loading != null){
            if (loading!!.visibility == View.VISIBLE){
                hideLoading()
                return true
            }
        }
        return false
    }


}