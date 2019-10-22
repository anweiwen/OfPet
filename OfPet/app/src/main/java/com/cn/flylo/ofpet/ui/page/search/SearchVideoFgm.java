package com.cn.flylo.ofpet.ui.page.search;

import android.view.View;
import android.widget.EditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.bean.Video;
import com.cn.flylo.ofpet.ui.adapter.HomeAdapter;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchVideoFgm extends BaseControllerFragment {

    @BindView(R.id.etText)
    EditText etText;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int layoutId() {
        return R.layout.fragment_search_video;
    }

    @Override
    public void InitView() {
        initRecycler();
    }

    @OnClick({R.id.tvCancel, R.id.ivDelete})
    public void ViewClick(View v){
        hideKeyboard();
        switch (v.getId()){
            case R.id.tvCancel:
                finish();
                break;
            case R.id.ivDelete:
                etText.setText("");
                break;
        }
    }

    private HomeAdapter adapter;
    private List<Video> list = new ArrayList();
    private void initRecycler(){
        GridLayoutManager linearLayoutManager = new GridLayoutManager(act, 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (adapter == null){
            adapter = new HomeAdapter(list);
        }
        recyclerView.setAdapter(adapter);
        adapter.setItemViewOnClickListener(new ItemViewOnClickListener<Video>() {
            @Override
            public void onClick(@NotNull View v, Video data, int position) {
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
            list.add(new Video());
        }
        adapter.notifyDataSetChanged();
    }
}
