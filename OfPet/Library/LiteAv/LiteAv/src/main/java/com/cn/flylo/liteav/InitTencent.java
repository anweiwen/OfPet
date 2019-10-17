package com.cn.flylo.liteav;

import android.content.Context;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.ugc.TXUGCBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InitTencent {


    public static void Init(Context context, String licenceUrl, String licenseKey){
        TXLiveBase.setConsoleEnabled(true);

        TXLiveBase.getInstance().setLicence(context, licenceUrl, licenseKey);
        TXUGCBase.getInstance().setLicence(context, licenceUrl, licenseKey);

        closeAndroidPDialog();
    }


    private static void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
