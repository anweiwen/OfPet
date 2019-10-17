package com.cn.flylo.ofpet.ui.page.search;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.cn.flylo.ofpet.R;
import com.cn.flylo.ofpet.base.BaseControllerFragment;
import com.cn.flylo.ofpet.ui.controller.PageEnum;
import com.cn.flylo.ofpet.ui.controller.StartTool;
import com.cn.flylo.ofpet.ui.dialog.SearchTypePop;

public class SearchFgm extends BaseControllerFragment {

    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.llType)
    LinearLayout llType;

    @BindView(R.id.etText)
    EditText etText;

    private int type = 0; // 0 视频 1 用户

    @Override
    public int layoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void InitView() {
        initEt();
    }

    private void initEt(){
        etText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    switch (type){
                        case 0:
                            StartTool.INSTANCE.start(act, PageEnum.SearchVideo);
                            break;
                        case 1:
                            StartTool.INSTANCE.start(act, PageEnum.SearchPeople);
                            break;
                    }
                }
                return false;
            }
        });
    }

    @OnClick({R.id.tvCancel, R.id.tvType})
    public void ViewClick(View v){
        hideKeyboard();
        switch (v.getId()){
            case R.id.tvCancel:
                finish();
                break;
            case R.id.tvType:
                SearchTypePop searchTypePop = new SearchTypePop();
                searchTypePop.type = type;
                searchTypePop.showPopupWindow(llType);
                searchTypePop.setViewClick(new SearchTypePop.ViewClick() {
                    @Override
                    public void onViewClick(View v) {
                        switch (v.getId()){
                            case R.id.tvText:
                                if (type == 0){
                                    type = 1;
                                }else{
                                    type = 0;
                                }
                                tvType.setText(type == 0? "视频 " : "用户 ");
                                break;
                        }
                    }
                });
                break;
        }
    }
}
