package com.cn.flylo.ofpet.ui.page.mine;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.ui.adapter.LabelAdapter;
import com.cn.flylo.ofpet.ui.adapter.RechargeAdapter;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.flylo.ofpet.ui.dialog.SelectPayPop;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RechargeFgm extends BaseControllerFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int layoutId() {
        return R.layout.fragment_recharge;
    }

    @Override
    public void InitView() {
        setTitle("账号充值", "", true);

        initRecycler();
    }

    @OnClick({R.id.llRecord})
    public void ViewClick(View v){
        switch (v.getId()){
            case R.id.llRecord:
                StartTool.INSTANCE.start(act, PageEnum.RechargeRecord);
                break;
        }
    }

    private RechargeAdapter adapter;
    private List<Bean> list = new ArrayList();
    private void initRecycler() {
        ChipsLayoutManager mgr = ChipsLayoutManager.newBuilder(act)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();
        recyclerView.setLayoutManager(mgr);
        if (adapter == null){
            adapter = new RechargeAdapter(list);
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
                        SelectPayPop selectPayPop = new SelectPayPop();
                        selectPayPop.show(act, view, new SelectPayPop.ViewClick(){

                            @Override
                            public void onViewClick(View v) {

                            }
                        });
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