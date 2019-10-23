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
import com.cn.flylo.ofpet.bean.base.BaseBean;
import com.cn.flylo.ofpet.tool.event.EventTool;
import com.cn.flylo.ofpet.tool.event.EventType;
import com.cn.flylo.ofpet.ui.adapter.PrizeAdapter;
import com.cn.flylo.ofpet.ui.adapter.PrizeListAdapter;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.flylo.ofpet.url.api.UrlId;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

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
    }


    private PrizeListAdapter adapter;
    private List<Bean> list = new ArrayList();
    private void initRecycler() {
        LinearLayoutManager mgr = new LinearLayoutManager(act);
        recyclerView.setLayoutManager(mgr);
        if (adapter == null){
            adapter = new PrizeListAdapter(list);
        }
        recyclerView.setAdapter(adapter);
        adapter.setItemViewOnClickListener(new ItemViewOnClickListener<Bean>() {
            @Override
            public void onClick(@NotNull View v, Bean data, int position) {
                if (data == null){
                    return;
                }
                switch (v.getId()){
                    case R.id.layout_item:
                        StartTool.INSTANCE.start(act, PageEnum.PrizeDetails);
                        break;
                }
            }
        });

        for (int i = 0; i < 10; i++){
            list.add(new Bean());
        }
        adapter.notifyDataSetChanged();

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

                }else{
                    showToast(baseBean.description);
                }
                break;
        }
    }
}
