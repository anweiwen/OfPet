package com.cn.flylo.ofpet.ui.page.prize;

import android.view.View;
import butterknife.OnClick;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;
import cn.addapp.pickers.listeners.OnLinkageListener;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.ui.dialog.SelectAddressPop;

public class EditAwardFgm extends BaseControllerFragment {
    @Override
    public int layoutId() {
        return R.layout.fragment_edit_award;
    }

    @Override
    public void InitView() {
        setTitle("填写信息", "", true);
    }

    @OnClick({R.id.llAddress})
    public void ViewClick(View v){
        switch (v.getId()){
            case R.id.llAddress:
                SelectAddressPop selectAddressPop = new SelectAddressPop();
                selectAddressPop.show(act, new OnLinkageListener() {
                    @Override
                    public void onAddressPicked(Province province, City city, County county) {

                    }
                });
                break;
        }
    }
}
