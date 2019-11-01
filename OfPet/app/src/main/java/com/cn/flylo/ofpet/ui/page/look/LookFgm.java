package com.cn.flylo.ofpet.ui.page.look;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Video;
import com.cn.flylo.ofpet.bean.base.BaseBean;
import com.cn.flylo.ofpet.bean.base.DataBean;
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

import butterknife.BindView;
import butterknife.OnClick;


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


    @BindView(R.id.fl_operation)
    LinearLayout fl_operation;

    @BindView(R.id.fl_add_love)
    FrameLayout fl_add_love;

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

        initTouch();
    }

    long mLastTime = 0;
    long mCurTime = 0;
    float lastX = 0;
    float lastY = 0;

    private void initTouch() {
        fl_add_love.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();

                float angle = getAngle(x, y, lastX, lastY);
                if (lastY == 0 && lastX == 0) {
                    angle = 0;
                }
                mLastTime = mCurTime;
                mCurTime = System.currentTimeMillis();
                if (mCurTime - mLastTime < 300) {
                    addLoveView(x, y, angle);
                } else {
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {
                                Thread.sleep(400);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (mCurTime - mLastTime > 300){
                                act.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        changeStart();
                                    }
                                });
                            }
                        }
                    }.start();
                }

                lastX = x;
                lastY = y;

                //showToast("hello...x:" + x + ", y:" + y + ", an:" + angle);
                System.out.println("hello...x:" + x + ", y:" + y + ", an:" + angle);
                return false;
            }
        });
    }

    public float getAngle(float thisX, float thisY, float x, float y) {
        float a = (thisY - y) / (thisX - x);
        float angle = (float) (Math.atan(a) / Math.PI * 180);
        System.out.println("angle:" + angle+", a:"+a);
        return angle;

    }

    private void addLoveView(float x, float y, float angle) {
        ImageView ivLove = new ImageView(act);
        ivLove.setImageResource(R.mipmap.love);

        int wh = 120;
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(wh, wh);
        lp.leftMargin = (int) (x - wh / 2);
        lp.topMargin = (int) (y - wh / 2);

        ivLove.setRotation(angle);

        fl_add_love.addView(ivLove, lp);

        new CountDownTimer(500, 500) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                fl_add_love.removeView(ivLove);
            }
        }.start();
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

    @OnClick({R.id.llComment, R.id.ivBack, R.id.ivZan, R.id.llEtComment})
    public void ViewClick(View view) {
        if (video == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        switch (view.getId()) {
            case R.id.llComment:
                if (!isLogin()){
                    showNoLogin();
                    return;
                }
                bundle.putInt("commentNum", video.commentNum);
                StartActTool.INSTANCE.Start(act, CommentAct.class, bundle, 0x01);
                break;
            case R.id.llEtComment:
                if (!isLogin()){
                    showNoLogin();
                    return;
                }
                bundle.putInt("type", 1);
                StartActTool.INSTANCE.Start(act, CommentAct.class, bundle, 0x01);
                break;
            case R.id.ivPause:
                changeStart();
                break;
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivZan:
                videoPraise();
                break;
        }
    }

    private void changeStart() {
        if (small != null) {
            isPlay = small.changePlayState();
            isPlayBg(isPlay);
        }
    }

    private boolean isPlay;

    private void isPlayBg(boolean isPlay) {
        this.isPlay = isPlay;
        if (isPlay) {
            ivPause.setImageResource(0);
            //ivPause.setBackground(null);
        } else {
            ivPause.setImageResource(R.mipmap.bofang1);
            //ivPause.setBackgroundColor(getResources().getColor(R.color.black95));
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

    private void videoPraise() {
        if (video == null) {
            return;
        }
        int status = video.status;
        if (status == 1) {
            status = 0;
        } else {
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
                if (success) {
                    int pisTrue = video.pisTrue;
                    if (pisTrue == 0) {
                        video.pisTrue = 1;
                        video.goodsNum++;
                    } else {
                        video.pisTrue = 0;
                        video.goodsNum--;
                    }
                    showZan();
                } else {
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

        tvName.setText("@" + getStr(video.nickName));
        tvContent.setText(getStr(video.context));

        GlideImage.INSTANCE.loadImage(act, video.headUrl, ivHead, R.drawable.place_holder_head);

        //video.isFollow;

        tvComment.setText(String.valueOf(video.commentNum));
        tvShare.setText(String.valueOf(video.shareNum));
        tvSee.setText(String.valueOf(video.playNum));

        showZan();
    }

    private void showZan() {
        if (video != null) {
            ivZan.setSelected(video.pisTrue == 1);
            tvZan.setText(String.valueOf(video.goodsNum));
        }
    }

    private void setVideoUrl(String title, String videoUrl) {
        SuperPlayerModel superPlayerModel = new SuperPlayerModel();
        superPlayerModel.title = title;
        superPlayerModel.url = videoUrl;
        superPlayer.playWithModel(superPlayerModel);
    }
}
