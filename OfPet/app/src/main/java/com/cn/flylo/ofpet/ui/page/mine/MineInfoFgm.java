package com.cn.flylo.ofpet.ui.page.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.ui.adapter.LabelAdapter;
import com.cn.flylo.ofpet.ui.page.common.VideoFgm;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import com.google.android.material.tabs.TabLayout;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MineInfoFgm extends BaseControllerFragment {

    @BindView(R.id.rvLabel)
    RecyclerView rvLabel;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.llOperation)
    LinearLayout llOperation;
    @BindView(R.id.llEdit)
    LinearLayout llEdit;
    @BindView(R.id.llAttention)
    LinearLayout llAttention;

    @Override
    public int layoutId() {
        return R.layout.fragment_mine_info;
    }

    @Override
    public void InitView() {
        setTitle("我的视频", R.mipmap.share, true);

        initRecyclerLabel();
        initTab();

        changeType(0);
    }

    /**
     *
     * @param type 0 自己 1 好友
     */
    private void changeType(int type){
        llOperation.setVisibility(View.GONE);
        llEdit.setVisibility(View.GONE);
        llAttention.setVisibility(View.GONE);
        switch (type){
            case 0:
                llEdit.setVisibility(View.VISIBLE);
                break;
            case 1:
                llOperation.setVisibility(View.VISIBLE);
                llAttention.setVisibility(View.VISIBLE);
                break;
        }
    }


    private LabelAdapter adapter;
    private List<Bean> list = new ArrayList();
    private void initRecyclerLabel() {
        ChipsLayoutManager mgr = ChipsLayoutManager.newBuilder(act)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();
        rvLabel.setLayoutManager(mgr);
        if (adapter == null){
            adapter = new LabelAdapter(list);
        }
        rvLabel.setAdapter(adapter);
        adapter.setItemViewOnClickListener(new ItemViewOnClickListener<Bean>() {
            @Override
            public void onClick(@NotNull View v, Bean data, int position) {
                if (data == null){
                    return;
                }
                switch (v.getId()){
                    case R.id.layout_item:
                        break;
                }
            }
        });

        for (int i = 0; i < 10; i++){
            list.add(new Bean());
        }
        adapter.notifyDataSetChanged();

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
        tabLayout.addTab(tabLayout.newTab().setTag(0).setText("视频 0"));
        tabLayout.addTab(tabLayout.newTab().setTag(1).setText("喜欢 0"));
    }

    private VideoFgm child_one;
    private VideoFgm child_two;
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
                    if (child_one == null) {
                        child_one = new VideoFgm();
                    }
                    fgm = child_one;
                    break;
                case 1:
                    if (child_two == null) {
                        child_two = new VideoFgm();
                    }
                    fgm = child_two;
                    break;
            }
            fgm.setArguments(bundle);
            return fgm;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
