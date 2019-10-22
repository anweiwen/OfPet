package com.cn.flylo.ofpet.bean.base;

import java.util.List;

/**
 * @author axw_an
 * @create on 2019/7/11
 * @refer url：
 * @description:
 * @update: axw_an:2019/7/11:
 */
public class DataListDataBean<T> {
    public int totalCount;
    public boolean firstPage;
    public boolean lastPage;
    public int totalPage;
    public int page;
    public int size;
    public List<T> content;
}
