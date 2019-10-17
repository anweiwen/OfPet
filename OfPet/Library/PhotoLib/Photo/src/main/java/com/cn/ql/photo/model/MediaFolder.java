package com.cn.ql.photo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author axw_an
 * @create on 2019/3/19
 * @refer url：
 * @description:
 * @update: axw_an:2019/3/19:
 */
public class MediaFolder implements Serializable {

    private String name;
    private String path;
    private String firstImagePath;
    private int imageNumber;
    private int checkedNumber;
    private boolean isChecked;
    private List<MediaEntity> images = new ArrayList();

    public MediaFolder(){

    }

    public MediaFolder(String name, String path, String firstImagePath, int imageNumber, int checkedNumber, boolean isChecked, List<MediaEntity> images) {
        this.name = name;
        this.path = path;
        this.firstImagePath = firstImagePath;
        this.imageNumber = imageNumber;
        this.checkedNumber = checkedNumber;
        this.isChecked = isChecked;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public int getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }

    public int getCheckedNumber() {
        return checkedNumber;
    }

    public void setCheckedNumber(int checkedNumber) {
        this.checkedNumber = checkedNumber;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public List<MediaEntity> getImages() {
        return images;
    }

    public void setImages(List<MediaEntity> images) {
        this.images = images;
    }
}
