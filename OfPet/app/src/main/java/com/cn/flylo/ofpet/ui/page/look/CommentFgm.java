package com.cn.flylo.ofpet.ui.page.look;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Comment;
import com.cn.flylo.ofpet.bean.base.BaseBean;
import com.cn.flylo.ofpet.bean.base.DataBean;
import com.cn.flylo.ofpet.bean.base.DataListBean;
import com.cn.flylo.ofpet.ui.adapter.CommentAdapter;
import com.cn.flylo.ofpet.url.api.UrlId;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import com.cn.ql.frame.utils.StringUtils;
import com.google.gson.JsonElement;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommentFgm extends BaseControllerFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvCommentNumber)
    TextView tvCommentNumber;
    @BindView(R.id.etContent)
    EditText etContent;

    @BindView(R.id.llComment)
    LinearLayout llComment;

    @Override
    public int layoutId() {
        return R.layout.fragment_comment;
    }

    private int type;
    private int id;
    private int commentNum;

    @Override
    protected void InitData(Bundle data) {
        super.InitData(data);
        type = data.getInt("type");
        id = data.getInt("id");
        commentNum = data.getInt("commentNum");
    }

    @Override
    public void InitView() {
        discussId = -1;
        initRecycler();
        showInitData();
        InitRefreshLayout();

        if (type == 1){
            llComment.setVisibility(View.INVISIBLE);
        }
        initEt();
    }

    private void showInitData() {
        tvCommentNumber.setText(commentNum + "条评论");

    }

    private void initEt() {
        etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND) {
                    String content = getText(etContent);
                    if (StringUtils.isEmpty(content)) {
                        showToast("评论内容不能为空");
                        return true;
                    }
                    if (discussId != -1) { // 评论的子评论
                        saveChildDis(content);
                    } else {
                        saveVideoDiscuss(content);
                    }
                    etContent.setText("");
                    etContent.setHint("说点什么！！！");
                    discussId = -1;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.view_cancel, R.id.ivCancel})
    public void ViewClick(View view) {
        switch (view.getId()) {
            case R.id.view_cancel:
            case R.id.ivCancel:
                finish();
                break;
        }
    }

    private CommentAdapter adapter;
    private List<Comment> list = new ArrayList();

    private Comment childComment;
    private Comment zanComment;
    private int selectPosition = -1;

    private void initRecycler() {
        GridLayoutManager linearLayoutManager = new GridLayoutManager(act, 1);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (adapter == null) {
            adapter = new CommentAdapter(list);
        }
        recyclerView.setAdapter(adapter);
        adapter.setItemViewOnClickListener(new ItemViewOnClickListener<Comment>() {
            @Override
            public void onClick(@NotNull View v, Comment data, int position) {
                selectPosition = position;
                if (data == null) {
                    return;
                }
                switch (v.getId()) {
                    case R.id.layout_item:
                        break;
                    case R.id.tvContent:
                        childComment = data;
                        discussId = data.discussId;
                        etContent.setHint("回复@"+data.nickName);
                        showKeyboard(etContent);
                        break;
                    case R.id.llZan:
                        zanComment = data;
                        int isTrue = data.isTrue;
                        saveInfoDispra(data.discussId, isTrue==1?0:1);
                        break;
                }
            }
        });

        adapter.setListener(new CommentAdapter.ViewClickListener() {
            @Override
            public void onChildViewClickListener(View view, Comment data, int parentPosition, int position) {
                selectPosition = parentPosition;
                if (data == null) {
                    return;
                }
                switch (view.getId()) {
                    case R.id.layout_item:
                        break;
                    case R.id.tvContent:
                        childComment = list.get(parentPosition);
                        selectPosition = parentPosition;

                        discussId = data.discussId;
                        etContent.setHint("回复@"+data.nickName);
                        showKeyboard(etContent);
                        break;
                    case R.id.llZan:
                        zanComment = data;
                        int isTrue = data.isTrue;
                        saveChildDispra(data.childId, isTrue==1?0:1);
                        break;
                }
            }
        });
    }

    // refresh

    @Override
    public void refresh(@Nullable SmartRefreshLayout refreshlayout) {
        super.refresh(refreshlayout);
        refresh();
    }

    @Override
    public void load(@Nullable SmartRefreshLayout refreshlayout) {
        super.load(refreshlayout);
        load();
    }

    // todo
    @Override
    public void InitLoad() {
        super.InitLoad();
        refresh();
    }

    private void refresh() {
        page = 1;
        getVideoDisList();
    }

    private void load() {
        page++;
        getVideoDisList();
    }

    private int page = 1;

    private void getVideoDisList() {
        getHttpTool().getVideo().getVideoDisList(id, page);
    }

    private int thisStatus; // 0 未点赞 1 已点赞
    private void saveInfoDispra(int discussId, int status) {
        thisStatus = status;
        getHttpTool().getVideo().saveInfoDispra(discussId, status);
    }

    private void saveChildDispra(int childId, int status) {
        thisStatus = status;
        getHttpTool().getVideo().saveChildDispra(childId, status);
    }

    private void saveVideoDiscuss(String content) {
        getHttpTool().getVideo().saveVideoDiscuss(id, content);
    }

    private int discussId = -1;

    private void saveChildDis(String content) {
        getHttpTool().getVideo().saveChildDis(id, discussId, content);
    }

    @Override
    public void onNetSuccess(int urlId, @NotNull JsonElement jsonElement, @NotNull String value, @NotNull BaseBean baseBean, boolean success) {
        super.onNetSuccess(urlId, jsonElement, value, baseBean, success);
        switch (urlId) {
            case UrlId.getVideoDisList:
                if (success) {
                    showComment(value);
                } else {
                    showToast(baseBean.description);
                }
                break;
            case UrlId.saveInfoDispra: // 评论点赞
            case UrlId.saveChildDispra:
                if (success) {
                    if (thisStatus == 1){
                        zanComment.isTrue = 1;
                        zanComment.praiseCount ++;
                    }else{
                        zanComment.isTrue = 0;
                        zanComment.praiseCount --;
                    }
                    adapter.notifyItemChanged(selectPosition);
                }else{
                    showToast(baseBean.description);
                }
                break;
            case UrlId.saveVideoDiscuss: // 保存视频评论
                showToast(baseBean.description);
                if (success) {
                    showVideoDiscuss(value, 0);
                }
                break;
            case UrlId.saveChildDis: // 保存子评论
                showToast(baseBean.description);
                if (success) {
                    showVideoDiscuss(value, 1);
                }
                break;
        }
    }

    // 保存视频评论 0 父评论 1 子评论
    private void showVideoDiscuss(String value, int type) {
        DataBean<Comment> bean = getBean(value, DataBean.class, Comment.class);
        if (bean != null){
            Comment comment = bean.result;
            if (comment != null){
                if (type == 0) {
                    list.add(0, comment);
                    adapter.notifyItemInserted(0);

                }else{
                    if (childComment != null) {
                        List<Comment> commentList = childComment.list;
                        if (commentList == null){
                            commentList = new ArrayList<>();
                            childComment.list = commentList;
                        }
                        commentList.add(comment);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
        commentNum++;
        showInitData();

        if (selectPosition != -1) {
            recyclerView.smoothScrollToPosition(selectPosition);
        }
    }

    private void showComment(String value) {
        DataListBean<Comment> bean = getBean(value, DataListBean.class, Comment.class);
        if (page == 1) {
            list.clear();
        }
        if (bean != null) {
            if (bean.result != null) {
                List<Comment> listTmp = bean.result.content;
                if (listTmp != null) {
                    list.addAll(listTmp);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
