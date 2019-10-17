package com.cn.flylo.ofpet.ui.page.mine;

import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.ui.adapter.BuyAdapter;
import com.cn.flylo.ofpet.ui.adapter.VideoAdapter;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BuyFgm extends BaseControllerFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int layoutId() {
        return R.layout.fragment_buy;
    }

    @Override
    public void InitView() {
        setTitle("购买头条", "", true);
        initRecycler();
    }

    @OnClick({R.id.tvConfirm})
    public void ViewClick(View view) {
        switch (view.getId()) {
            case R.id.tvConfirm:
                StartTool.INSTANCE.start(act, PageEnum.BuyConfirm);
                break;
        }
    }

    private BuyAdapter adapter;
    private List<Bean> list = new ArrayList();
    private void initRecycler(){
        GridLayoutManager linearLayoutManager = new GridLayoutManager(act, 3);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (adapter == null){
            adapter = new BuyAdapter(list);
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
                        break;
                }
            }
        });

        for (int i = 0; i < 10; i++){
            list.add(new Bean());
        }
        adapter.notifyDataSetChanged();
    }
}
