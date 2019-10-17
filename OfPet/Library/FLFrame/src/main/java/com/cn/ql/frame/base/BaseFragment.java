package com.cn.ql.frame.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cn.ql.frame.base.impl.BaseInterface;
import com.cn.ql.frame.tool.ToastTool;
import com.cn.ql.frame.tool.event.EventBusBean;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author axw_an
 * @create on 2019/3/5
 * @refer url：
 * @description:
 * @update: axw_an:2019/3/5:
 */
public abstract class BaseFragment extends RxFragment implements BaseInterface {

    protected Activity act;
    protected View view;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            visibleToUser();
        }else{
            goneToUser();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            act = (Activity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        InitSettring();
        view = inflater.inflate(layoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //反注册EventBus
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        Bundle data = getArguments();
        if (data != null) {
            InitData(data);
        }
        InitView();
        Init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
    }

    // TODO: 2018/3/28 life cycle end

    /**
     * 初始化参数
     */
    protected void InitData(Bundle data){}

    protected void InitSettring() {}

    protected void Init() {}

    /**
     * 界面显示方法
     */
    protected void visibleToUser(){}
    protected void goneToUser(){}

    protected Fragment fgm;

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    protected void hideKeyboard() {
        View v = act.getCurrentFocus();
        if (v == null){
            return;
        }
        IBinder token = v.getWindowToken();
        if (token != null) {
            InputMethodManager im = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==Activity.RESULT_OK) {
            onActResult(requestCode, resultCode, data);
            handleResult(requestCode, resultCode, data);
        }
    }

    /**
     * 递归调用，对所有子Fragement生效
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(int requestCode, int resultCode,
                              Intent data) {
        List<Fragment> frags = getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null) {
                    f.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    protected void onActResult(int requestCode, int resultCode, Intent data){}

    /** 返回上一个界面的Intent */
    public void setActResult(Intent intent){
        //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
        if (act != null) {
            act.setResult(Activity.RESULT_OK, intent);
        }
        //此处一定要调用finish()方法
        act.finish();
    }

    /**
     * 提示
     * @param tip
     */
    protected void showToast(String tip){
        ToastTool.showToast(act, tip);
    }

    // TODO: 2018/3/28 第三方
    /**
     * EventBus
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean event){
        Bundle data = event.getData();
        if (data != null){
            int type = data.getInt("type");
            EventMessage(type, data);
        }
    }

    protected void EventMessage(int type, Bundle data){}

}
