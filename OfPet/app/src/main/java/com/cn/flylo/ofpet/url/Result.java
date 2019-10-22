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
    public static int pageSize = 10;


    public static String getImage(String path){
        if (path.startsWith("http")){
            return path;
        }
        return HttpTool.Companion.getBaseUrl()+path;
    }
}
