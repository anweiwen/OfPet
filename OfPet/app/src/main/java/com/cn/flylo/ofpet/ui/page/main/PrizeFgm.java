package com.cn.flylo.ofpet.ui.page.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Advert;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.bean.PrizeClassify;
import com.cn.flylo.ofpet.bean.base.BaseBean;
import com.cn.flylo.ofpet.bean.base.ListBean;
import com.cn.flylo.ofpet.tool.event.EventTool;
import com.cn.flylo.ofpet.tool.event.EventType;
import com.cn.flylo.ofpet.ui.adapter.PrizeClassifyAdapter;
import com.cn.flylo.ofpet.ui.adapter.PrizeReceiveAdapter;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.flylo.ofpet.ui.dialog.PrizeRuleDialog;
import com.cn.flylo.ofpet.ui.page.prize.PrizeActListFgm;
import com.cn.flylo.ofpet.ui.page.prize.PrizeFreeListFgm;
import com.cn.flylo.ofpet.ui.page.prize.SignInFgm;
import com.cn.flylo.ofpet.url.Result;
import com.cn.flylo.ofpet.url.api.UrlId;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import com.cn.ql.frame.tool.GlideImage;
import com.cn.ql.frame.tool.banner.Banner;
import com.cn.ql.frame.tool.banner.listener.OnBannerListener;
import com.cn.ql.frame.tool.banner.loader.ImageLoader;
import com.cn.ql.frame.utils.WebViewUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonElement;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PrizeFgm extends BaseControllerFragment {

    @BindView(R.id.recyclerViewAct)
    RecyclerView recyclerViewAct;
    @BindView(R.id.recyclerViewReceive)
    RecyclerView recyclerViewReceive;


    @BindView(R.id.tabPrizeLayout)
    TabLayout tabPrizeLayout;
    @BindView(R.id.viewPrizePager)
    ViewPager viewPrizePager;

    @BindView(R.id.banner)
    Banner banner;

    @Override
    public int layoutId() {
        return R.layout.fragment_prize;
    }

    @Override
    public void InitView() {
        initRecyclerAct();
        initRecyclerReceive();
        initTab();
        InitRefreshLayout();
    }


    @OnClick({R.id.image_top_menu, R.id.tvTopRight})
    public void ViewClick(View view) {
        switch (view.getId()) {
            case R.id.image_top_menu:
                EventTool.getInstance().send(EventType.OpenMenu);
                break;
            case R.id.tvTopRight:
                StartTool.INSTANCE.start(act, PageEnum.ProvidePrize);
                break;
            case R.id.tvRule:
                PrizeRuleDialog prizeRuleDialog = new PrizeRuleDialog();
                prizeRuleDialog.show(act);
                break;
        }
    }

    private List<Advert> list_ads = new ArrayList<>();
    private void showBanner() {
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                String url = (String) path;
                GlideImage.INSTANCE.loadImage(act, url, imageView, R.drawable.place_holder);
            }
        });
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (list_ads.size() > position) {
                    Advert item = list_ads.get(position);
                    if (item == null){
                        return;
                    }
                    int type = item.type; // 1 外链 2 关联id
                    String about = item.abort;
                    switch (type){
                        case 1:
                            WebViewUtils.startExplorer(act, about);
                            break;
                        case 2:
                            try {
                                int id = Integer.parseInt(about);
                                Bundle bundle = new Bundle();
                                bundle.putInt("id", id);
                                StartTool.INSTANCE.start(act, PageEnum.PrizeDetails, bundle);
                            }catch (Exception e){
                                showToast("类型信息错误");
                            }
                            break;
                    }

                }
            }
        });

        List<String> list = new ArrayList<>();
        for (int i = 0; i < list_ads.size(); i++) {
            Advert ads = list_ads.get(i);
            if (ads != null) {
                list.add(Result.getImageOriginal(String.valueOf(ads.attachId)));
            }
        }
        banner.setImages(list);
        banner.start();
    }

    private PrizeClassifyAdapter adapter;
    private List<PrizeClassify> list = new ArrayList();

    private void initRecyclerAct() {
        GridLayoutManager mgr = new GridLayoutManager(act, 4);
        recyclerViewAct.setLayoutManager(mgr);
        if (adapter == null) {
            adapter = new PrizeClassifyAdapter(list);
        }
        recyclerViewAct.setAdapter(adapter);
        adapter.setItemViewOnClickListener(new ItemViewOnClickListener<PrizeClassify>() {
            @Override
            public void onClick(@NotNull View v, PrizeClassify data, int position) {
                if (data == null) {
                    return;
                }
                switch (v.getId()) {
                    case R.id.layout_item:
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", data.classifyId);
                        StartTool.INSTANCE.start(act, PageEnum.PrizeList, bundle);
                        break;
                }
            }
        });

    }

    private PrizeReceiveAdapter adapterReceive;
    private List<Bean> listReceive = new ArrayList();

    private void initRecyclerReceive() {
        GridLayoutManager mgr = new GridLayoutManager(act, 3);
        recyclerViewReceive.setLayoutManager(mgr);
        if (adapterReceive == null) {
            adapterReceive = new PrizeReceiveAdapter(listReceive);
        }
        recyclerViewReceive.setAdapter(adapterReceive);
        adapterReceive.setItemViewOnClickListener(new ItemViewOnClickListener<Bean>() {
            @Override
            public void onClick(@NotNull View v, Bean data, int position) {
                if (data == null) {
                    return;
                }
                switch (v.getId()) {
                    case R.id.layout_item:
                        StartTool.INSTANCE.start(act, PageEnum.PrizeList);
                        break;
                }
            }
        });

        for (int i = 0; i < 3; i++) {
            listReceive.add(new Bean());
        }
        adapterReceive.notifyDataSetChanged();

    }

    private int index;

    private void initTab() {
        addTabTitle();
        viewPrizePager.setAdapter(new MyAdapter(getFragmentManager()));
        tabPrizeLayout.setupWithViewPager(viewPrizePager);
        viewPrizePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        tabPrizeLayout.removeAllTabs();
        tabPrizeLayout.addTab(tabPrizeLayout.newTab().setTag(0).setText("免费"));
        tabPrizeLayout.addTab(tabPrizeLayout.newTab().setTag(1).setText("秒杀"));
        tabPrizeLayout.addTab(tabPrizeLayout.newTab().setTag(2).setText("拼团"));
        tabPrizeLayout.addTab(tabPrizeLayout.newTab().setTag(3).setText("签到有钱"));
        tabPrizeLayout.addTab(tabPrizeLayout.newTab().setTag(4).setText("奖品规则"));
    }

    private PrizeFreeListFgm child_one;
    private PrizeActListFgm child_two;
    private PrizeActListFgm child_three;
    private SignInFgm child_four;
    private PrizeActListFgm child_five;

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
                        child_one = new PrizeFreeListFgm();
                    }
                    fgm = child_one;
                    break;
                case 1:
                    if (child_two == null) {
                        child_two = new PrizeActListFgm();
                    }
                    fgm = child_two;
                    break;
                case 2:
                    if (child_three == null) {
                        child_three = new PrizeActListFgm();
                    }
                    fgm = child_three;
                    break;
                case 3:
                    if (child_four == null) {
                        child_four = new SignInFgm();
                    }
                    fgm = child_four;
                    break;
                case 4:
                    if (child_five == null) {
                        child_five = new PrizeActListFgm();
                    }
                    fgm = child_five;
                    break;
            }
            fgm.setArguments(bundle);
            return fgm;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    // refresh

    @Override
    public void refresh(@Nullable SmartRefreshLayout refreshlayout) {
        super.refresh(refreshlayout);
        InitLoad();
    }


    // todo

    @Override
    public void InitLoad() {
        super.InitLoad();
        getPrizeClassifyList();
        getAdvertList();
    }

    private void getPrizeClassifyList() {
        getHttpTool().getPrize().getPrizeClassifyList();
    }

    private void getAdvertList(){
        getHttpTool().getPrize().getAdvertList(1);
    }

    @Override
    public void onNetSuccess(int urlId, @NotNull JsonElement jsonElement, @NotNull String value, @NotNull BaseBean baseBean, boolean success) {
        super.onNetSuccess(urlId, jsonElement, value, baseBean, success);
        switch (urlId) {
            case UrlId.getPrizeClassifyList:
                if (success) {
                    showPrizeClassify(value);
                } else {
                    showToast(baseBean.description);
                }
                break;
            case UrlId.getAdvertList:
                if (success) {
                    showAdvert(value);
                } else {
                    showToast(baseBean.description);
                }
                break;
        }
    }

    private void showPrizeClassify(String value) {
        ListBean<PrizeClassify> bean = getBean(value, ListBean.class, PrizeClassify.class);
        list.clear();
        if (bean != null) {
            List<PrizeClassify> listTmp = bean.result;
            if (listTmp != null) {
                int len = listTmp.size();
                if (len > 4){
                    list.addAll(listTmp.subList(0, 4));
                }else{
                    list.addAll(listTmp);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void showAdvert(String value) {
        ListBean<Advert> bean = getBean(value, ListBean.class, Advert.class);
        list_ads.clear();
        if (bean != null){
            List<Advert> listTmp = bean.result;
            if (listTmp != null){
                list_ads.addAll(listTmp);
            }
        }
        showBanner();
    }
}
