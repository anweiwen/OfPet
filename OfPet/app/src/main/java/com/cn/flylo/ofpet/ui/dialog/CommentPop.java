package com.cn.flylo.ofpet.ui.dialog;


import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.cn.flylo.ofpet.R;
import com.cn.ql.frame.widget.PopupWindowView;

/**
 * Created by AXW on 2018/5/4.
 * 注：选择图片
 */

public class CommentPop {

    private PopupWindowView.Builder builder;
    private View view;
    private Context mContext;

    public View show(Context context, View downView, ViewClick viewClick) {
        this.mContext = context;
        this.viewClick = viewClick;
        if (builder != null) {
            builder.dismiss();
        }
        builder = new PopupWindowView.Builder(context, R.layout.p_comment);
        builder.setStyle(R.style.DialogStyle);
        builder.show(true, downView);
        view = builder.getView();
        ClickView();
        return view;
    }

    private void ClickView() {

    }

    private ViewClick viewClick;
    public interface ViewClick {
        void onViewClick(View v);
    }
}
