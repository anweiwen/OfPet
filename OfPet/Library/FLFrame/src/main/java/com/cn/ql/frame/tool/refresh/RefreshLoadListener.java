package com.cn.ql.frame.tool.refresh;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * Created by Administrator on 2017/9/5.
 */

public interface RefreshLoadListener {
    void onRefresh(RefreshLayout refreshlayout);
    void onLoadmore(RefreshLayout refreshlayout);
}
