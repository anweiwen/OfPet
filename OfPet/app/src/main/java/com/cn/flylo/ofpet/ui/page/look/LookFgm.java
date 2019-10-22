package com.cn.flylo.ofpet.ui.page.look;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Video;
import com.cn.flylo.ofpet.bean.base.BaseBean;
import com.cn.flylo.ofpet.bean.base.DataBean;
import com.cn.flylo.ofpet.ui.JumpCode;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.flylo.ofpet.url.api.UrlId;
import com.cn.ql.frame.tool.GlideImage;
import com.cn.ql.frame.tool.StartActTool;
import com.google.gson.JsonElement;
import com.shehuan.niv.NiceImageView;
import com.tencent.liteav.demo.play.SuperPlayerConst;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.liteav.demo.play.controller.TCVodControllerBase;
import com.tencent.liteav.demo.play.controller.TCVodControllerLarge;
import com.tencent.liteav.demo.play.controller.TCVodControllerSmall;
import com.tencent.liteav.demo.player.server.VideoDataMgr;
import org.jetbrains.annotations.NotNull;


public class LookFgm extends BaseControllerFragment {

    @BindView(R.id.superPlayer)
    SuperPlayerView superPlayer;

    @BindView(R.id.ivPause)
    ImageView ivPause;

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvContent)
    TextView tvContent;

    @BindView(R.id.ivHead)
    NiceImageView ivHead;
    @BindView(R.id.ivAdd)
    ImageView ivAdd;
    @BindView(R.id.ivZan)
    NiceImageView ivZan;
    @BindView(R.id.tvZan)
    TextView tvZan;
    @BindView(R.id.tvComment)
    TextView tvComment;
    @BindView(R.id.tvShare)
    TextView tvShare;
    @BindView(R.id.tvSee)
    TextView tvSee;
    @BindView(R.id.llComment)
    LinearLayout llComment;

    @Override
    public int layoutId() {
        return R.layout.fragment_look;
    }

    private int id;

    @Override
    protected void InitData(Bundle data) {
        super.InitData(data);
        id = data.getInt("id");
    }

    @Override
    public void InitView() {
        act.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initSuperView();
        isPlayBg(true);
    }

    // controller
    private TCVodControllerSmall small;

    private void initSuperView() {

        View view = superPlayer.getRootView();
        small = view.findViewById(R.id.controller_small);
        TCVodControllerLarge large = view.findViewById(R.id.controller_large);
        View tcDanmuView = view.findViewById(R.id.danmaku_view);
        View tcVodControllerFloat = view.findViewById(R.id.controller_float);

        if (small != null) {
            small.setVisibility(View.GONE);
            small.setStateListener(new TCVodControllerBase.StateListener() {
                @Override
                public void onPlayFinish() {
                    small.seekTo(0);
                    small.changePlayState();
                }
            });
        }
        if (large != null) {
            large.setVisibility(View.GONE);
        }
        if (tcDanmuView != null) {
            tcDanmuView.setVisibility(View.GONE);
        }
        if (tcVodControllerFloat != null) {
            tcVodControllerFloat.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.llComment, R.id.ivPause, R.id.ivBack, R.id.ivZan, R.id.llEtComment})
    public void ViewClick(View view) {
        if (video == null){
            return;
        }
        switch (view.getId()) {
            case R.id.llComment:
            case R.id.llEtComment:
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putInt("commentNum", video.commentNum);
                StartActTool.INSTANCE.Start(act, CommentAct.class, bundle, 0x01);
                break;
            case R.id.ivPause:
                if (small != null) {
                    isPlay = small.changePlayState();
                    isPlayBg(isPlay);
                }
                break;
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivZan:
                videoPraise();
                break;
        }
    }

    private boolean isPlay;

    private void isPlayBg(boolean isPlay) {
        this.isPlay = isPlay;
        if (isPlay) {
            ivPause.setImageResource(0);
            ivPause.setBackground(null);
        } else {
            ivPause.setImageResource(R.mipmap.bofang1);
            ivPause.setBackgroundColor(getResources().getColor(R.color.black95));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destory();
    }

    // superPlayer

    private void destory() {
        superPlayer.release();
        if (superPlayer.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            superPlayer.resetPlayer();
        }
        VideoDataMgr.getInstance().setGetVideoInfoListListener(null);
    }


    // todo

    @Override
    public void InitLoad() {
        super.InitLoad();
        getVideo();
    }

    private void getVideo() {
        getHttpTool().getVideo().getVideo(id);
    }

    private void videoPraise(){
        if (video == null){
            return;
        }
        int status = video.status;
        if (status == 1){
            status = 0;
        }else{
            status = 1;
        }
        getHttpTool().getVideo().videoPraise(id, status);
    }

    @Override
    public void onNetSuccess(int urlId, @NotNull JsonElement jsonElement, @NotNull String value, @NotNull BaseBean baseBean, boolean success) {
        super.onNetSuccess(urlId, jsonElement, value, baseBean, success);
        switch (urlId) {
            case UrlId.getVideo:
                if (success) {
                    showVideo(value);
                } else {
                    showToast(baseBean.description);
                }
                break;
            case UrlId.videoPraise:
                if (success){
                    getVideo();
                }else{
                    showToast(baseBean.description);
                }
                break;
        }
    }

    private Video video;
    private void showVideo(String value) {
        DataBean<Video> bean = getBean(value, DataBean.class, Video.class);
        if (bean == null) {
            return;
        }
        video = bean.result;
        if (video == null) {
            return;
        }
        showVideoData(video);
    }

    private void showVideoData(Video video) {
        setVideoUrl(video.title, video.videoUrl);

        tvName.setText("@"+getStr(video.nickName));
        tvContent.setText(getStr(video.context));

        GlideImage.INSTANCE.loadImage(act, video.headUrl, ivHead, R.drawable.place_holder_head);

        //video.isFollow;

        tvZan.setText(String.valueOf(video.goodsNum));
        tvComment.setText(String.valueOf(video.commentNum));
        tvShare.setText(String.valueOf(video.shareNum));
        tvSee.setText(String.valueOf(video.playNum));

        int status = video.status;
        ivZan.setSelected(status == 1);

    }

    private void setVideoUrl(String title, String videoUrl) {
        SuperPlayerModel superPlayerModel = new SuperPlayerModel();
        superPlayerModel.title = title;
        superPlayerModel.url = videoUrl;
        superPlayer.playWithModel(superPlayerModel);
    }
}
