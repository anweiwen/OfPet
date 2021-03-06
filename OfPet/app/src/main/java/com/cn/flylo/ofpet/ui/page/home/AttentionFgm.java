package com.cn.flylo.ofpet.ui.page.home;

import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.ui.adapter.AttentionAdapter;
import com.cn.flylo.ofpet.ui.adapter.HomeAdapter;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AttentionFgm extends BaseControllerFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int layoutId() {
        return R.layout.fragment_attention;
    }

    @Override
    public void InitView() {
        initRecycler();
    }

    private AttentionAdapter adapter;
    private List<Bean> list = new ArrayList();
    private void initRecycler(){
        GridLayoutManager linearLayoutManager = new GridLayoutManager(act, 1);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (adapter == null){
            adapter = new AttentionAdapter(list);
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
