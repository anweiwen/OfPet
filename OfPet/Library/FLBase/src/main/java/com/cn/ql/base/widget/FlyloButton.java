package com.cn.ql.base.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class FlyloButton extends AppCompatButton {

    public FlyloButton(Context context) {
        super(context);
        init();
    }

    public FlyloButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlyloButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setAllCaps(false);
    }
}
