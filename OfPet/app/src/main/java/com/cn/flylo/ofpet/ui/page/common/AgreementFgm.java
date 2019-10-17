package com.cn.flylo.ofpet.ui.page.common;

import android.webkit.WebView;
import android.widget.ImageView;
import butterknife.BindView;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.ql.frame.utils.WebViewUtils;

public class AgreementFgm extends BaseControllerFragment {

    @BindView(R.id.image_title_back)
    ImageView image_title_back;

    @BindView(R.id.webView)
    WebView webView;

    @Override
    public int layoutId() {
        return R.layout.fragment_agreement;
    }

    @Override
    public void InitView() {
        setTitle("协议内容", "", true);

        image_title_back.setImageResource(R.mipmap.guangbi1);

        WebViewUtils.loadHtml(webView, "文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容");
    }
}
