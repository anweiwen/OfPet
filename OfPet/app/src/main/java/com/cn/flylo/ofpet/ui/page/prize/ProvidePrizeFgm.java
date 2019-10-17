package com.cn.flylo.ofpet.ui.page.prize;

import android.view.View;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.ui.dialog.SelectClassPop;

public class ProvidePrizeFgm extends BaseControllerFragment {
    @Override
    public int layoutId() {
        return R.layout.fragment_provide_prize;
    }

    @Override
    public void InitView() {
        setTitle("提供奖品", "", true);
    }

    @OnClick({R.id.tvConfirm, R.id.llClass})
    public void ViewClick(View v){
        switch (v.getId()){
            case R.id.tvConfirm:

                break;
            case R.id.llClass:
                SelectClassPop selectClassPop = new SelectClassPop();
                selectClassPop.show(act, view, new SelectClassPop.ViewClick() {
                    @Override
                    public void onViewClick(View v) {

                    }
                });
                break;
        }
    }
}
