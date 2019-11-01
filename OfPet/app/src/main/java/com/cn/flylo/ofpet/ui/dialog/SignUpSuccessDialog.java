package com.cn.flylo.ofpet.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import com.cn.flylo.ofpet.R;
import com.cn.ql.frame.tool.ViewTool;
import com.cn.ql.frame.widget.DialogView;

/**
 * @author axw_an
 * @create on 2019/5/28
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/28:
 */
public class SignUpSuccessDialog {

    private DialogView.Builder builder;
    private View view;
    private Context mContext;

    public View show(Context context) {
        this.mContext = context;
        if (builder != null) {
            builder.dismiss();
        }
        builder = new DialogView.Builder(context, R.layout.d_sign_up_success);
        builder.show(true);
        view = builder.getView();
        InitView();
        return view;
    }

    public void dismiss() {
        if (mContext != null){
            if (mContext instanceof Activity){
                Activity act = (Activity) mContext;
                if (act.isFinishing()){
                    return;
                }
            }
        }
        if (builder != null) {
            builder.dismiss();
            mContext = null;
        }
    }

    private void InitView() {
        builder.getMyDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (viewClick != null){
                    viewClick.dismiss();
                }
            }
        });
        ViewTool.InitView(mContext, view, R.id.llParent, 50);

        view.findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    private ViewClick viewClick;
    public void setViewClick(ViewClick viewClick) {
        this.viewClick = viewClick;
    }
    public interface ViewClick {
        void onViewClick(View v);
        void dismiss();
    }

}
