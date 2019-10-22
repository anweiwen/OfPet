package com.cn.flylo.ofpet.ui.page.account;

import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Account;
import com.cn.flylo.ofpet.bean.base.BaseBean;
import com.cn.flylo.ofpet.bean.base.DataBean;
import com.cn.flylo.ofpet.tool.SaveTool;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.flylo.ofpet.url.api.UrlId;
import com.cn.ql.frame.mgr.AppManager;
import com.cn.ql.frame.utils.StringUtils;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

public class LoginFgm extends BaseControllerFragment {

    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etPassword)
    EditText etPassword;

    private SaveTool saveTool;

    @Override
    public int layoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void InitView() {
        setTitle("", "", true);
        saveTool = new SaveTool(act);
        initText();
    }

    private void initText() {
        etMobile.setText("13480221756");
        etPassword.setText("123456");
    }

    @OnClick({R.id.btnSubmit, R.id.tvRegister, R.id.tvForget, R.id.tvAgreement,
            R.id.ivWx, R.id.ivQQ, R.id.ivDeleteMobile, R.id.ivDeletePassword})
    public void ViewClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                pLogin();
                break;
            case R.id.tvRegister:
                StartTool.INSTANCE.start(act, PageEnum.Register);
                break;
            case R.id.tvForget:
                StartTool.INSTANCE.start(act, PageEnum.Forget);
                break;
            case R.id.tvAgreement:
                StartTool.INSTANCE.start(act, PageEnum.Agreement);
                break;
            case R.id.ivWx:
                StartTool.INSTANCE.start(act, PageEnum.Three);
                break;
            case R.id.ivQQ:
                StartTool.INSTANCE.start(act, PageEnum.Three);
                break;
            case R.id.ivDeleteMobile:
                etMobile.setText("");
                break;
            case R.id.ivDeletePassword:
                etPassword.setText("");
                break;
        }
    }

    // todo

    @Override
    public void InitLoad() {
        super.InitLoad();
    }

    private void pLogin() {
        String mobile = getText(etMobile);
        String password = getText(etPassword);

        if (StringUtils.isEmpty(mobile)) {
            showToast("请检查输入的手机号码");
            return;
        }
        if (!StringUtils.isChinaMobile(mobile)) {
            showToast("请检查输入的手机号码");
            return;
        }
        if (StringUtils.isEmpty(password)) {
            showToast("请输入密码");
            return;
        }
        getHttpTool().getAccount().pLogin(mobile, password);
    }

    @Override
    public void onNetSuccess(int urlId, @NotNull JsonElement jsonElement, @NotNull String value, @NotNull BaseBean baseBean, boolean success) {
        super.onNetSuccess(urlId, jsonElement, value, baseBean, success);
        switch (urlId) {
            case UrlId.pLogin:
                showToast(baseBean.description);
                if (success) {
                    setAccount(value);
                }
                break;
        }
    }

    private void setAccount(String value){
        DataBean<Account> bean = getBean(value, DataBean.class, Account.class);
        if (bean == null){
            return;
        }
        Account account = bean.result;
        if (account == null){
            return;
        }
        saveTool.putUser(toJson(account));

        Account.setInstance(account);
        finish();
    }
}
