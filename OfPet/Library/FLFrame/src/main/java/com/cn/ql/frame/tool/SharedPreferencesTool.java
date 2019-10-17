package com.cn.ql.frame.tool;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by AXW on 2018/5/3.
 * 注：保存信息
 */
public class SharedPreferencesTool {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public SharedPreferencesTool(Context context, String name){
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 保存信息 包含提交
     * @param key key
     * @param value 值
     */
    public void put(String key, Object value) {
        String type = value.getClass().getSimpleName();
        if ("String".equals(type)) {
            editor.putString(key, (String) value);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) value);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) value);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) value);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) value);
        }
        editor.commit();
    }

    /**
     * 装载数据
     */
    public SharedPreferences.Editor putString(String key, String value) {
        return editor.putString(key, value);
    }

    public SharedPreferences.Editor putInt(String key, int value) {
        return editor.putInt(key, value);
    }

    public SharedPreferences.Editor putLong(String key, long value) {
        return editor.putLong(key, value);
    }

    public SharedPreferences.Editor putFloat(String key, float value) {
        return editor.putFloat(key, value);
    }

    public SharedPreferences.Editor putBoolean(String key, boolean value) {
        return editor.putBoolean(key, value);
    }

    /**
     * 获取指定Key的信息
     * @param key key
     * @param defaultValue 默认值
     * @return
     */
    public Object get(String key, Object defaultValue) {
        String type = defaultValue.getClass().getSimpleName();
        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultValue);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultValue);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultValue);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultValue);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultValue);
        }
        return null;
    }

    /**
     * 删除指定key的内容
     */
    public SharedPreferences.Editor remove(String key) {
        return editor.remove(key);
    }

    /**
     * 清空所有内容
     */
    public SharedPreferences.Editor clear() {
        return editor.clear();
    }

    /**
     * 提交数据
     */
    public boolean commit() {
        return editor.commit();
    }
}
