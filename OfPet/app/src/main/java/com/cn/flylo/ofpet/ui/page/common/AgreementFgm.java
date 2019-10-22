package com.cn.flylo.ofpet.ui.page.common;

import android.webkit.WebView;
import android.widget.ImageView;
import butterknife.BindView;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.bean.Agreement;
import com.cn.flylo.ofpet.bean.base.BaseBean;
import com.cn.flylo.ofpet.bean.base.DataBean;
import com.cn.flylo.ofpet.url.api.UrlId;
import com.cn.ql.frame.utils.WebViewUtils;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

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

    }

    // todo

    @Override
    public void InitLoad() {
        super.InitLoad();
        getAgree();
    }

    private void getAgree(){
        getHttpTool().getCommon().getAgree(1);
    }

    @Override
    public void onNetSuccess(int urlId, @NotNull JsonElement jsonElement, @NotNull String value, @NotNull BaseBean baseBean, boolean success) {
        super.onNetSuccess(urlId, jsonElement, value, baseBean, success);
        switch (urlId){
            case UrlId.getAgree:
                if (success){
                    showAgree(value);
                }else{
                    showToast(baseBean.description);
                }
                break;
        }
    }

    private void showAgree(String value) {
        DataBean<Agreement> bean = getBean(value, DataBean.class, Agreement.class);
        if (bean == null){
            return;
        }
        Agreement agreement = bean.result;
        if (agreement == null){
            return;
        }
        WebViewUtils.loadHtml(webView, agreement.context);
    }
}
