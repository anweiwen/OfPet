package com.cn.flylo.ofpet.ui.page.account;

import android.view.View;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;

public class LoginFgm extends BaseControllerFragment {

    @Override
    public int layoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void InitView() {
        setTitle("", "", true);
    }

    @OnClick({R.id.btnSubmit, R.id.tvRegister, R.id.tvForget, R.id.tvAgreement,
    R.id.ivWx, R.id.ivQQ})
    public void ViewClick(View view){
        switch (view.getId()){
            case R.id.btnSubmit:
                StartTool.INSTANCE.start(act, PageEnum.Main);
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
        }
    }
}
