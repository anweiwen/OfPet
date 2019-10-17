package com.cn.ql.frame.base

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener
import com.cn.ql.frame.listener.itemclick.ItemViewOnLongClickListener
import com.cn.ql.frame.utils.StringUtils
import java.util.*

/**
 * @create on 2019/3/20
 * @author axw_an
 * @refer url：
 * @description:
 * @update: axw_an:2019/3/20:
 */
abstract class BaseRecyclerAdapter<T: Any, VH: RecyclerView.ViewHolder>(list: List<T>) : RecyclerView.Adapter<VH>() {

    open lateinit var context: Context

    var datas: List<T> = ArrayList()

    init {
        this.datas = list
    }

    abstract fun layoutId(viewType: Int):Int

    fun getView(viewGroup: ViewGroup, viewType: Int): View{
        context = viewGroup.context
        var view = LayoutInflater.from(context).inflate(layoutId(viewType), viewGroup, false)
        return view
    }

    override fun getItemCount(): Int = datas.size

    lateinit var itemViewOnClickListener: ItemViewOnClickListener<T>
    lateinit var itemViewOnLongClickListener: ItemViewOnLongClickListener<T>



    open fun getStr(str: String?): String{
        if (StringUtils.isEmpty(str)){
            return ""
        }
        return str!!
    }

    open fun getStr(string: String?, text: String?): String{
        if (TextUtils.isEmpty(string)){
            return text!!
        }else{
            return string!!
        }
    }
}