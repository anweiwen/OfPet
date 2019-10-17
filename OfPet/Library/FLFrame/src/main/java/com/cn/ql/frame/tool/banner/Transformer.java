package com.cn.ql.frame.tool.banner;


import androidx.viewpager.widget.ViewPager.PageTransformer;

import com.cn.ql.frame.tool.banner.transformer.AccordionTransformer;
import com.cn.ql.frame.tool.banner.transformer.BackgroundToForegroundTransformer;
import com.cn.ql.frame.tool.banner.transformer.CubeInTransformer;
import com.cn.ql.frame.tool.banner.transformer.CubeOutTransformer;
import com.cn.ql.frame.tool.banner.transformer.DefaultTransformer;
import com.cn.ql.frame.tool.banner.transformer.DepthPageTransformer;
import com.cn.ql.frame.tool.banner.transformer.FlipHorizontalTransformer;
import com.cn.ql.frame.tool.banner.transformer.FlipVerticalTransformer;
import com.cn.ql.frame.tool.banner.transformer.ForegroundToBackgroundTransformer;
import com.cn.ql.frame.tool.banner.transformer.RotateDownTransformer;
import com.cn.ql.frame.tool.banner.transformer.RotateUpTransformer;
import com.cn.ql.frame.tool.banner.transformer.ScaleInOutTransformer;
import com.cn.ql.frame.tool.banner.transformer.StackTransformer;
import com.cn.ql.frame.tool.banner.transformer.TabletTransformer;
import com.cn.ql.frame.tool.banner.transformer.ZoomInTransformer;
import com.cn.ql.frame.tool.banner.transformer.ZoomOutSlideTransformer;
import com.cn.ql.frame.tool.banner.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
