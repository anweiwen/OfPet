package com.cn.flylo.ofpet.ui.page.look;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Comment;
import com.cn.flylo.ofpet.bean.base.BaseBean;
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

    @Override
    public int layoutId() {
        return R.layout.fragment_comment;
    }

    private int id;
    private int commentNum;

    @Override
    protected void InitData(Bundle data) {
        super.InitData(data);
        id = data.getInt("id");
        commentNum = data.getInt("commentNum");
    }

    @Override
    public void InitView() {
        initRecycler();
        showInitData();
        InitRefreshLayout();
    }

    private void showInitData() {
        tvCommentNumber.setText(commentNum + "条评论");

        initEt();
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
                if (data == null) {
                    return;
                }
                switch (v.getId()) {
                    case R.id.layout_item:
                        break;
                    case R.id.tvContent:
                        discussId = data.discussId;
                        showKeyboard(etContent);
                        break;
                    case R.id.llZan:
                        saveInfoDispra(data.discussId, 1);
                        break;
                }
            }
        });

        adapter.setListener(new CommentAdapter.ViewClickListener() {
            @Override
            public void onChildViewClickListener(View view, Comment data, int position) {
                if (data == null) {
                    return;
                }
                switch (view.getId()) {
                    case R.id.layout_item:
                        break;
                    case R.id.tvContent:
                        discussId = data.discussId;
                        showKeyboard(etContent);
                        break;
                    case R.id.llZan:
                        saveInfoDispra(data.discussId, 1);
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


    private void saveInfoDispra(int discussId, int status) {
        getHttpTool().getVideo().saveInfoDispra(discussId, status);
    }

    private void saveVideoDiscuss(String content) {
        getHttpTool().getVideo().saveVideoDiscuss(id, content);
    }

    private int discussId;

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
            case UrlId.saveInfoDispra:
                showToast(baseBean.description);
                if (success) {
                    refresh();
                }
                break;
            case UrlId.saveVideoDiscuss:
                showToast(baseBean.description);
                if (success) {
                    refresh();
                }
                break;
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
