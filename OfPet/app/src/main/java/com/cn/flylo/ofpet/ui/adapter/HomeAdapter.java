package com.cn.flylo.ofpet.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.bean.Video;
import com.cn.flylo.ofpet.url.Result;
import com.cn.flylo.ofpet.utils.Constants;
import com.cn.ql.frame.base.BaseRecyclerAdapter;
import com.cn.ql.frame.listener.itemclick.BaseItemViewOnClick;
import com.cn.ql.frame.tool.DensityTool;
import com.cn.ql.frame.tool.GlideImage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author axw_an
 * @create on 2019/5/31
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/31:
 */
public class HomeAdapter extends BaseRecyclerAdapter<Video, HomeAdapter.ViewHolder> {

    public HomeAdapter(@NotNull List list) {
        super(list);
    }

    @Override
    public int layoutId(int viewType) {
        return R.layout.item_home;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(getView(viewGroup, i));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Video item = getDatas().get(i);
        viewHolder.layout_item.setOnClickListener(new BaseItemViewOnClick(itemViewOnClickListener, item, i));

        GlideImage.INSTANCE.loadImage(context, Result.getImageOriginal(item.attachId), viewHolder.ivImage, R.drawable.place_holder);
        GlideImage.INSTANCE.loadImage(context, item.headUrl, viewHolder.ivHead, R.drawable.place_holder_head);

        Integer goodsNum = item.goodsNum;
        if (goodsNum == null) {
            goodsNum = 0;
        }
        viewHolder.tvLikeNumber.setText(goodsNum + "");

        Integer imgWidth = item.imgWidth;
        Integer imgHeight = item.imgHeight;
        if (imgWidth == null || imgHeight == null) {
            imgWidth = Constants.staWidth;
            imgHeight = DensityTool.Companion.dp2px(context, 260);
        }

        int height = (int) ((Constants.staWidth * 1.0f) / imgWidth * imgHeight);

        System.out.println("hello w:" + Constants.staWidth + ",h:" + height + ", imageW:" + imgWidth + ", imageH:" + imgHeight);


        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) viewHolder.ivImage.getLayoutParams();
        lp.height = height;
        viewHolder.ivImage.setLayoutParams(lp);

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout layout_item;
        ImageView ivImage, ivHead;
        TextView tvLikeNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivHead = itemView.findViewById(R.id.ivHead);
            tvLikeNumber = itemView.findViewById(R.id.tvLikeNumber);
        }
    }
}
