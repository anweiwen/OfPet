package com.cn.ql.frame.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ViewTool {

    public static final int DialogLR = 60;

    public static void InitView(Context mContext, View view, int viewID) {
        InitView(mContext, view, viewID, DialogLR);
    }

    /**
     * LinearLayout布局设置左右间距
     * @param mContext
     * @param view
     * @param viewID
     * @param LR
     */
    public static void InitView(Context mContext, View view, int viewID, int LR) {
        LinearLayout llParent = view.findViewById(viewID);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) llParent.getLayoutParams();
        lp.width = (int) (DisplayTool.INSTANCE.getScreenWidth(mContext) - DensityTool.Companion.dp2px(mContext, LR));
        llParent.setLayoutParams(lp);
    }

    public static Bitmap createDrawableFromView(Context context, View view){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Activity activity = (Activity) context;
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


    public static void setLayoutParam(View view, Float dpVal){
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int wh = DensityTool.Companion.dp2px(view.getContext(), dpVal);
        lp.width = wh;
        lp.height = wh;
        view.setLayoutParams(lp);
    }
}
