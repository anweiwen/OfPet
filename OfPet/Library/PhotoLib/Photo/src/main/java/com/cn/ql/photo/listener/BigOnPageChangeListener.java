package com.cn.ql.photo.listener;

/**
 * @author axw_an
 * @create on 2019/3/13
 * @refer url：
 * @description:
 * @update: axw_an:2019/3/13:
 */
public interface BigOnPageChangeListener {
    /**
     * @param position 当前页码
     * @param positionOffset 页面偏移量（百分比） 当往右滑动时递增，往左滑动时递减。
     * @param positionOffsetPixels 页面像素偏移量 当往右滑动时递增，往左滑动时递减。
     */
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    /**
     *
     * @param position 当前选中的页码
     */
    void onPageSelected(int position);

    /**
     * SCROLL_STATE_IDLE : 值为0，表示当前页已经选定。
     * SCROLL_STATE_DRAGGING: 值为1，表示当前页面正在拖动。
     * SCROLL_STATE_SETTING: 值为2，表示当前页面正在拖动中，还没有到选定状态。
     * @param state
     */
    void onPageScrollStateChanged(int state);
}
