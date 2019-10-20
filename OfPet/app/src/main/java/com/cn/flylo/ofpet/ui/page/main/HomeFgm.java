package com.cn.flylo.ofpet.ui.page.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.tool.event.EventTool;
import com.cn.flylo.ofpet.tool.event.EventType;
import com.cn.flylo.ofpet.ui.page.home.AttentionFgm;
import com.cn.flylo.ofpet.ui.page.home.HomeChildFgm;
import com.google.android.material.tabs.TabLayout;
import com.tencent.liteav.demo.videorecord.TCVideoSettingActivity;

public class HomeFgm extends BaseControllerFragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public int layoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void InitView() {
        initTab();
    }

    @OnClick({R.id.image_top_menu, R.id.image_top_take})
    public void ViewClick(View view){
        switch (view.getId()){
            case R.id.image_top_menu:
                EventTool.getInstance().send(EventType.OpenMenu);
                break;
            case R.id.image_top_take:
                Intent intent = new Intent(act, TCVideoSettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    private int index;
    private void initTab() {
        addTabTitle();
        viewPager.setAdapter(new MyAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                index = i;

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        addTabTitle();
    }

    private void addTabTitle() {
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setTag(0).setText("关注"));
        tabLayout.addTab(tabLayout.newTab().setTag(1).setText("热门"));
        tabLayout.addTab(tabLayout.newTab().setTag(2).setText("同城"));
        tabLayout.addTab(tabLayout.newTab().setTag(3).setText("直播"));
    }

    private AttentionFgm attention;
    private HomeChildFgm child_two;
    private HomeChildFgm child_three;
    private HomeChildFgm child_four;

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            BaseControllerFragment fgm = null;
            Bundle bundle = new Bundle();
            switch (i) {
                case 0:
                    if (attention == null) {
                        attention = new AttentionFgm();
                    }
                    fgm = attention;
                    break;
                case 1:
                    if (child_two == null) {
                        child_two = new HomeChildFgm();
                    }
                    fgm = child_two;
                    break;
                case 2:
                    if (child_three == null) {
                        child_three = new HomeChildFgm();
                    }
                    fgm = child_three;
                    break;
                case 3:
                    if (child_four == null) {
                        child_four = new HomeChildFgm();
                    }
                    fgm = child_four;
                    break;
            }
            fgm.setArguments(bundle);
            return fgm;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
