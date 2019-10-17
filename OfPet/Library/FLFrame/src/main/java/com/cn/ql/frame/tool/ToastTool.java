package com.cn.ql.frame.tool;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * @author AXW on 2018/3/28.
 * 注：提示
 */
public class ToastTool {

    private static Toast mToast;
    /**
     * 提示语
     * @param context
     * @param text
     */
    public static void showToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showToast(Context context, View view, int duration){
        if (context == null){
            return;
        }
        Toast mToast = new Toast(context.getApplicationContext());
        mToast.setView(view);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(duration);
        mToast.show();
    }

}
