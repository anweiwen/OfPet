package com.cn.flylo.ofpet.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.ql.frame.base.BaseRecyclerAdapter;
import com.cn.ql.frame.listener.itemclick.BaseItemViewOnClick;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author axw_an
 * @create on 2019/5/31
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/31:
 */
public class VideoAdapter extends BaseRecyclerAdapter<Bean, VideoAdapter.ViewHolder> {

    public VideoAdapter(@NotNull List list) {
        super(list);
    }

    @Override
    public int layoutId(int viewType) {
        return R.layout.item_video;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(getView(viewGroup, i));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Bean item = getDatas().get(i);
        viewHolder.layout_item.setOnClickListener(new BaseItemViewOnClick(itemViewOnClickListener, item, i));

        int index = i + 1;
        if (index % 3 == 0){
            viewHolder.viewLine.setVisibility(View.GONE);
        }else{
            viewHolder.viewLine.setVisibility(View.VISIBLE);
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        FrameLayout layout_item;
        View viewLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
            viewLine = itemView.findViewById(R.id.viewLine);
        }
    }
}
