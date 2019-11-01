package com.cn.flylo.ofpet.ui.page.main;

import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.bean.Video;
import com.cn.flylo.ofpet.bean.base.BaseBean;
import com.cn.flylo.ofpet.bean.base.DataListBean;
import com.cn.flylo.ofpet.bean.base.DataListDataBean;
import com.cn.flylo.ofpet.tool.event.EventTool;
import com.cn.flylo.ofpet.tool.event.EventType;
import com.cn.flylo.ofpet.ui.adapter.HomeAdapter;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.flylo.ofpet.url.api.UrlId;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import com.google.gson.JsonElement;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class IdleFgm extends BaseControllerFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int layoutId() {
        return R.layout.fragment_idle;
    }

    @Override
    public void InitView() {
        initRecycler();
        InitRefreshLayout();
    }


    @OnClick({R.id.image_top_menu})
    public void ViewClick(View view){
        switch (view.getId()){
            case R.id.image_top_menu:
                EventTool.getInstance().send(EventType.OpenMenu);
                break;
        }
    }

    private HomeAdapter adapter;
    private List<Video> list = new ArrayList();
    private void initRecycler(){
        GridLayoutManager linearLayoutManager = new GridLayoutManager(act, 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (adapter == null){
            adapter = new HomeAdapter(list);
        }
        recyclerView.setAdapter(adapter);
        adapter.setItemViewOnClickListener(new ItemViewOnClickListener<Video>() {
            @Override
            public void onClick(@NotNull View v, Video data, int position) {
                if (data == null){
                    return;
                }
                switch (v.getId()){
                    case R.id.layout_item:
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", data.videoId);
                        StartTool.INSTANCE.start(act, PageEnum.Look, bundle);
                        break;
                }
            }
        });
    }

    // refresh

    @Override
    public void refresh(@Nullable SmartRefreshLayout refreshlayout) {
        super.refresh(refreshlayout);
        page = 1;
        getNewVideos();
    }

    @Override
    public void load(@Nullable SmartRefreshLayout refreshlayout) {
        super.load(refreshlayout);
        page ++;
        getNewVideos();
    }

    // todo

    @Override
    public void InitLoad() {
        super.InitLoad();
        getNewVideos();
    }

    private int page = 1;
    private void getNewVideos(){
        getHttpTool().getVideo().getNewVideos("", 8, page);
    }

    @Override
    public void onNetSuccess(int urlId, @NotNull JsonElement jsonElement, @NotNull String value, @NotNull BaseBean baseBean, boolean success) {
        super.onNetSuccess(urlId, jsonElement, value, baseBean, success);
        switch (urlId){
            case UrlId.getNewVideos:
                if (success){
                    showVideos(value);
                }else{
                    showToast(baseBean.description);
                }
                break;
        }
    }

    private void showVideos(String value) {
        DataListBean<Video> bean = getBean(value, DataListBean.class, Video.class);
        if (page == 1){
            list.clear();
        }
        int size = list.size();
        int changeSize = 0;
        if (bean != null){
            DataListDataBean data = bean.result;
            if (data != null){
                List<Video> listTmp = data.content;
                if (listTmp != null){
                    list.addAll(listTmp);
                    changeSize = listTmp.size();
                }
                if (page != 1 && listTmp.size() == 0) {
                    page--;
                }
            }
        }
        if (page == 1) {
            adapter.notifyDataSetChanged();
        }else{
            adapter.notifyItemRangeInserted(size, changeSize);
        }
    }
}
