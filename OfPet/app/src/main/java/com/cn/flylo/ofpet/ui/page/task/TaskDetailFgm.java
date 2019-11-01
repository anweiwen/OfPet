package com.cn.flylo.ofpet.ui.page.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.OfferUser;
import com.cn.flylo.ofpet.bean.Task;
import com.cn.flylo.ofpet.bean.base.BaseBean;
import com.cn.flylo.ofpet.bean.base.DataBean;
import com.cn.flylo.ofpet.ui.JumpCode;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.flylo.ofpet.url.Result;
import com.cn.flylo.ofpet.url.api.UrlId;
import com.cn.flylo.ofpet.utils.DateUtils;
import com.cn.ql.frame.tool.GlideImage;
import com.cn.ql.frame.utils.WebViewUtils;
import com.google.gson.JsonElement;
import com.shehuan.niv.NiceImageView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.OnClick;

public class TaskDetailFgm extends BaseControllerFragment {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.ivBg)
    ImageView ivBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvSize)
    TextView tvSize;
    @BindView(R.id.ivHead)
    NiceImageView ivHead;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;


    @Override
    public int layoutId() {
        return R.layout.fragment_task_detail;
    }

    private int id;
    @Override
    protected void InitData(Bundle data) {
        super.InitData(data);
        id = data.getInt("id");
    }

    @Override
    public void InitView() {
        setTitle("任务详情", R.mipmap.share_gray, true);
    }

    @OnClick({R.id.tvConfirm})
    public void ViewClick(View v) {
        switch (v.getId()) {
            case R.id.tvConfirm:
                if (task == null){
                    return;
                }
                Integer join = task.join;
                if (join != 1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    StartTool.INSTANCE.start(act, PageEnum.EditTask, bundle, JumpCode.refresh);
                }
                break;
        }
    }

    @Override
    public void onActResult(int requestCode, int resultCode, Intent data) {
        super.onActResult(requestCode, resultCode, data);
        switch (requestCode){
            case JumpCode.refresh:
                InitLoad();
                break;
        }
    }

    // todo
    @Override
    public void InitLoad() {
        super.InitLoad();
        getTaskInfo();
    }

    private void getTaskInfo() {
        getHttpTool().getTask().getTaskInfo(id);
    }

    @Override
    public void onNetSuccess(int urlId, @NotNull JsonElement jsonElement, @NotNull String value, @NotNull BaseBean baseBean, boolean success) {
        super.onNetSuccess(urlId, jsonElement, value, baseBean, success);
        switch (urlId) {
            case UrlId.getTaskInfo:
                if (success) {
                    showInfo(value);
                } else {
                    showToast(baseBean.description);
                }
                break;
        }
    }

    private Task task;
    private void showInfo(String value) {
        DataBean<Task> bean = getBean(value, DataBean.class, Task.class);
        if (bean != null) {
            task = bean.result;
            if (task != null) {
                GlideImage.INSTANCE.loadImage(act, Result.getImageOriginal(String.valueOf(task.attachId)), ivBg, R.drawable.place_holder);

                tvTitle.setText(getStr(task.title));
                tvTime.setText("时间："+ DateUtils.getDate(task.startDate) +"-"+DateUtils.getDate(task.endDate));
                tvSize.setText("已参加人数："+task.attendCount);

                OfferUser offerUser = task.user;
                if (offerUser != null){
                    GlideImage.INSTANCE.loadImage(act, offerUser.headUrl, ivHead, R.drawable.place_holder_head);
                    tvName.setText(getStr(offerUser.nickName));
                    tvDesc.setText(getStr(offerUser.styleSign));
                }

                WebViewUtils.loadHtml(webView, task.content);

                Integer join = task.join; //
                if (join == null){
                    join = 0;
                }
                tvConfirm.setSelected(join == 1);
                if (join == 1){
                    tvConfirm.setText("已参加");
                }else{
                    tvConfirm.setText("报名参加");
                }
            }
        }
    }


}
