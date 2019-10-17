package com.cn.ql.frame.widget.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by lvr on 2017/5/25.
 */

public class FlyRecyclerView extends RecyclerView {

    private LinearLayout mHeaderViewContainer;
    private LinearLayout mFooterViewContainer;

    public FlyRecyclerView(Context context) {
        this(context, null);
    }

    public FlyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLinear();
    }

    public void setGridSize(int gridSize){
        setLayoutManager(new GridLayoutManager(getContext(), gridSize));
        setHasFixedSize(true);
    }

    public LinearLayoutManager setLinear(){
        LinearLayoutManager mgr = new LinearLayoutManager(getContext());
        setLayoutManager(mgr);
        return mgr;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
    }

    public LinearLayout getHeaderContainer() {
        ensureHeaderViewContainer();
        return mHeaderViewContainer;
    }

    public LinearLayout getFooterContainer() {
        ensureFooterViewContainer();
        return mFooterViewContainer;
    }

    public void addHeaderView(View headerView) {
        ensureHeaderViewContainer();
        mHeaderViewContainer.addView(headerView);
        Adapter adapter = getAdapter();
        if (adapter != null) {
            adapter.notifyItemChanged(0);
        }
    }

    public void addFooterView(View footerView) {
        ensureFooterViewContainer();
        mFooterViewContainer.addView(footerView);
        Adapter adapter = getAdapter();
        if (adapter != null) {
            adapter.notifyItemChanged(adapter.getItemCount() - 1);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        ensureHeaderViewContainer();
        ensureFooterViewContainer();
        super.setAdapter(new WrapperAdapter(adapter, mHeaderViewContainer, mFooterViewContainer));
    }


    private void ensureHeaderViewContainer() {
        if (mHeaderViewContainer == null) {
            mHeaderViewContainer = new LinearLayout(getContext());
            mHeaderViewContainer.setOrientation(LinearLayout.VERTICAL);
            mHeaderViewContainer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void ensureFooterViewContainer() {
        if (mFooterViewContainer == null) {
            mFooterViewContainer = new LinearLayout(getContext());
            mFooterViewContainer.setOrientation(LinearLayout.VERTICAL);
            mFooterViewContainer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    public <T extends View> T getHeaderView(@IdRes int viewId){
        if (mHeaderViewContainer != null){
            return mHeaderViewContainer.findViewById(viewId);
        }
        return null;
    }

    public <T extends View> T getFooterView(@IdRes int viewId){
        if (mFooterViewContainer != null){
            return mFooterViewContainer.findViewById(viewId);
        }
        return null;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (onScollChangedListener != null){
            onScollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }


    private OnScollChangedListener onScollChangedListener = null;

    public void setOnScollChangedListener(OnScollChangedListener onScollChangedListener) {
        this.onScollChangedListener = onScollChangedListener;
    }

    public interface OnScollChangedListener {
        void onScrollChanged(FlyRecyclerView recyclerView, int x, int y, int oldx, int oldy);
    }
}