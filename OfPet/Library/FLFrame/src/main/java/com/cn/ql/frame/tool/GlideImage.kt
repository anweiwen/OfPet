package com.cn.ql.frame.tool

import android.content.Context
import android.net.Uri
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cn.ql.frame.utils.StringUtils

/**
 * @author axw_an
 * @create on 2018/12/11
 * @refer url：
 * @description: 图片缓存加载
 * @update: axw_an:2018/12/11:
 */
object GlideImage {

    /**
     * 加载自动设置占位图的图片
     * @param context
     * @param path
     * @param ivImage
     * @param placeHolder
     */
    fun loadImage(context: Context, path: String?, ivImage: ImageView, placeHolder: Int) {
        if(StringUtils.isEmpty(path)){
            return
        }
        loadImage(context, path!!, ivImage, placeHolder, false, 1.0f)
    }

    /**
     * 加载自动设置占位图的图片
     * @param context
     * @param path
     * @param ivImage
     * @param placeHolder
     */
    fun loadCircleImage(context: Context, path: String, ivImage: ImageView, placeHolder: Int) {
        loadImage(context, path, ivImage, placeHolder, true, 1.0f)
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
    fun loadImage(context: Context, path: String, ivImage: ImageView, placeHolder: Int = 0, circleCrop: Boolean = false, thumbnail: Float = 1.0f) {
        val options = RequestOptions
                .placeholderOf(placeHolder)
                .placeholder(placeHolder)
                .error(placeHolder)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        if (circleCrop) {
            options.circleCrop()
        }

        Glide.with(context.applicationContext).load(path).thumbnail(thumbnail).apply(options).into(ivImage)
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
    fun loadRoundImage(context: Context, path: String, ivImage: ImageView, placeHolder: Int, roundingRadius: Int, thumbnail: Float) {
        val options = RequestOptions
                .bitmapTransform(RoundedCorners(roundingRadius))
                .placeholder(placeHolder)
                .error(placeHolder)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context).load(path).apply(options).thumbnail(thumbnail).into(ivImage)
    }


    fun loadImage(context: Context, uri: Uri?, placeHolder: Int, imageView: ImageView) {
        val options = RequestOptions
                .bitmapTransform(RoundedCorners(1))
                .placeholder(placeHolder)
                .error(placeHolder)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context.applicationContext)
                .load(uri)
                .apply(options)
                .into(imageView)
    }

}

