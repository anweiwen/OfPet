package com.cn.flylo.ofpet.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
public class PrizeFreeAdapter extends BaseRecyclerAdapter<Bean, PrizeFreeAdapter.ViewHolder> {

    public PrizeFreeAdapter(@NotNull List list) {
        super(list);
    }

    @Override
    public int layoutId(int viewType) {
        return R.layout.item_prize_free;
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

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layout_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
        }
    }
}
