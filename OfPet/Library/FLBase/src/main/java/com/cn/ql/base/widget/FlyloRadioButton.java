package com.cn.ql.base.widget;

import android.content.Context;
import androidx.appcompat.widget.AppCompatCheckBox;
import android.util.AttributeSet;

public class FlyloRadioButton extends AppCompatCheckBox {

    public FlyloRadioButton(Context context) {
        super(context);
        init();
    }

    public FlyloRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlyloRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setAllCaps(false);
    }
}
