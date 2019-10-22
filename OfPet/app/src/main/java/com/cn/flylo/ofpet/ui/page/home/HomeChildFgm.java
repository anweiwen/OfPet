package com.cn.flylo.ofpet.ui.page.home;

import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.bean.Video;
import com.cn.flylo.ofpet.bean.base.BaseBean;
import com.cn.flylo.ofpet.bean.base.ListBean;
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

public class HomeChildFgm extends BaseControllerFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int layoutId() {
        return R.layout.fragment_home_child;
    }

    private int type;

    @Override
    protected void InitData(Bundle data) {
        super.InitData(data);
        type = data.getInt("type");
    }

    @Override
    public void InitView() {
        initRecycler();
        InitRefreshLayout();
    }

    private HomeAdapter adapter;
    private List<Video> list = new ArrayList();
    private void initRecycler(){
        recyclerView.setLayoutManager(new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

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
        refresh();
    }

    @Override
    public void load(@Nullable SmartRefreshLayout refreshlayout) {
        super.load(refreshlayout);
        load();
    }

    // todo

    @Override
    public void InitLoad() {
        super.InitLoad();
        refresh();
    }

    private void refresh(){
        page = 1;
        switch (type){
            case 2:
                getPopularVideo();
                break;
        }
    }

    private void load(){
        page++;
        switch (type){
            case 2:
                getPopularVideo();
                break;
        }
    }

    private int page = 1;
    private void getPopularVideo(){
        getHttpTool().getVideo().getPopularVideo(type, page);
    }

    @Override
    public void onNetSuccess(int urlId, @NotNull JsonElement jsonElement, @NotNull String value, @NotNull BaseBean baseBean, boolean success) {
        super.onNetSuccess(urlId, jsonElement, value, baseBean, success);
        switch (urlId){
            case UrlId.getPopularVideo:
                if (success){
                    showData(value);
                }else{
                    showToast(baseBean.description);
                }
                break;
        }
    }

    private void showData(String value) {
        ListBean<Video> bean = getBean(value, ListBean.class, Video.class);
        if (page == 1){
            list.clear();
        }
        int size = list.size();
        int changeSize = 0;

        if (bean != null){
            List<Video> listTmp = bean.result;
            if (listTmp != null){
                list.addAll(listTmp);
                changeSize = listTmp.size();
            }
            if (page != 1 && listTmp.size() == 0){
                page --;
            }
        }
        if (page == 1) {
            adapter.notifyDataSetChanged();
        }else{
            adapter.notifyItemRangeInserted(size, changeSize);
        }
    }
}
