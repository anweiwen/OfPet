package com.cn.ql.base.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

public class FlyloCheckBox extends AppCompatCheckBox {

    public FlyloCheckBox(Context context) {
        super(context);
        init();
    }

    public FlyloCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlyloCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setAllCaps(false);
    }
}
