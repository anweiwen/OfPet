package com.cn.ql.frame.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by AXW on 2017/7/16.
 * 注：没有滚动的GridView
 */

public class NoScrollRecyclerView extends RecyclerView {

    public NoScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    public NoScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public NoScrollRecyclerView(Context context) {
        super(context);
        init();
    }

    private void init(){
        setNestedScrollingEnabled(false);
        setFocusable(false);
    }

    /**
     * 设置不滚动
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}