package com.cn.ql.frame.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.cn.ql.frame.R;


/**
 * Created by AXW on 2017/7/16.
 * 注：弹窗Dialog
 */
public class DialogView extends Dialog{

    public DialogView(Context context) {
        super(context);
    }

    public DialogView(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder{
        private Context context;
        private int layoutId;
        private View view;

        /**
         *
         * @param context 上下文
         * @param layoutId 弹窗布局layout
         */
        public Builder(Context context,int layoutId){
            this.context=context;
            this.layoutId = layoutId;
        }
        
        private DialogView myDialog;
        public DialogView show(boolean touchOutSide){
        	LayoutInflater inflater = LayoutInflater.from(context);
            myDialog=new DialogView(context, R.style.dialog);
            view = inflater.inflate(layoutId, null);
            myDialog.setContentView(view);
            myDialog.setCanceledOnTouchOutside(touchOutSide);
            myDialog.show();
            return myDialog;
        }

        public DialogView getMyDialog() {
            return myDialog;
        }

        public View getView(){
        	return view;
        }
        
        public void dismiss(){
        	if (myDialog.isShowing()) {
            	myDialog.dismiss();
			}
            context = null;
        }

    }

}
