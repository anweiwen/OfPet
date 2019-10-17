package com.cn.ql.photo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.cn.ql.photo.R;
import com.cn.ql.photo.adapter.ImagePagerAdapter;
import com.cn.ql.photo.listener.BigOnPageChangeListener;
import com.cn.ql.photo.model.MediaEntity;
import com.cn.ql.photo.model.MediaFolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author axw_an
 * @create on 2019/3/13
 * @refer url：
 * @description:
 * @update: axw_an:2019/3/13:
 */
public class BigImageFragment extends Fragment {

    private ViewPager viewPager;

    private int position;
    private List<MediaEntity> datas = new ArrayList<>();

    private ImagePagerAdapter adapter;

    private BigOnPageChangeListener bigOnPageChangeListener;

    public void setBigOnPageChangeListener(BigOnPageChangeListener bigOnPageChangeListener) {
        this.bigOnPageChangeListener = bigOnPageChangeListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.f_big_image, null);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void initView(View view) {
        viewPager = view.findViewById(R.id.viewPager);
    }

    private void init() {
        viewPager.addOnPageChangeListener(listener);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Serializable serializable = bundle.getSerializable("data");
            position = bundle.getInt("position");
            if (serializable != null) {
                if (serializable instanceof MediaFolder) {
                    MediaFolder total = (MediaFolder) serializable;
                    if (total != null) {
                        datas = total.getImages();
                    }
                }
            }
        }

        initAdapter();

    }

    private void initAdapter() {
        if (adapter == null){
            adapter = new ImagePagerAdapter(getActivity(), datas);
        }
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);


    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
            if (bigOnPageChangeListener != null){
                bigOnPageChangeListener.onPageScrolled(i, v, i1);
            }
        }

        @Override
        public void onPageSelected(int i) {
            if (bigOnPageChangeListener != null){
                bigOnPageChangeListener.onPageSelected(i);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {
            if (bigOnPageChangeListener != null){
                bigOnPageChangeListener.onPageScrollStateChanged(i);
            }
        }
    };
}
