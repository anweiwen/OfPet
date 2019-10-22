package com.cn.flylo.ofpet.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.bean.Comment;
import com.cn.ql.frame.base.BaseRecyclerAdapter;
import com.cn.ql.frame.listener.itemclick.BaseItemViewOnClick;
import com.cn.ql.frame.tool.GlideImage;
import com.cn.ql.frame.widget.NoScrollRecyclerView;
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
public class CommentChildAdapter extends BaseRecyclerAdapter<Comment, CommentChildAdapter.ViewHolder> {

    public CommentChildAdapter(@NotNull List list) {
        super(list);
    }

    @Override
    public int layoutId(int viewType) {
        return R.layout.item_comment_child;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(getView(viewGroup, i));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Comment item = getDatas().get(i);
        viewHolder.layout_item.setOnClickListener(new BaseItemViewOnClick(itemViewOnClickListener, item, i));
        viewHolder.tvContent.setOnClickListener(new BaseItemViewOnClick(itemViewOnClickListener, item, i));

        GlideImage.INSTANCE.loadImage(context, item.headUrl, viewHolder.ivHead, R.drawable.place_holder_head);
        viewHolder.tvName.setText(getStr(item.nickName));
        viewHolder.tvContent.setText(getStr(item.context));
        viewHolder.tvZan.setText(String.valueOf(item.praiseCount));
    }


    static
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_item)
        LinearLayout layout_item;
        @BindView(R.id.ivHead)
        NiceImageView ivHead;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.ivZan)
        ImageView ivZan;
        @BindView(R.id.tvZan)
        TextView tvZan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
