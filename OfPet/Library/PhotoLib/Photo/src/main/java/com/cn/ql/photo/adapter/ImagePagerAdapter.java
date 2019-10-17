package com.cn.ql.photo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.cn.ql.photo.R;
import com.cn.ql.photo.model.MediaEntity;
import com.cn.ql.photo.tool.GlideImage;
import com.cn.ql.photo.widget.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;


/**
 * @author axw_an
 * @create on 2019/3/13
 * @refer url：
 * @description:
 * @update: axw_an:2019/3/13:
 */
public class ImagePagerAdapter extends PagerAdapter {

    private Context context;
    private List<MediaEntity> list = new ArrayList<>();

    public ImagePagerAdapter(Context context, List<MediaEntity> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.item_big_image, null);
        PhotoView image = view.findViewById(R.id.image);
        MediaEntity mediaEntity = list.get(position);
        if (mediaEntity != null) {
            GlideImage.loadImage(context, mediaEntity.getLocalPath(), image);
        }
        container.addView(view);
        return view;
    }
}
