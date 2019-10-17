package com.cn.ql.photo.tool;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.cn.ql.photo.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author axw_an
 * @create on 2018/12/11
 * @refer url：
 * @description: 图片缓存加载
 * @update: axw_an:2018/12/11:
 */
public class GlideImage {

    /**
     * 加载正常图片
     * @param context
     * @param path
     * @param ivImage
     */
    public static void loadImage(Context context, String path, ImageView ivImage) {
        loadImage(context, path, ivImage, 0, false, 1.0f);
    }

    /**
     * 加载自动设置占位图的图片
     * @param context
     * @param path
     * @param ivImage
     * @param placeHolder
     */
    public static void loadImage(Context context, String path, ImageView ivImage, int placeHolder) {
        loadImage(context, path, ivImage, placeHolder, false, 1.0f);
    }

    /**
     * 加载自动设置占位图的图片
     * @param context
     * @param path
     * @param ivImage
     * @param placeHolder
     */
    public static void loadCircleImage(Context context, String path, ImageView ivImage, int placeHolder) {
        loadImage(context, path, ivImage, placeHolder, true, 1.0f);
    }

    /**
     * 加载正常和圆形图
     * @param context 上下文
     * @param path 路径
     * @param ivImage 控件
     * @param placeHolder 预加载占位图
     * @param circleCrop 是否圆图
     * @param thumbnail 缩略图 0~1.0f
     */
    public static void loadImage(Context context, String path, ImageView ivImage, int placeHolder, boolean circleCrop, float thumbnail) {
        RequestOptions options = RequestOptions
                        .placeholderOf(placeHolder)
                        .placeholder(placeHolder)
                        .error(placeHolder)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (circleCrop) {
            options.circleCrop();
        }

        Glide.with(context.getApplicationContext()).load(path).thumbnail(thumbnail).apply(options).into(ivImage);
    }

    public static void loadImage(String uri, ImageView ivImage, int placeHolder, boolean circleCrop, float thumbnail, int width, int height) {
            RequestOptions options = RequestOptions
                    .placeholderOf(placeHolder)
                    .override(width, height)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    ;
            Glide.with(ivImage.getContext()).load(uri).transition(withCrossFade()).thumbnail(thumbnail).apply(options).into(ivImage);
    }

    /**
     * 加载圆角图片
     * @param context 上下文
     * @param path 路径
     * @param ivImage 控件
     * @param placeHolder 预加载占位图
     * @param roundingRadius 圆角多少 像素
     * @param thumbnail 缩略图 0~1.0f
     */
    public static void loadRoundImage(Context context, String path, ImageView ivImage, int placeHolder, int roundingRadius, float thumbnail) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundedCorners(roundingRadius))
                .placeholder(placeHolder)
                .error(placeHolder)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(path).apply(options).thumbnail(thumbnail).into(ivImage);
    }

}
