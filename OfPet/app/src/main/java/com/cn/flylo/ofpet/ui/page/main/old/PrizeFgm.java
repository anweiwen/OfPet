package com.cn.flylo.ofpet.ui.page.main.old;

import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.tool.event.EventTool;
import com.cn.flylo.ofpet.tool.event.EventType;
import com.cn.flylo.ofpet.ui.adapter.PrizeAdapter;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.flylo.ofpet.ui.dialog.PrizeRuleDialog;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PrizeFgm extends BaseControllerFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int layoutId() {
        return R.layout.fragment_prize;
    }

    @Override
    public void InitView() {
        initRecycler();
    }


    @OnClick({R.id.image_top_menu, R.id.tvTopRight, R.id.tvRule})
    public void ViewClick(View view){
        switch (view.getId()){
            case R.id.image_top_menu:
                EventTool.getInstance().send(EventType.OpenMenu);
                break;
            case R.id.tvTopRight:
                StartTool.INSTANCE.start(act, PageEnum.ProvidePrize);
                break;
            case R.id.tvRule:
                PrizeRuleDialog prizeRuleDialog = new PrizeRuleDialog();
                prizeRuleDialog.show(act);
                break;
        }
    }

    private PrizeAdapter adapter;
    private List<Bean> list = new ArrayList();
    private void initRecycler() {
        GridLayoutManager mgr = new GridLayoutManager(act, 2);
        recyclerView.setLayoutManager(mgr);
        if (adapter == null){
            adapter = new PrizeAdapter(list);
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
                        StartTool.INSTANCE.start(act, PageEnum.PrizeList);
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
