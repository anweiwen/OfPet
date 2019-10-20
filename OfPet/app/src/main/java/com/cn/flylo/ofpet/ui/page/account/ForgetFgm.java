package com.cn.flylo.ofpet.ui.page.account;

import android.view.View;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;

public class ForgetFgm extends BaseControllerFragment {

    @Override
    public int layoutId() {
        return R.layout.fragment_forget;
    }

    @Override
    public void InitView() {
        setTitle("忘记密码", "", true);

    }

    @OnClick({R.id.btnSubmit, R.id.tvAgreement})
    public void ViewClick(View view){
        switch (view.getId()){
            case R.id.btnSubmit:
                break;
            case R.id.tvAgreement:
                StartTool.INSTANCE.start(act, PageEnum.Agreement);
                break;
        }
    }
}
