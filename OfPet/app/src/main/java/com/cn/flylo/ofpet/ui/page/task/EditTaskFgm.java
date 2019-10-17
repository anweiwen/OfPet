package com.cn.flylo.ofpet.ui.page.task;

import android.view.View;
import butterknife.OnClick;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;
import cn.addapp.pickers.listeners.OnLinkageListener;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.ui.dialog.SelectAddressPop;
import com.cn.flylo.ofpet.ui.dialog.SignUpSuccessDialog;

public class EditTaskFgm extends BaseControllerFragment {

    @Override
    public int layoutId() {
        return R.layout.fragment_edit_task;
    }

    @Override
    public void InitView() {
        setTitle("填写信息", "", true);
    }

    @OnClick({R.id.tvConfirm})
    public void ViewClick(View v){
        switch (v.getId()){
            case R.id.tvConfirm:
                SignUpSuccessDialog signUpSuccessDialog = new SignUpSuccessDialog();
                signUpSuccessDialog.show(act);
                break;
        }
    }
}
