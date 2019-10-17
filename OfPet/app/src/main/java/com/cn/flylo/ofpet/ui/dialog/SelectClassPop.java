package com.cn.flylo.ofpet.ui.dialog;


import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.ui.adapter.ClassAdapter;
import com.cn.flylo.ofpet.ui.adapter.PrizeAdapter;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import com.cn.ql.frame.widget.PopupWindowView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AXW on 2018/5/4.
 * 注：选择图片
 */

public class SelectClassPop {

    private PopupWindowView.Builder builder;
    private View view;
    private Context mContext;

    public View show(Context context, View downView, ViewClick viewClick) {
        this.mContext = context;
        this.viewClick = viewClick;
        if (builder != null) {
            builder.dismiss();
        }
        builder = new PopupWindowView.Builder(context, R.layout.p_select_class);
        builder.setStyle(R.style.Pop_Bottom_Anim);
        builder.show(true, downView);
        view = builder.getView();
        ClickView();
        return view;
    }

    private RecyclerView recyclerView;
    private void ClickView() {
        recyclerView = view.findViewById(R.id.recyclerView);

        initRecycler();
    }

    private ClassAdapter adapter;
    private List<Bean> list = new ArrayList();
    private void initRecycler() {
        LinearLayoutManager mgr = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mgr);
        if (adapter == null){
            adapter = new ClassAdapter(list);
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

    private ViewClick viewClick;
    public interface ViewClick {
        void onViewClick(View v);
    }
}
