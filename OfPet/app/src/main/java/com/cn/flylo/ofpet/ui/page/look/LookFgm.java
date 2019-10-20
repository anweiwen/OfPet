package com.cn.flylo.ofpet.ui.page.look;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.flylo.ofpet.ui.dialog.CommentPop;
import com.cn.ql.frame.tool.StartActTool;

public class LookFgm extends BaseControllerFragment {
    @Override
    public int layoutId() {
        return R.layout.fragment_look;
    }

    @Override
    public void InitView() {
        act.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @OnClick({R.id.llComment})
    public void ViewClick(View view){
        switch (view.getId()){
            case R.id.llComment:
                Bundle bundle = new Bundle();
                StartActTool.INSTANCE.Start(act, CommentAct.class, bundle, 0x01);
                break;
        }
    }
}
