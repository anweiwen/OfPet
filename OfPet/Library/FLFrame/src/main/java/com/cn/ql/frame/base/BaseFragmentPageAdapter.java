package com.cn.ql.frame.base;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author axw_an on 2018/5/4.
 * 注：PageAdapter基类
 */
public class BaseFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fgms;
    public BaseFragmentPageAdapter(FragmentManager fm, List<Fragment> fgms) {
        super(fm);
        this.fgms = fgms;
    }

    @Override
    public int getCount() {
        return fgms.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fgms.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
