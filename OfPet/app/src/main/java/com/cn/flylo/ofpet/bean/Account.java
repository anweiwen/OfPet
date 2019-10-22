package com.cn.flylo.ofpet.bean;

import com.cn.ql.frame.utils.StringUtils;

public class Account {
 
    public Account(){}

    public static Account instance;

    public static Account getInstance() {
        if (instance == null){
            synchronized (Account.class){
                if (instance == null){
                    instance = new Account();
                }
            }
        }
        return instance;
    }

    public static void setInstance(Account instance) {
        Account.instance = instance;
    }

    public boolean isLogin(){
        boolean login = !StringUtils.isEmpty(token);
        return login;
    }

    public int status;
    public int version;
    public String createTime;
    public int tokenId;
    public String token;
    public int userId;
    public String userRole;
    public String device;
    public String deviceTag;
    public String ipAddr;
    public String lastTime;
 
}
