package com.cn.flylo.ofpet.ui.page.task;

import android.view.View;
import android.webkit.WebView;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.ql.frame.utils.WebViewUtils;

public class TaskDetailFgm extends BaseControllerFragment {

    @BindView(R.id.webView)
    WebView webView;

    @Override
    public int layoutId() {
        return R.layout.fragment_task_detail;
    }

    @Override
    public void InitView() {
        setTitle("任务详情", R.mipmap.share_gray, true);

        WebViewUtils.loadHtml(webView, "\n" +
                "                            1.视频规则视频规则视频规则视频规则视频规则视频规则视频规\n" +
                "    视频规则视频规则视频规则视频规则视频规则视频规则视频规\n" +
                "    则视频规则\n" +
                "                        ");
    }

    @OnClick({R.id.tvConfirm})
    public void ViewClick(View v){
        switch (v.getId()){
            case R.id.tvConfirm:
                StartTool.INSTANCE.start(act, PageEnum.EditTask);
                break;
        }
    }
}
