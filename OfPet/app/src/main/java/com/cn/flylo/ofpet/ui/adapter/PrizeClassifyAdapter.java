package com.cn.flylo.ofpet.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.bean.PrizeClassify;
import com.cn.flylo.ofpet.url.Result;
import com.cn.ql.frame.base.BaseRecyclerAdapter;
import com.cn.ql.frame.listener.itemclick.BaseItemViewOnClick;
import com.cn.ql.frame.tool.GlideImage;
import com.cn.ql.frame.widget.SizeNiceImageView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author axw_an
 * @create on 2019/5/31
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/31:
 */
public class PrizeClassifyAdapter extends BaseRecyclerAdapter<PrizeClassify, PrizeClassifyAdapter.ViewHolder> {

    public PrizeClassifyAdapter(@NotNull List list) {
        super(list);
    }

    @Override
    public int layoutId(int viewType) {
        return R.layout.item_prize_classify;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(getView(viewGroup, i));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        PrizeClassify item = getDatas().get(i);
        viewHolder.layout_item.setOnClickListener(new BaseItemViewOnClick(itemViewOnClickListener, item, i));

        GlideImage.INSTANCE.loadImage(context, Result.getImageThumbnail(item.attachId), viewHolder.ivImage, R.drawable.place_holder);
        viewHolder.tvName.setText(getStr(item.title));
        viewHolder.tvSize.setText(item.version+"人已参与");
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.ivImage)
        SizeNiceImageView ivImage;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvSize)
        TextView tvSize;
        @BindView(R.id.layout_item)
        LinearLayout layout_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
