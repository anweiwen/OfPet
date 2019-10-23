package com.cn.flylo.ofpet.url;

import com.cn.flylo.ofpet.url.http.HttpTool;

/**
 * @author axw_an
 * @create on 2019/7/9
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/18:
 */
public class Result {

    public static int success = 200;
    public static int pageSize = 20;

    /**
     * 缩略图
     * @param attachId
     * @return
     */
    public static String getImageThumbnail(String attachId){
        return getImage(4, attachId);
    }

    /**
     * 原图
     * @param attachId
     * @return
     */
    public static String getImageOriginal(String attachId){
        return getImage(2, attachId);
    }

    /**
     * 常规1/原图2/缩略4
     * @param type
     * @param attachId
     * @return
     */
    public static String getImage(int type, String attachId){
        return HttpTool.Companion.getBaseUrl()+"pets/attach/showPic?type="+type+"&attachId="+attachId;
    }
}
