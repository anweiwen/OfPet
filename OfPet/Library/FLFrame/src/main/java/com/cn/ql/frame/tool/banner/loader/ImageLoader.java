package com.cn.ql.frame.tool.banner.loader;

import android.content.Context;
import android.widget.ImageView;

import com.cn.ql.frame.tool.DensityTool;
import com.cn.ql.frame.tool.DisplayTool;
import com.shehuan.niv.NiceImageView;

public abstract class ImageLoader implements ImageLoaderInterface<ImageView> {

    @Override
    public ImageView createImageView(Context context) {
        NiceImageView imageView = new NiceImageView(context);
        return imageView;
    }

}
