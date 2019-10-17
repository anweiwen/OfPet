package com.cn.flylo.ofpet.tool;

import android.content.Context;

import com.cn.ql.frame.tool.SharedPreferencesTool;
import com.cn.ql.frame.utils.StringUtils;

/**
 * Created by AXW on 2018/5/3.
 * 注：保存用户信息
 */

public class SaveTool {

    private SharedPreferencesTool tool;
    private String name = "info";
    public SaveTool(Context context){
        if (tool == null){
            tool = new SharedPreferencesTool(context.getApplicationContext(), name);
        }
    }

    private final String VERSION = "VERSION"; // 版本号
    private final String USER = "USER"; // 用户信息
    private final String AREACODE = "AREACODE"; // 区号
    private final String ACCOUNT = "ACCOUNT"; // 账号
    private final String PASSWORD = "PASSWORD"; // 密码
    private final String SEARCH = "SEARCH"; // 搜索药品历史

    private final String LANGUAGE = "LANGUAGE"; // 语言

    public void putVersion(int version){
        tool.put(VERSION, version);
    }

    public int getVersion(){
        return (int) tool.get(VERSION, 0);
    }

    public void putUser(String user){
        tool.put(USER, user);
    }

    public String getUser(){
        return (String) tool.get(USER, "");
    }

    public void putSearch(String value){
        tool.put(SEARCH, value);
    }

    public String getSearch(){
        return (String) tool.get(SEARCH, "");
    }

    public void putLanguage(int language){
        tool.put(LANGUAGE, language);
    }

    public int getLanguage() {
        return (int) tool.get(LANGUAGE, 0);
    }

    public void putAccount(String account, String password){
        if (!StringUtils.isEmpty(account)) {
            tool.put(ACCOUNT, account);
        }
        if (!StringUtils.isEmpty(password)) {
            tool.put(PASSWORD, password);
        }
    }

    public String getAreaCode() {
        return (String) tool.get(AREACODE, "");
    }

    public String getAccount() {
        return (String) tool.get(ACCOUNT, "");
    }

    public String getPassword() {
        return (String) tool.get(PASSWORD, "");
    }

    public void clear(){
        tool.put("USER", "");
        tool.put("PASSWORD", "");

        tool.remove(USER);
        tool.remove(PASSWORD);
    }


    public void clearSearch(){
        tool.put(SEARCH, "");
        tool.remove(SEARCH);
    }
}
