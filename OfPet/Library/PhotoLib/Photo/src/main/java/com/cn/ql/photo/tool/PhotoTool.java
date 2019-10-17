package com.cn.ql.photo.tool;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.File;

/**
 * Created by AXW on 2018/5/4.
 * 注：图片操作
 */

public class PhotoTool {

    private static PhotoTool instance;

    public static PhotoTool getInstance() {
        synchronized (PhotoTool.class) {
            if (instance == null) {
                if (instance == null) {
                    instance = new PhotoTool();
                }
            }
        }
        return instance;
    }

    SelectPhotoTool selectPhoto;
    Activity act;
    public void Init(Activity act) {
        this.act = act;
        selectPhoto = new SelectPhotoTool();
        selectPhoto.Init(act, true, new SelectPhotoTool.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                Log.e("QA", "file:" + outputFile.getAbsolutePath());
            }

            @Override
            public void onCompressionFinish(File outputFile) {

            }
        });
    }

    /**
     * 拍照
     */
    public void take(){
        PermissionTool.with(act).permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA).result(new PermissionTool.Result() {
            @Override
            public void onGranted() {
                selectPhoto.TakePhoto();
            }

            @Override
            public void onDenied() {

            }
        }).request();
    }

    /**
     * 选择图片
     */
    public void select() {
        PermissionTool.with(act).permissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).result(new PermissionTool.Result() {
            @Override
            public void onGranted() {
                selectPhoto.SelectPhoto();
            }

            @Override
            public void onDenied() {

            }
        }).request();
    }

    public void onActResult(int requestCode, int resultCode, Intent data) {
        selectPhoto.ActivityForResult(requestCode, resultCode, data);
    }

}
