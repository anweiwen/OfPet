package com.cn.flylo.ofpet.ui.page.mine;

import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;

public class DetailFgm extends BaseControllerFragment {

    @Override
    public int layoutId() {
        return R.layout.fragment_detail;
    }

    @Override
    public void InitView() {
        setTitle("我的明细", "", true);
    }
}
