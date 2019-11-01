package com.cn.flylo.ofpet.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.bean.Prize;
import com.cn.flylo.ofpet.url.Result;
import com.cn.flylo.ofpet.utils.DateUtils;
import com.cn.ql.frame.base.BaseRecyclerAdapter;
import com.cn.ql.frame.listener.itemclick.BaseItemViewOnClick;
import com.cn.ql.frame.tool.GlideImage;
import com.shehuan.niv.NiceImageView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author axw_an
 * @create on 2019/5/31
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/31:
 */
public class PrizeListAdapter extends BaseRecyclerAdapter<Prize, PrizeListAdapter.ViewHolder> {

    public PrizeListAdapter(@NotNull List list) {
        super(list);
    }

    @Override
    public int layoutId(int viewType) {
        return R.layout.item_prize_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(getView(viewGroup, i));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Prize item = getDatas().get(i);
        viewHolder.layout_item.setOnClickListener(new BaseItemViewOnClick(itemViewOnClickListener, item, i));

        GlideImage.INSTANCE.loadImage(context, Result.getImageOriginal(item.attachId), viewHolder.ivImage, R.drawable.place_holder);
        viewHolder.tvTime.setText(DateUtils.getDate(item.startTime)+"~"+DateUtils.getDate(item.endTime));
        viewHolder.tvSize.setText("已参加人数："+item.attendCount+"人");

        String doingText = "未开始";
        int doing = item.doing; // 0：活动为未开始； 1：活动进行中 ； 2：活动已结束
        switch (doing){
            case 0:
                doingText = "未开始";
                break;
            case 1:
                doingText = "进行中";
                break;
            case 2:
                doingText = "已结束";
                break;
        }
        viewHolder.tvStatus.setText(doingText);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout_item;

        NiceImageView ivImage;
        TextView tvTime;
        TextView tvSize;
        TextView tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvTime =itemView.findViewById(R.id.tvTime);
            tvSize = itemView.findViewById(R.id.tvSize);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
