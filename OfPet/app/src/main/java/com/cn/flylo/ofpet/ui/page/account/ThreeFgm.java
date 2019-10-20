package com.cn.flylo.ofpet.ui.page.account;

import android.view.View;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;

public class ThreeFgm extends BaseControllerFragment {

    @Override
    public int layoutId() {
        return R.layout.fragment_three;
    }

    @Override
    public void InitView() {
        setTitle("第三方授权登录", "", true);

    }

    @OnClick({R.id.btnSubmit})
    public void ViewClick(View view){
        switch (view.getId()){
            case R.id.btnSubmit:
                break;
        }
    }
}
