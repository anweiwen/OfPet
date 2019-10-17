package com.cn.ql.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class FlyloScrollView extends ScrollView {

    public FlyloScrollView(Context context) {
        super(context);
        init();
    }

    public FlyloScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlyloScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (onScollChangedListener != null) {
            onScollChangedListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    private OnScollChangedListener onScollChangedListener = null;

    public void setOnScollChangedListener(OnScollChangedListener onScollChangedListener) {
        this.onScollChangedListener = onScollChangedListener;
    }

    public interface OnScollChangedListener {
        void onScrollChanged(FlyloScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}
