package com.cn.ql.photo.listener;


import com.cn.ql.photo.model.MediaEntity;
import com.cn.ql.photo.model.MediaFolder;

import java.util.List;

/**
 * @author axw_an
 * @create on 2019/3/19
 * @refer url：
 * @description:
 * @update: axw_an:2019/3/19:
 */
public interface ImageLoadListener {
    void loadStart();
    void loadEnd();
    void loadComplete(List<MediaEntity> allEntity, List<MediaFolder> folders);
}
