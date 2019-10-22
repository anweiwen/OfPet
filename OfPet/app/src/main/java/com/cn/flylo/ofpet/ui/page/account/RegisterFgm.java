package com.cn.flylo.ofpet.ui.page.account;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Account;
import com.cn.flylo.ofpet.bean.base.BaseBean;
import com.cn.flylo.ofpet.bean.base.DataBean;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.flylo.ofpet.url.api.UrlId;
import com.cn.ql.frame.mgr.AppManager;
import com.cn.ql.frame.tool.QAViewTool;
import com.cn.ql.frame.utils.StringUtils;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

public class RegisterFgm extends BaseControllerFragment {

    @BindView(R.id.tvGetCode)
    TextView tvGetCode;

    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etPasswordOnce)
    EditText etPasswordOnce;


    @Override
    public int layoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void InitView() {
        setTitle("注册", "", true);

    }

    @OnClick({R.id.btnSubmit, R.id.tvAgreement, R.id.tvGetCode, R.id.ivDelete,
    R.id.ivPassword, R.id.ivPaswordOnce})
    public void ViewClick(View view){
        switch (view.getId()){
            case R.id.ivDelete:
                etMobile.setText("");
                break;
            case R.id.btnSubmit:
                pResgist();
                break;
            case R.id.tvAgreement:
                StartTool.INSTANCE.start(act, PageEnum.Agreement);
                break;
            case R.id.tvGetCode:
                sendVcode();
                break;
            case R.id.ivPassword:
                boolean show = QAViewTool.changeEtShow(etPassword);
                view.setSelected(show);
                break;
            case R.id.ivPaswordOnce:
                boolean showOnce = QAViewTool.changeEtShow(etPasswordOnce);
                view.setSelected(showOnce);
                break;
        }
    }

    private CountDownTimer timer;
    private void startTimer(){
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                tvGetCode.setEnabled(false);
                tvGetCode.setSelected(true);
                tvGetCode.setText((l/1000)+"s");
            }

            @Override
            public void onFinish() {
                tvGetCode.setEnabled(true);
                tvGetCode.setSelected(false);
                tvGetCode.setText("获取验证码");
                stopTimer();

            }
        }.start();
    }

    private void stopTimer(){
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    // TODO

    @Override
    public void InitLoad() {
        super.InitLoad();
    }

    private void sendVcode(){
        String mobile = getText(etMobile);
        if (StringUtils.isEmpty(mobile)){
            showToast("请填入手机号");
            return;
        }
        if (!StringUtils.isChinaMobile(mobile)){
            showToast("请检查手机号码是否正确");
            return;
        }
        getHttpTool().getAccount().sendVcode(mobile, 1);
    }

    private void pResgist(){
        String mobile = getText(etMobile);
        String vercoed = getText(etCode);
        String password = getText(etPassword);
        String passwordOnce = getText(etPasswordOnce);
        if (StringUtils.isEmpty(mobile)){
            showToast("请检查输入的手机号码");
            return;
        }
        if (!StringUtils.isChinaMobile(mobile)){
            showToast("请检查输入的手机号码");
            return;
        }
        if (StringUtils.isEmpty(vercoed)){
            showToast("请输入验证码");
            return;
        }
        if (StringUtils.isEmpty(password)){
            showToast("验证码错误");
            return;
        }
        int len = password.length();
        if (len > 12 || len < 6){
            showToast("请输入6-12位数字与字母组合的密码");
            return;
        }
        if (StringUtils.isEmpty(password)){
            showToast("两次输入密码不一致");
            return;
        }
        if (!password.equals(passwordOnce)){
            showToast("两次输入密码不一致");
            return;
        }
        getHttpTool().getAccount().pResgist(mobile, vercoed, password);
    }

    @Override
    public void onNetSuccess(int urlId, @NotNull JsonElement jsonElement, @NotNull String value, @NotNull BaseBean baseBean, boolean success) {
        super.onNetSuccess(urlId, jsonElement, value, baseBean, success);
        switch (urlId){
            case UrlId.sendVcode:
                if (success){
                    startTimer();
                }else{
                    showToast(baseBean.description);
                }
                break;
            case UrlId.pResgist:
                showToast(baseBean.description);
                if (success){
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
        Account.setInstance(account);

        StartTool.INSTANCE.start(act, PageEnum.Main);
        AppManager.getAppManager().finishActivityFragment(LoginFgm.class);
        finish();
    }
}
