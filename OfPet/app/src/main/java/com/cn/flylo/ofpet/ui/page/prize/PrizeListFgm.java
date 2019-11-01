package com.cn.flylo.ofpet.ui.page.prize;

import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.bean.Prize;
import com.cn.flylo.ofpet.bean.base.BaseBean;
import com.cn.flylo.ofpet.bean.base.DataListBean;
import com.cn.flylo.ofpet.bean.base.DataListDataBean;
import com.cn.flylo.ofpet.tool.event.EventTool;
import com.cn.flylo.ofpet.tool.event.EventType;
import com.cn.flylo.ofpet.ui.adapter.PrizeAdapter;
import com.cn.flylo.ofpet.ui.adapter.PrizeListAdapter;
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

public class PrizeListFgm extends BaseControllerFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int layoutId() {
        return R.layout.fragment_prize_list;
    }

    private int id;
    @Override
    protected void InitData(Bundle data) {
        super.InitData(data);
        id = data.getInt("id");
    }

    @Override
    public void InitView() {
        setTitle("奖品列表", "", true);
        initRecycler();
        InitRefreshLayout();
    }

    private PrizeListAdapter adapter;
    private List<Prize> list = new ArrayList();
    private void initRecycler() {
        LinearLayoutManager mgr = new LinearLayoutManager(act);
        recyclerView.setLayoutManager(mgr);
        if (adapter == null){
            adapter = new PrizeListAdapter(list);
        }
        recyclerView.setAdapter(adapter);
        adapter.setItemViewOnClickListener(new ItemViewOnClickListener<Prize>() {
            @Override
            public void onClick(@NotNull View v, Prize data, int position) {
                if (data == null){
                    return;
                }
                switch (v.getId()){
                    case R.id.layout_item:
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", data.prizeId);
                        StartTool.INSTANCE.start(act, PageEnum.PrizeDetails, bundle);
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
        InitLoad();
    }

    @Override
    public void load(@Nullable SmartRefreshLayout refreshlayout) {
        super.load(refreshlayout);
        page ++;
        getPrizeList();
    }

    // todo
    @Override
    public void InitLoad() {
        super.InitLoad();
        getPrizeList();
    }

    private int page = 1;
    private void getPrizeList(){
        getHttpTool().getPrize().getPrizeList(id, page);
    }

    @Override
    public void onNetSuccess(int urlId, @NotNull JsonElement jsonElement, @NotNull String value, @NotNull BaseBean baseBean, boolean success) {
        super.onNetSuccess(urlId, jsonElement, value, baseBean, success);
        switch (urlId){
            case UrlId.getPrizeList:
                if (success){
                    showPrizeList(value);
                }else{
                    showToast(baseBean.description);
                }
                break;
        }
    }

    private void showPrizeList(String value) {
        DataListBean<Prize> bean = getBean(value, DataListBean.class, Prize.class);
        if (page == 1){
            list.clear();
        }
        int size = list.size();
        int changeSize = 0;
        if (bean != null){
            DataListDataBean data = bean.result;
            if (data != null){
                List<Prize> listTmp = data.content;
                if (listTmp != null){
                    changeSize = listTmp.size();
                    list.addAll(listTmp);
                }
                if (page != 1 && changeSize == 0) {
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
