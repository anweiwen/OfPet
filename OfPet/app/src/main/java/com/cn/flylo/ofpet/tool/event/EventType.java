package com.cn.flylo.ofpet.tool.event;

/**
 * Created by AXW on 2018/5/10.
 * 注：
 */

public enum EventType {

    ToMainGame(1),
    OpenMenu(2),
    ;//

    int value;
    EventType(int value) {
        this.value = value;
    }

    public final int get() {
        return value;
    }
}
