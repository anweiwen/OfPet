package com.cn.flylo.ofpet.utils;

import android.view.View;

/**
 * @author axw_an
 * @create on 2019/10/24
 * @refer url：
 * @description:
 * @update: axw_an:2019/10/24:
 */
public class DoubleClickListener  implements View.OnClickListener {
    private static final long DOUBLE_TIME = 1000;
    private static long lastClickTime = 0;

    private DoubleClick doubleClick;
    public DoubleClickListener(DoubleClick doubleClick){
        this.doubleClick = doubleClick;
    }

    @Override
    public void onClick(View v) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastClickTime < DOUBLE_TIME) {
            if (doubleClick != null){
                doubleClick.onClick(v);
            }
        }
        lastClickTime = currentTimeMillis;
    }

    public interface DoubleClick{
        void onClick(View view);
    }
}
