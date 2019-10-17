package com.cn.ql.frame.net;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

/**
 * @author axw_an
 * @create on 2019/7/24
 * @refer url：
 * @description:
 * @update: axw_an:2019/7/24:
 */
public class HttpBodyUtils {

    public static RequestBody getTextBody(String text){
        return RequestBody.create(MediaType.parse("text/plain"), text);
    }

    public static RequestBody getTextBodyData(String text){
        return RequestBody.create(MediaType.parse("multipart/form-data"), text);
    }

    public static MultipartBody.Part getFilePart(String key, File file){
        if (!file.isFile()){
            return null;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        return MultipartBody.Part.createFormData(key, file.getName(), requestBody);
    }

    public static RequestBody getFileBody(File file){
        return RequestBody.create(MediaType.parse("application/octet-stream"), file);
    }

    public static String getFileKey(String key, File file){
        String fileKey = key+"\"; filename=\"" + file.getName() + "";
        return fileKey;
    }
}
