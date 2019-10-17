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

public class SelectPayPop {

    private PopupWindowView.Builder builder;
    private View view;
    private Context mContext;

    public View show(Context context, View downView, ViewClick viewClick) {
        this.mContext = context;
        this.viewClick = viewClick;
        if (builder != null) {
            builder.dismiss();
        }
        builder = new PopupWindowView.Builder(context, R.layout.p_select_pay);
        builder.setStyle(R.style.Pop_Bottom_Anim);
        builder.show(true, downView);
        view = builder.getView();
        ClickView();
        return view;
    }

    private void ClickView() {
        TextView text_cancel = view.findViewById(R.id.text_cancel);

        text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
    }

    private ViewClick viewClick;
    public interface ViewClick {
        void onViewClick(View v);
    }
}
