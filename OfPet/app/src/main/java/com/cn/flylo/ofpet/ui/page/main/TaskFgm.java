package com.cn.flylo.ofpet.ui.page.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Advert;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.bean.Task;
import com.cn.flylo.ofpet.bean.base.BaseBean;
import com.cn.flylo.ofpet.bean.base.DataListBean;
import com.cn.flylo.ofpet.bean.base.DataListDataBean;
import com.cn.flylo.ofpet.bean.base.ListBean;
import com.cn.flylo.ofpet.tool.event.EventTool;
import com.cn.flylo.ofpet.tool.event.EventType;
import com.cn.flylo.ofpet.ui.adapter.PrizeAdapter;
import com.cn.flylo.ofpet.ui.adapter.TaskAdapter;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.flylo.ofpet.ui.dialog.PrizeRuleDialog;
import com.cn.flylo.ofpet.url.Result;
import com.cn.flylo.ofpet.url.api.UrlId;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import com.cn.ql.frame.tool.GlideImage;
import com.cn.ql.frame.tool.banner.Banner;
import com.cn.ql.frame.tool.banner.listener.OnBannerListener;
import com.cn.ql.frame.tool.banner.loader.ImageLoader;
import com.google.gson.JsonElement;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TaskFgm extends BaseControllerFragment {

    @BindView(R.id.tvTopRight)
    TextView tvTopRight;
    @BindView(R.id.tvTopTitle)
    TextView tvTopTitle;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.banner_task)
    Banner banner;

    @Override
    public int layoutId() {
        return R.layout.fragment_task;
    }

    @Override
    public void InitView() {
        tvTopRight.setText("");
        tvTopTitle.setText("任务");

        initRecycler();
        InitRefreshLayout();
    }

    @OnClick({R.id.image_top_menu})
    public void ViewClick(View view){
        switch (view.getId()){
            case R.id.image_top_menu:
                EventTool.getInstance().send(EventType.OpenMenu);
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
                    Bundle bundle = new Bundle();
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

    private TaskAdapter adapter;
    private List<Task> list = new ArrayList();
    private void initRecycler() {
        GridLayoutManager mgr = new GridLayoutManager(act, 1);
        recyclerView.setLayoutManager(mgr);
        if (adapter == null){
            adapter = new TaskAdapter(list);
        }
        recyclerView.setAdapter(adapter);
        adapter.setItemViewOnClickListener(new ItemViewOnClickListener<Task>() {
            @Override
            public void onClick(@NotNull View v, Task data, int position) {
                if (data == null){
                    return;
                }
                switch (v.getId()){
                    case R.id.layout_item:
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", data.taskId);
                        StartTool.INSTANCE.start(act, PageEnum.TaskDetail, bundle);
                        break;
                }
            }
        });

    }

    // refresh

    @Override
    public void refresh(@Nullable SmartRefreshLayout refreshlayout) {
        super.refresh(refreshlayout);
        page = 1;
        InitLoad();
    }

    @Override
    public void load(@Nullable SmartRefreshLayout refreshlayout) {
        super.load(refreshlayout);
        page ++;
        getTaskList();
    }

    // todo

    @Override
    public void InitLoad() {
        super.InitLoad();
        getAdvertList();
        getTaskList();
    }

    private void getAdvertList(){
        getHttpTool().getPrize().getAdvertList(2);
    }

    private int page = 1;
    private void getTaskList(){
        getHttpTool().getTask().getTaskList(2, page);
    }

    @Override
    public void onNetSuccess(int urlId, @NotNull JsonElement jsonElement, @NotNull String value, @NotNull BaseBean baseBean, boolean success) {
        super.onNetSuccess(urlId, jsonElement, value, baseBean, success);
        switch (urlId){
            case UrlId.getAdvertList:
                if (success) {
                    showAdvert(value);
                } else {
                    showToast(baseBean.description);
                }
                break;
            case UrlId.getTaskList:
                if (success){
                    showTaskList(value);
                }else{
                    showToast(baseBean.description);
                }
                break;
        }
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

    private void showTaskList(String value) {
        DataListBean<Task> bean = getBean(value, DataListBean.class, Task.class);
        if (page == 1){
            list.clear();
        }
        int size = list.size();
        int changeSize = 0;

        if (bean != null){
            DataListDataBean data = bean.result;
            if (data != null){
                List<Task> listTmp = data.content;
                if (listTmp != null){
                    changeSize = listTmp.size();
                    list.addAll(listTmp);
                }
            }
        }
        if (page != 1 && changeSize == 0) {
            page--;
        }
        if (page == 1) {
            adapter.notifyDataSetChanged();
        }else{
            adapter.notifyItemRangeInserted(size, changeSize);
        }
    }
}
