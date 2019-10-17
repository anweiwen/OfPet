package com.cn.ql.frame.mgr;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.List;
import java.util.Stack;

/**
 * Created by AXW on 2018/3/27.
 * activity堆栈式管理
 */
public class AppManager {

    private static Stack<FragmentActivity> activityStack = new Stack<FragmentActivity>();
    private static Stack<Fragment> fragmentStack = new Stack<Fragment>();
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单例模式
     *
     * @return AppManager
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public static Stack<FragmentActivity> getActivityStack() {
        return activityStack;
    }

    public static Stack<Fragment> getFragmentStack() {
        return fragmentStack;
    }


    /**
     * 添加Activity到堆栈
     */
    public void addActivity(FragmentActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<FragmentActivity>();
        }
        activityStack.add(activity);
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            if (activity instanceof AppCompatActivity) {
                List<Fragment> list = ((AppCompatActivity) activity).getSupportFragmentManager().getFragments();
                for (int i=0;i<list.size();i++){
                    Fragment fgm = list.get(i);
                    if (fgm != null){
                        removeFragment(fgm);
                    }
                }
            }
            activityStack.remove(activity);
            activity.finish();
        }
    }


    /**
     * 是否有activity
     */
    public boolean isActivity() {
        if (activityStack != null) {
            return !activityStack.isEmpty();
        }
        return false;
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack == null) return null;
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if (activityStack == null) return;
        FragmentActivity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(FragmentActivity activity) {
        if (activity != null) {
            if (!activity.isFinishing()) {
                activity.finish();
                activityStack.remove(activity);
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack == null){
            return;
        }
        for (FragmentActivity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack == null) return;
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                finishActivity(activityStack.get(i));
            }
        }
        activityStack.clear();
    }

    /**
     * 获取指定的Activity
     *
     * @author kymjs
     */
    public Activity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }


    /**
     * 添加Fragment到堆栈
     */
    public void addFragment(Fragment fragment) {
        if (fragmentStack == null) {
            fragmentStack = new Stack<Fragment>();
        }
        fragmentStack.add(fragment);
    }

    /**
     * 移除指定的Fragment
     */
    public void removeFragment(Fragment fragment) {
        if (fragment != null && fragmentStack != null) {
            fragmentStack.remove(fragment);
        }
    }


    /**
     * 是否有Fragment
     */
    public boolean isFragment() {
        if (fragmentStack != null) {
            return !fragmentStack.isEmpty();
        }
        return false;
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Fragment currentFragment() {
        if (fragmentStack != null) {
            Fragment fragment = fragmentStack.lastElement();
            return fragment;
        }
        return null;
    }

    public void finishActivityToFragment(Class<?> cls) {
        if (activityStack == null){
            return;
        }
        for (int i = activityStack.size() - 1; i >= 0;i--) {
            FragmentActivity activity = activityStack.get(i);
            List<Fragment> list = activity.getSupportFragmentManager().getFragments();
            if (list != null){
                if (list.size() != 0){
                    Fragment fgm = list.get(0);
                    String name = cls.getName();
                    String fgmName = fgm.getClass().getName();
                    if (name.equals(fgmName)){
                        return;
                    }
                }
            }
            finishActivity(activity);
        }
    }

    public void finishActivityToFragmentS(Class<?> cls) {
        if (activityStack == null){
            return;
        }
        for (int i = 0; i < activityStack.size(); i++) {
            FragmentActivity activity = activityStack.get(i);
            List<Fragment> list = activity.getSupportFragmentManager().getFragments();
            if (list != null){
                if (list.size() != 0){
                    Fragment fgm = list.get(0);
                    String name = cls.getName();
                    String fgmName = fgm.getClass().getName();
                    if (name.equals(fgmName)){
                        return;
                    }
                }
            }
            finishActivity(activity);
        }
    }

    /**
     * 退出所有关联的Activity
     * @param cls
     */
    public void finishActivityFragment(Class<?> cls) {
        if (activityStack == null){
            return;
        }
        for (int i = activityStack.size() - 1; i >= 0;i--) {
            FragmentActivity activity = activityStack.get(i);
            List<Fragment> list = activity.getSupportFragmentManager().getFragments();
            if (list != null){
                if (list.size() != 0){
                    Fragment fgm = list.get(0);
                    String name = cls.getName();
                    String fgmName = fgm.getClass().getName();
                    if (name.equals(fgmName)){
                        finishActivity(activity);
                    }
                }
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            activityStack.clear();
            e.printStackTrace();
        }
    }
}