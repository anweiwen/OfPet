package com.cn.flylo.ofpet.ui.page.main;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;

import butterknife.BindView;
import com.cn.flylo.ofpet.bean.Account;
import com.cn.flylo.ofpet.tool.event.EventType;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.flylo.ofpet.ui.dialog.AgreementDialog;

public class MainFgm extends BaseControllerFragment {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.flHome)
    FrameLayout flHome;


    private FragmentManager fm;
    private View selectView;

    @Override
    public int layoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void InitView() {
        fm = getFragmentManager();
        initDrawer();
        ViewClick(flHome);

        showAgreement();

        // todo
        //Account.setInstance(null);
    }

    private void showAgreement(){
        AgreementDialog agreementDialog = new AgreementDialog();
        agreementDialog.show(act);
        agreementDialog.setViewClick(new AgreementDialog.ViewClick() {
            @Override
            public void onViewClick(View v) {
                switch (v.getId()){
                    case R.id.tvContent:
                        StartTool.INSTANCE.start(act, PageEnum.Agreement);
                        break;
                }
            }
        });
    }

    @OnClick({R.id.ivHead, R.id.llNews, R.id.llMessage, R.id.llSearch,
            R.id.llMyHome, R.id.llBuy, R.id.llEarnings, R.id.llDetail,
            R.id.llRecharge, R.id.llCertification, R.id.llSetting})
    public void MenuClick(View view) {
        closeMenu();
        switch (view.getId()){
            case R.id.ivHead:
                StartTool.INSTANCE.start(act, PageEnum.Interested); // 感兴趣的内容

                StartTool.INSTANCE.start(act, PageEnum.Login);
                break;
            case R.id.llNews:
                StartTool.INSTANCE.start(act, PageEnum.News);
                break;
            case R.id.llMessage:
                StartTool.INSTANCE.start(act, PageEnum.Message);
                break;
            case R.id.llSearch:
                StartTool.INSTANCE.start(act, PageEnum.Search);
                break;
            case R.id.llMyHome:
                StartTool.INSTANCE.start(act, PageEnum.MineInfo);
                break;
            case R.id.llBuy:
                StartTool.INSTANCE.start(act, PageEnum.Buy);
                break;
            case R.id.llEarnings:
                StartTool.INSTANCE.start(act, PageEnum.Earnings);
                break;
            case R.id.llDetail:
                StartTool.INSTANCE.start(act, PageEnum.Detail);
                break;
            case R.id.llRecharge:
                StartTool.INSTANCE.start(act, PageEnum.Recharge);
                break;
            case R.id.llCertification:
                StartTool.INSTANCE.start(act, PageEnum.Certification);
                break;
            case R.id.llSetting:
                StartTool.INSTANCE.start(act, PageEnum.Setting);
                break;
        }
    }

    @OnClick({R.id.flHome, R.id.flPrize, R.id.flTask,
            R.id.flIdel})
    public void ViewClick(View view) {
        FragmentTransaction ft = fm.beginTransaction();
        hideAllFgm();
        switch (view.getId()) {
            case R.id.flHome:
                if (child_one == null) {
                    child_one = new HomeFgm();
                    ft.add(R.id.frame_layout_tab, child_one);
                } else {
                    ft.show(child_one);
                }
                break;
            case R.id.flPrize:
                if (child_two == null) {
                    child_two = new PrizeFgm();
                    ft.add(R.id.frame_layout_tab, child_two);
                } else {
                    ft.show(child_two);
                }
                break;
            case R.id.flTask:
                if (child_three == null) {
                    child_three = new TaskFgm();
                    ft.add(R.id.frame_layout_tab, child_three);
                } else {
                    ft.show(child_three);
                }
                break;
            case R.id.flIdel:
                if (child_four == null) {
                    child_four = new IdleFgm();
                    ft.add(R.id.frame_layout_tab, child_four);
                } else {
                    ft.show(child_four);
                }
                break;
        }
        changeView(view);
        ft.commitAllowingStateLoss();
    }

    private void initDrawer() {

    }


    private HomeFgm child_one;
    private PrizeFgm child_two;
    private TaskFgm child_three;
    private IdleFgm child_four;

    private synchronized void hideAllFgm() {
        FragmentTransaction ft = fm.beginTransaction();
        if (child_one != null) {
            ft.hide(child_one);
        }
        if (child_two != null) {
            ft.hide(child_two);
        }
        if (child_three != null) {
            ft.hide(child_three);
        }
        if (child_four != null) {
            ft.hide(child_four);
        }
        ft.commitAllowingStateLoss();
    }

    private synchronized void changeView(View view) {
        view.setSelected(true);
        if (selectView != null) {
            if (view.getId() == selectView.getId()) {
                return;
            }
            selectView.setSelected(false);
        }
        selectView = view;
    }


    private void openMenu() {
        drawer_layout.openDrawer(Gravity.LEFT);
    }

    private void closeMenu() {
        drawer_layout.closeDrawer(Gravity.LEFT);
    }

    // todo


    @Override
    protected void EventMessage(int type, Bundle data) {
        super.EventMessage(type, data);
        if (type == EventType.OpenMenu.get()) {
            openMenu();
        }
    }

}
