package com.cn.flylo.ofpet.ui.dialog;


import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.cn.flylo.ofpet.R;
import com.cn.ql.frame.tool.DensityTool;
import com.cn.ql.frame.tool.DisplayTool;

/**
 * Created by AXW on 2018/5/4.
 * 注：选择图片
 */

public class SearchTypePop implements View.OnClickListener {

    public int type; // 0 视频 1 用户
    private PopupWindow mPopupWindow;
    private Context context;

    public void showPopupWindow(View view) {
        context = view.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.p_search_type, null);
        inflate.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        mPopupWindow = new PopupWindow(inflate);

        inflate.findViewById(R.id.linear_parent).setOnClickListener(this);

        TextView tvText = inflate.findViewById(R.id.tvText);
        tvText.setOnClickListener(this);

        tvText.setText(type == 0?"用户 ": "视频 ");

        //必须设置宽和高
        mPopupWindow.setWidth(DisplayTool.INSTANCE.getScreenWidth(context));
        mPopupWindow.setHeight(DensityTool.Companion.dp2px(context, 120));
        mPopupWindow.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mPopupWindow.showAsDropDown(view, 0, 0, Gravity.BOTTOM);
        }
        mPopupWindow.update();
    }

    @Override
    public void onClick(View v) {
        if (viewClick != null){
            viewClick.onViewClick(v);
        }
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    public void setViewClick(ViewClick viewClick) {
        this.viewClick = viewClick;
    }

    private ViewClick viewClick;
    public interface ViewClick {
        void onViewClick(View v);
    }
}
