package com.cn.ql.frame.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cn.ql.frame.base.impl.BaseImpl;
import com.cn.ql.frame.base.impl.BaseInterface;
import com.cn.ql.frame.tool.ToastTool;
import com.cn.ql.frame.tool.event.EventBusBean;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;


/**
 * @author axw_an
 * @create on 2019/3/5
 * @refer url：
 * @description:
 * @update: axw_an:2019/3/5:
 */
public abstract class BaseActivity extends RxAppCompatActivity implements BaseInterface {

    protected BaseImpl baseImpl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitSetting();
        setContentView(layoutId());

        ButterKnife.bind(this);

        baseImpl = new BaseImpl();
        baseImpl.initBindToLifecycle(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            InitData(bundle);
        }
        InitView();

        //initRx();

    }

    private void initRx(){
        // 当执行onDestory()时， 自动解除订阅
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                    }
                })
                .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long num) throws Exception {
                    }
                });
    }

    protected void InitData(Bundle bundle){}

    protected void InitSetting(){}

    @Override
    protected void onResume() {
        super.onResume();
        baseImpl.initBindUntilEvent(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            onActResult(requestCode, data);
            // 确保Activity下的Fragment也可以获取返回数据
            handleResult(requestCode, resultCode, data);
        }
    }

    /**
     * 设置字体大小不受系统设置
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(int requestCode, int resultCode,
                              Intent data) {
        List<Fragment> frags = getSupportFragmentManager().getFragments();
        if (frags != null && frags.size() != 0) {
            for (Fragment f : frags) {
                if (f != null) {
                    f.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    protected void onActResult(int requestCode, Intent data) {}


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsResult(requestCode, permissions, grantResults);
        onPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    private void PermissionsResult(int requestCode, String[] permissions,
                                   int[] grantResults) {
        List<Fragment> frags = getSupportFragmentManager().getFragments();
        if (frags != null && frags.size() != 0) {
            for (Fragment f : frags) {
                if (f != null) {
                    f.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        }
    }

    /**
     * 获取权限返回
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    protected void onPermissionsResult(int requestCode, String[] permissions, int[] grantResults) { }

    /**
     * 返回上一个界面的Intent
     * @param intent
     */
    public void setActResult(Intent intent) {
        //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
        this.setResult(RESULT_OK, intent);
        //此处一定要调用finish()方法
        this.finish();
    }

    /**
     * 提示
     * @param tip
     */
    protected void showToast(String tip) {
        ToastTool.showToast(getApplicationContext(), tip);
    }

    protected void showToast(int id) {
        ToastTool.showToast(getApplicationContext(), getString(id));
    }

    // TODO: 2018/3/28 第三方

    /**
     * EventBus
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean event) {
        Bundle data = event.getData();
        if (data != null) {
            int type = data.getInt("type");
            EventMessage(type, data);
        }
    }

    protected void EventMessage(int type, Bundle data) { }

}
