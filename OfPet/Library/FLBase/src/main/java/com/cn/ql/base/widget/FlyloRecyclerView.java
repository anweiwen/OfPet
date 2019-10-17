package com.cn.ql.base.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

public class FlyloRecyclerView extends RecyclerView {

    public FlyloRecyclerView(Context context) {
        super(context);
        init();
    }

    public FlyloRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlyloRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
    }
}
