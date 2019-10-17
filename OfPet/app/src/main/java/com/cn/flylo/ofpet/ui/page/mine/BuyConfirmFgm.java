package com.cn.flylo.ofpet.ui.page.mine;

import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.ui.adapter.BuyAdapter;
import com.cn.flylo.ofpet.ui.adapter.MoneyAdapter;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BuyConfirmFgm extends BaseControllerFragment {

    @BindView(R.id.recyclerViewFind)
    RecyclerView recyclerViewFind;
    @BindView(R.id.recyclerViewCity)
    RecyclerView recyclerViewCity;

    @Override
    public int layoutId() {
        return R.layout.fragment_buy_confirm;
    }

    @Override
    public void InitView() {
        setTitle("购买头条", "", true);
        initRecycler();
        initRecyclerCity();
    }

    private MoneyAdapter adapter;
    private List<Bean> list = new ArrayList();
    private void initRecycler(){
        ChipsLayoutManager mgr = ChipsLayoutManager.newBuilder(act)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();
        recyclerViewFind.setLayoutManager(mgr);
        if (adapter == null){
            adapter = new MoneyAdapter(list);
        }
        recyclerViewFind.setAdapter(adapter);
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

    private MoneyAdapter adapter_city;
    private List<Bean> list_city = new ArrayList();
    private void initRecyclerCity(){
        ChipsLayoutManager mgr = ChipsLayoutManager.newBuilder(act)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();
        recyclerViewCity.setLayoutManager(mgr);
        if (adapter_city == null){
            adapter_city = new MoneyAdapter(list_city);
        }
        recyclerViewCity.setAdapter(adapter_city);
        adapter_city.setItemViewOnClickListener(new ItemViewOnClickListener<Bean>() {
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
            list_city.add(new Bean());
        }
        adapter_city.notifyDataSetChanged();
    }
}
