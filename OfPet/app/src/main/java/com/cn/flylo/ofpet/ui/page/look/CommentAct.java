package com.cn.flylo.ofpet.ui.page.look;

import android.view.View;
import android.view.WindowManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerActivity;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.ui.adapter.CommentAdapter;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import com.cn.ql.frame.sytem.AndroidBug5497Workaround;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommentAct extends BaseControllerActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int layoutId() {
        return R.layout.activity_comment;
    }

    @Override
    public void InitView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        AndroidBug5497Workaround.assistActivity(this);

        initRecycler();
    }

    private CommentAdapter adapter;
    private List<Bean> list = new ArrayList();
    private void initRecycler(){
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (adapter == null){
            adapter = new CommentAdapter(list);
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
