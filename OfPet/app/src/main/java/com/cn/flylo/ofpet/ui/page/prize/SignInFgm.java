package com.cn.flylo.ofpet.ui.page.prize;

import android.view.View;

import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Task;
import com.cn.flylo.ofpet.ui.adapter.SignInAdapter;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import com.cn.ql.frame.widget.NoScrollRecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;

/**
 * @author axw_an
 * @create on 2019/10/25
 * @refer url：
 * @description:
 * @update: axw_an:2019/10/25:
 */
public class SignInFgm extends BaseControllerFragment {

    @BindView(R.id.recyclerView)
    NoScrollRecyclerView recyclerView;

    @Override
    public int layoutId() {
        return R.layout.fragment_sign_in;
    }

    @Override
    public void InitView() {
        initRecycler();
    }

    private SignInAdapter adapter;
    private List<Task> list = new ArrayList();
    private void initRecycler() {
        GridLayoutManager mgr = new GridLayoutManager(act, 1);
        recyclerView.setLayoutManager(mgr);
        if (adapter == null){
            adapter = new SignInAdapter(list);
        }
        recyclerView.setAdapter(adapter);
        adapter.setItemViewOnClickListener(new ItemViewOnClickListener<Task>() {
            @Override
            public void onClick(@NotNull View v, Task data, int position) {
                if (data == null){
                    return;
                }
                switch (v.getId()){
                    case R.id.layout_item:
                        break;
                }
            }
        });

        for (int i=0;i < 10; i++){
            list.add(new Task());
        }
        adapter.notifyDataSetChanged();
    }
}
