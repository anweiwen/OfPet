package com.cn.flylo.ofpet.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.bean.Task;
import com.cn.flylo.ofpet.url.Result;
import com.cn.flylo.ofpet.utils.DateUtils;
import com.cn.ql.frame.base.BaseRecyclerAdapter;
import com.cn.ql.frame.listener.itemclick.BaseItemViewOnClick;
import com.cn.ql.frame.tool.GlideImage;
import com.shehuan.niv.NiceImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author axw_an
 * @create on 2019/5/31
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/31:
 */
public class TaskAdapter extends BaseRecyclerAdapter<Task, TaskAdapter.ViewHolder> {

    public TaskAdapter(@NotNull List list) {
        super(list);
    }

    @Override
    public int layoutId(int viewType) {
        return R.layout.item_task;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(getView(viewGroup, i));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Task item = getDatas().get(i);
        viewHolder.layout_item.setOnClickListener(new BaseItemViewOnClick(itemViewOnClickListener, item, i));

        GlideImage.INSTANCE.loadImage(context, Result.getImageOriginal(String.valueOf(item.attachId)), viewHolder.ivImage, R.drawable.place_holder);
        viewHolder.tvName.setText(getStr(item.title));
        viewHolder.tvTime.setText("时间："+ DateUtils.getDate(item.startDate) +"-"+DateUtils.getDate(item.endDate));
        viewHolder.tvSize.setText("已参加人数："+item.attendCount);

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

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivImage)
        NiceImageView ivImage;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvSize)
        TextView tvSize;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.tvLook)
        TextView tvLook;
        @BindView(R.id.layout_item)
        LinearLayout layout_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
