package com.cn.ql.frame.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

/**
 * @author axw_an on 2017/9/11.
 */
class SizeTextView : TextView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
