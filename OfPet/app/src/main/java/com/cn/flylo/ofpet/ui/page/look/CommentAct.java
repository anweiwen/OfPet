package com.cn.flylo.ofpet.ui.page.look;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerActivity;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Bean;
import com.cn.flylo.ofpet.ui.adapter.CommentAdapter;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.ql.frame.listener.itemclick.ItemViewOnClickListener;
import com.cn.ql.frame.sytem.AndroidBug5497Workaround;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommentAct extends BaseControllerActivity {

    @Override
    public int layoutId() {
        return R.layout.activity_controller;
    }

    private int id;
    private int commentNum;
    private int type;
    @Override
    protected void InitData(Bundle data) {
        super.InitData(data);
        type = data.getInt("type");
        id = data.getInt("id");
        commentNum = data.getInt("commentNum");
    }

    @Override
    public void InitView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        AndroidBug5497Workaround.assistActivity(this);

        CommentFgm commentFgm = new CommentFgm();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("commentNum", commentNum);
        bundle.putInt("type", type);
        commentFgm.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, commentFgm);
        ft.commitAllowingStateLoss();

    }


    @Override
    public void finish() {
        super.finish();

        StartTool.INSTANCE.BottomToTop(this);
    }
}
