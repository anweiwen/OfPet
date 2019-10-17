package com.cn.ql.frame.tool;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by AXW on 2017/11/16.
 * 注：控件与界面左右边距
 */

public class QAViewTool {
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

    /**
     *
     * @param editText
     * @return true 可见 false 不可见
     */
    public static boolean changeEtShow(EditText editText){
        boolean result = false;
        int type = editText.getInputType();
        if (type == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){ //密码不可见
            type = InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD;
            result = false;
        }else{ //密码可见
            type = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
            result = true;
        }
        editText.setInputType(type);
        String str = editText.getText().toString();
        if(!TextUtils.isEmpty(str)){
            int len = str.trim().length();
            editText.setSelection(len);
        }
        return result;
    }

    /**
     *
     * @param editText
     * @return true 可见 false 不可见
     */
    public static boolean changeEtNumShow(EditText editText){
        boolean result = false;
        int type = editText.getInputType();
        int nowType = InputType.TYPE_CLASS_NUMBER| InputType.TYPE_NUMBER_FLAG_SIGNED;
        if (type == nowType){ //密码不可见
            type = InputType.TYPE_CLASS_NUMBER| InputType.TYPE_NUMBER_VARIATION_PASSWORD;
            result = false;
        }else{ //密码可见
            type = InputType.TYPE_CLASS_NUMBER| InputType.TYPE_NUMBER_FLAG_SIGNED;
            result = true;
        }
        editText.setInputType(type);
        String str = editText.getText().toString();
        if(!TextUtils.isEmpty(str)){
            int len = str.trim().length();
            editText.setSelection(len);
        }
        return result;
    }

    /**
     * EditText竖直方向是否可以滚动
     * @param editText  需要判断的EditText
     * @return  true：可以滚动   false：不可以滚动
     */
    public static boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

    /**
     * 输入框加删除按钮 输入框为空的时候删除按钮隐藏
     * @param view 删除按钮 View
     * @param editText 输入框
     * @return
     */
    public static boolean DelText(final View view, final EditText editText){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int len = editable.length();
                view.setVisibility(len == 0? View.GONE: View.VISIBLE);
            }
        });
        return false;
    }

    private void search(EditText editText){
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH) {

                }
                return false;
            }
        });
    }

    /**
     * 显示到顶部
     * @param top 顶部的一个布局
     * @param other 焦点高的View
     */
    public static void seeTop(View top, View other){
        other.setFocusable(false);
        top.setFocusable(true);
        top.setFocusableInTouchMode(true);
        top.requestFocus();
    }

    /**
     * 获取控件内容的宽度
     * @param view
     * @return
     */
    public static int getViewWidth(View view){
        view.measure(0,  0);
        return view.getMeasuredWidth();
    }


    /**
     * 获取控件内容的高度
     * @param view
     * @return
     */
    public static int getViewHight(View view){
        view.measure( 0,  0);
        return view.getMeasuredHeight();
    }

    public static void setViewWidth(View view, int width){
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = width;
        view.setLayoutParams(lp);
    }


    /**
     * 获取ActionBarSize的长度
     * @param context
     * @return
     */
    public static int getActionBarSize(Context context) {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

}
