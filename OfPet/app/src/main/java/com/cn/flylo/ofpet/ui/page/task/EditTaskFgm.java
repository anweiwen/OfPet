package com.cn.flylo.ofpet.ui.page.task;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.base.BaseBean;
import com.cn.flylo.ofpet.ui.dialog.SignUpSuccessDialog;
import com.cn.flylo.ofpet.url.api.UrlId;
import com.cn.ql.frame.utils.StringUtils;
import com.google.gson.JsonElement;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.OnClick;

public class EditTaskFgm extends BaseControllerFragment {

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etWeChat)
    EditText etWeChat;
    @BindView(R.id.etRemark)
    EditText etRemark;

    @Override
    public int layoutId() {
        return R.layout.fragment_edit_task;
    }

    private int id;

    @Override
    protected void InitData(Bundle data) {
        super.InitData(data);
        id = data.getInt("id");
    }

    @Override
    public void InitView() {
        setTitle("填写信息", "", true);
    }

    @OnClick({R.id.tvConfirm})
    public void ViewClick(View v) {
        switch (v.getId()) {
            case R.id.tvConfirm:
                saveTaskUserInfo();
                break;
        }
    }

    // todo

    @Override
    public void InitLoad() {
        super.InitLoad();
    }

    private void saveTaskUserInfo() {
        String name = getText(etName);
        String phone = getText(etPhone);
        String wechat = getText(etWeChat);
        String remark = getText(etRemark);
        if (StringUtils.isEmpty(name)){
            showToast("真实姓名不能为空");
            return;
        }
        if (!StringUtils.isChinaMobile(phone)){
            showToast("请检查输入的手机号码");
            return;
        }
        getHttpTool().getTask().saveTaskUserInfo(id, name, phone, wechat, remark);
    }

    @Override
    public void onNetSuccess(int urlId, @NotNull JsonElement jsonElement, @NotNull String value, @NotNull BaseBean baseBean, boolean success) {
        super.onNetSuccess(urlId, jsonElement, value, baseBean, success);
        switch (urlId){
            case UrlId
                    .saveTaskUserInfo:
                if (success){
                    showTip();
                }else{
                    showToast(baseBean.description);
                }
                break;
        }
    }

    private void showTip() {
        SignUpSuccessDialog signUpSuccessDialog = new SignUpSuccessDialog();
        signUpSuccessDialog.show(act);
        signUpSuccessDialog.setViewClick(new SignUpSuccessDialog.ViewClick() {
            @Override
            public void onViewClick(View v) {

            }

            @Override
            public void dismiss() {
                setActResult(new Intent());
            }
        });
    }
}
