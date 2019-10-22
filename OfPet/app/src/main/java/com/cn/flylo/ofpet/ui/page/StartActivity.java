package com.cn.flylo.ofpet.ui.page;

import android.os.CountDownTimer;

import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerActivity;
import com.cn.flylo.ofpet.bean.Account;
import com.cn.flylo.ofpet.tool.SaveTool;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.ql.frame.tool.SystemTool;
import com.cn.ql.frame.tool.gson.GsonTool;
import com.cn.ql.frame.utils.StringUtils;

/**
 * @author axw_an
 * @create on 2019/5/28
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/18:
 */
public class StartActivity extends BaseControllerActivity {

    private SaveTool saveTool;

    @Override
    public int layoutId() {
        return R.layout.activity_start;
    }

    @Override
    public void InitView() {
        saveTool = new SaveTool(this);
        initTimer();
    }

    private void initTimer() {
        CountDownTimer timer = new CountDownTimer(1 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                toOther();
            }
        }.start();
    }

    private void toOther() {
        int version = saveTool.getVersion();
        int thisVersion = SystemTool.INSTANCE.getLocalVersion(this);
//        if (thisVersion > version){
//            StartTool.INSTANCE.start(StartActivity.this, PageEnum.Guide);
//        }else {
        String userValue = saveTool.getUser();
        if (StringUtils.isEmpty(userValue)) {
        } else {
            Account account = GsonTool.INSTANCE.getBean(userValue, Account.class);
            if (account != null) {
                Account.setInstance(account);
            }
        }
//        }

        StartTool.INSTANCE.start(StartActivity.this, PageEnum.Main);

        finish();
    }
}
