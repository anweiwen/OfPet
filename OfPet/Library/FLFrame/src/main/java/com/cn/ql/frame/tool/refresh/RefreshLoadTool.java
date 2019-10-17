package com.cn.ql.frame.tool.refresh;

import android.content.Context;

import androidx.core.content.ContextCompat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by Administrator on 2017/9/5.
 */

public class RefreshLoadTool {


//    public static String REFRESH_HEADER_PULLDOWN = "下拉可以刷新";
//    public static String REFRESH_HEADER_REFRESHING = "正在刷新...";
//    public static String REFRESH_HEADER_LOADING = "正在加载...";
//    public static String REFRESH_HEADER_RELEASE = "释放立即刷新";
//    public static String REFRESH_HEADER_FINISH = "刷新完成";
//    public static String REFRESH_HEADER_FAILED = "刷新失败";
//    public static String REFRESH_HEADER_LASTTIME = "上次更新 M-d HH:mm";
//    public static String REFRESH_HEADER_SECOND_FLOOR = "释放进入二楼";

    public static void InitView(Context context){

    }

    public static void Init(final int bgColor, final int textColor){
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                RefreshAttrs(layout);
                layout.setPrimaryColorsId(bgColor, textColor);
                ClassicsHeader header = new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);
                return header;
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                RefreshAttrs(layout);
                ClassicsFooter footer = new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
                footer.setAccentColor(ContextCompat.getColor(context, textColor));
                return footer;
            }
        });
    }

    public static void InitRefreshLoad(SmartRefreshLayout refreshLayout, final RefreshLoadListener listener){
        InitRefreshLoad(refreshLayout, true, listener);
    }

    public static void InitRefreshLoad(SmartRefreshLayout refreshLayout, boolean loadMore, final RefreshLoadListener listener){
        if (refreshLayout == null){
            return;
        }
        ClassicsHeader header = new ClassicsHeader(refreshLayout.getContext()).setSpinnerStyle(SpinnerStyle.Translate);
        refreshLayout.setRefreshHeader(header);

        ClassicsFooter footer = new ClassicsFooter(refreshLayout.getContext()).setSpinnerStyle(SpinnerStyle.Translate);
        refreshLayout.setRefreshFooter(footer);

        if (refreshLayout != null) {
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    refreshlayout.finishRefresh(15 * 1000);
                    if (listener != null) {
                        listener.onRefresh(refreshlayout);
                    }
                }
            });
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshLayout) {
                    refreshLayout.finishLoadMore(15 * 1000);
                    if (listener != null) {
                        listener.onLoadmore(refreshLayout);
                    }
                }
            });
            refreshLayout.setEnableLoadMore(loadMore);
            RefreshAttrs(refreshLayout);
        }
    }

    public static void finishRefreshAndLoad(SmartRefreshLayout refreshLayout){
        if (refreshLayout != null) {
            refreshLayout.finishRefresh(true);
            refreshLayout.finishLoadMore(true);
        }
    }

    public static void RefreshAttrs(RefreshLayout refreshLayout){
        if (refreshLayout != null) {
            refreshLayout.setHeaderHeight(50);
            refreshLayout.setFooterHeight(50);
            refreshLayout.setEnableOverScrollBounce(false);
            refreshLayout.setEnableAutoLoadMore(false);
        }
    }

}
