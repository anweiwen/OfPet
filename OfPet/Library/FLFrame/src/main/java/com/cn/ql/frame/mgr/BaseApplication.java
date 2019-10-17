package com.cn.ql.frame.mgr;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import com.cn.ql.frame.tool.log.FlyLog;
import com.cn.ql.frame.utils.Utils;


/**
 * @author axw_an
 * @create on 2019/3/5
 * @refer url：
 * @description:
 * @update: axw_an:2019/3/5:
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        InitApp();
        Init();
    }

    protected void Init(){}

    private void InitApp() {
        Utils.init(this);

        initLifecycle();
        initLog(true);
    }

    public void initLog(final boolean showLog) {
        FlyLog.init().setTag("Flylo").setDebug(showLog);
    }

    private void initLifecycle() {
        registerActivityLifecycleCallbacks(mCallbacks);
    }

    /**
     * RxLifecycle 的周期管理
     */
    private ActivityLifecycleCallbacks mCallbacks = new ActivityLifecycleCallbacks() {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (activity instanceof FragmentActivity){
                FragmentActivity fragmentActivity = (FragmentActivity) activity;
                AppManager.getAppManager().addActivity(fragmentActivity);
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            AppManager.getAppManager().removeActivity(activity);
        }
    };

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
