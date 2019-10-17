package com.cn.flylo.ofpet.ui.dialog;

import android.app.Activity;
import android.graphics.Color;
import cn.addapp.pickers.entity.Province;
import cn.addapp.pickers.listeners.OnLinkageListener;
import cn.addapp.pickers.picker.AddressPicker;
import cn.addapp.pickers.util.ConvertUtils;
import com.cn.ql.frame.tool.gson.GsonTool;

import java.util.ArrayList;

/**
 * @author axw_an
 * @create on 2019/7/13
 * @refer url：
 * @description:
 * @update: axw_an:2019/7/13:
 */
public class SelectAddressPop {

    public void show(Activity activity, OnLinkageListener listener){
        try {
            ArrayList<Province> data = new ArrayList<>();
            String json = ConvertUtils.toString(activity.getAssets().open("city.json"));
            data.addAll(GsonTool.INSTANCE.<Province>getList(json, Province.class));
            AddressPicker picker = new AddressPicker(activity, data);
            picker.setCanLoop(true);
            picker.setWheelModeEnable(false);

            picker.setUnSelectedTextColor(Color.parseColor("#7F7F7F"));
            picker.setSelectedTextColor(Color.parseColor("#FF8000"));
            picker.setCancelText("取消");
            picker.setSubmitText("完成");
            picker.setCancelTextColor(Color.parseColor("#AEAEAE"));
            picker.setSubmitTextColor(Color.parseColor("#FF8000"));

            picker.setSelectedItem("广东省", "广州市", "白云区");
            picker.setOnLinkageListener(listener);
            picker.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
