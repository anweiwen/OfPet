package com.cn.flylo.ofpet.ui.page.prize;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.bean.OfferUser;
import com.cn.flylo.ofpet.bean.PrizeDetail;
import com.cn.flylo.ofpet.bean.Video;
import com.cn.flylo.ofpet.bean.base.BaseBean;
import com.cn.flylo.ofpet.bean.base.DataBean;
import com.cn.flylo.ofpet.ui.adapter.WorksAdapter;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.flylo.ofpet.ui.dialog.QuestRewardsDialog;
import com.cn.flylo.ofpet.url.api.UrlId;
import com.cn.flylo.ofpet.utils.DateUtils;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import com.cn.ql.frame.tool.GlideImage;
import com.google.gson.JsonElement;
import com.shehuan.niv.NiceImageView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PrizeDetailsFgm extends BaseControllerFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvSize)
    TextView tvSize;
    @BindView(R.id.tvRule)
    TextView tvRule;
    @BindView(R.id.tvGet)
    TextView tvGet;
    @BindView(R.id.ivHead)
    NiceImageView ivHead;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.ivVideo)
    NiceImageView ivVideo;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.tvVideoSize)
    TextView tvVideoSize;

    @Override
    public int layoutId() {
        return R.layout.fragment_prize_detail;
    }

    private int id;
    @Override
    protected void InitData(Bundle data) {
        super.InitData(data);
        id = data.getInt("id");
    }

    @Override
    public void InitView() {
        setTitle("", R.mipmap.share_gray, true);
        initRecycler();
    }

    @OnClick({R.id.tvGet})
    public void ViewClick(View view) {
        switch (view.getId()) {
            case R.id.tvGet:
                QuestRewardsDialog questRewardsDialog = new QuestRewardsDialog();
                questRewardsDialog.show(act);

                StartTool.INSTANCE.start(act, PageEnum.EditAward);
                break;
        }
    }

    private WorksAdapter adapter;
    private List<Video> list = new ArrayList();

    private void initRecycler() {
        GridLayoutManager linearLayoutManager = new GridLayoutManager(act, 3);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (adapter == null) {
            adapter = new WorksAdapter(list);
        }
        recyclerView.setAdapter(adapter);
        adapter.setItemViewOnClickListener(new ItemViewOnClickListener<Video>() {
            @Override
            public void onClick(@NotNull View v, Video data, int position) {
                if (data == null) {
                    return;
                }
                switch (v.getId()) {
                    case R.id.layout_item:
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", data.videoId);
                        StartTool.INSTANCE.start(act, PageEnum.Look, bundle);
                        break;
                }
            }
        });
    }

    // todo


    @Override
    public void InitLoad() {
        super.InitLoad();
        getPrizeInfo();
    }

    private int page = 1;

    private void getPrizeInfo() {
        getHttpTool().getPrize().getPrizeInfo(id, page);
    }

    @Override
    public void onNetSuccess(int urlId, @NotNull JsonElement jsonElement, @NotNull String value, @NotNull BaseBean baseBean, boolean success) {
        super.onNetSuccess(urlId, jsonElement, value, baseBean, success);
        switch (urlId) {
            case UrlId.getPrizeInfo:
                if (success) {
                    showInfo(value);
                } else {
                    showToast(baseBean.description);
                }
                break;
        }
    }

    private void showInfo(String value) {
        DataBean<PrizeDetail> bean = getBean(value, DataBean.class, PrizeDetail.class);
        if (bean != null) {
            PrizeDetail detail = bean.result;
            if (detail != null) {
                showDetail(detail);
            }
        }
    }

    private void showDetail(PrizeDetail detail) {
        tvTime.setText(DateUtils.getDate(detail.startTime)+"~"+DateUtils.getDate(detail.endTime));
        tvSize.setText("已参加 "+detail.attendCount+"人");
        tvRule.setText(getStr(detail.rule));

        tvVideoSize.setText("参赛作品（"+detail.videoCount+"个）");

        OfferUser offerUser = detail.offerUser;
        if (offerUser != null){
            GlideImage.INSTANCE.loadImage(act, offerUser.headUrl, ivHead, R.drawable.place_holder_head);
            tvName.setText(getStr(offerUser.userName));
            tvDesc.setText(getStr(offerUser.styleSign));
        }

        if (page == 1){
            list.clear();
        }

        int size = list.size();
        int changeSize = 0;
        List<Video> listTmp = detail.videosList;
        if (listTmp != null){
            changeSize = listTmp.size();
            list.addAll(listTmp);
        }
        if (page != 1 && listTmp.size() == 0) {
            page--;
        }

        if (page == 1) {
            adapter.notifyDataSetChanged();
        }else{
            adapter.notifyItemRangeInserted(size, changeSize);
        }
    }
}
