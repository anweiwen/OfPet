package com.cn.flylo.ofpet.tool.event;

import android.os.Bundle;

import com.cn.ql.frame.tool.event.EventBusTool;

/**
 * Created by AXW on 2018/5/10.
 * 注：
 */
public class EventTool {
    private static EventTool ourInstance = new EventTool();

    public static EventTool getInstance() {
        return ourInstance;
    }

    private EventTool() {
    }

    /**
     * 发送消息
     * @param type
     */
    public void send(EventType type){
        EventBusTool.Companion.getInstance().Send(type.get());
    }

    /**
     * 发送消息
     * @param type
     */
    public void send(EventType type, Bundle data){
        EventBusTool.Companion.getInstance().Send(type.get(), data);
    }

    /**
     * 打开个人中心的菜单
     */
    public void OpenMenu(){
    }

    public void ShowLoading(){
    }

    public void HideLoading(){
    }

}
