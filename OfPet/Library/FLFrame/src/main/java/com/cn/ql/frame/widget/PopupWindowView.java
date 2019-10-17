package com.cn.ql.frame.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import androidx.core.content.ContextCompat;

/**
 * Created by AXW on 2017/7/16.
 * 注：PopupWindow
 */
public class PopupWindowView extends PopupWindow {

    public PopupWindowView(Context context) {
        super(context);
    }

    public PopupWindowView(View view, int matchParent, int matchParent1, boolean b) {
        super(view, matchParent, matchParent1, b);
    }

    public static class Builder {
        private Context context;
        private int layoutId;
        private View view;
        private int style;
        private boolean isAll = true;
        private boolean asDown = false;

        private boolean asTop = false;

        public boolean viewBottom = false;

        /**
         * @param context  上下文
         * @param layoutId 弹窗布局layout
         */
        public Builder(Context context, int layoutId) {
            this.context = context;
            this.layoutId = layoutId;
        }

        /**
         * 进入退出动画的style
         *
         * @param style
         */
        public void setStyle(int style) {
            this.style = style;
        }

        /**
         * 属性
         *
         * @param isAll  是否全屏
         * @param asDown 是否可点击
         */
        public void setAttrs(boolean isAll, boolean asDown) {
            this.isAll = isAll;
            this.asDown = asDown;
        }

        /**
         * 属性
         *
         * @param isAll  是否全屏
         * @param asDown 是否可点击
         */
        public void setAttrs(boolean isAll, boolean asDown, boolean asTop) {
            this.isAll = isAll;
            this.asDown = asDown;
            this.asTop = asTop;
        }

        private PopupWindowView pop;

        public PopupWindowView show(boolean touchOutSide, View downView) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layoutId, null);
            if (!isAll) {
                pop = new PopupWindowView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, true);
            } else {
                if (asDown) {
                    pop = new PopupWindowView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT, true);
                } else {
                    pop = new PopupWindowView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, true);
                }

            }
            pop.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent)));
            pop.setOutsideTouchable(touchOutSide);
            pop.setAnimationStyle(style);

            if (viewBottom){
                pop.showAsDropDown(downView);
            }else{
                if (asDown) {
                    pop.showAsDropDown(downView, 0, 0);
                    setBackgroundAlpha(1.0f);
                } else {
                    pop.showAtLocation(downView, Gravity.BOTTOM, 0, 0);
                    setBackgroundAlpha(0.5f);
                }
            }



            pop.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1.0f);
                    if (viewClick != null){
                        viewClick.onDismiss();
                    }
                }
            });
            return pop;
        }

        public View getView() {
            return view;
        }

        public void dismiss() {
            if (context != null) {
                if (context instanceof Activity) {
                    Activity act = (Activity) context;
                    if (act.isFinishing()) {
                        return;
                    }
                }
            }
            if (pop != null) {
                if (pop.isShowing()) {
                    pop.dismiss();
                }
            }
            context = null;
        }

        public void setBackgroundAlpha(float bgAlpha) {
            WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                    .getAttributes();
            lp.alpha = bgAlpha;
            ((Activity) context).getWindow().setAttributes(lp);
        }

        private ViewClick viewClick;

        public void setViewClick(ViewClick viewClick) {
            this.viewClick = viewClick;
        }

        public interface ViewClick {
            void onDismiss();
        }

    }

}
