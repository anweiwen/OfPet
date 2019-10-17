package com.cn.ql.frame.bean;

import android.graphics.Bitmap;
import android.text.TextUtils;

/**
 * @author axw_an
 * @create on 2019/1/6
 * @refer url：
 * @description:
 * @update: axw_an:2019/1/6:
 */
public class LocalContacts {

    private String name;
    private String phone;
    private Bitmap photo;

    private String letters;//显示拼音的首字母

    private int type;

    private boolean select;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    public String getLetters() {
        if (TextUtils.isEmpty(letters)){
            letters = "#";
        }else{
            letters = letters.substring(0, 1);
        }
        return letters;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isSelect() {
        return select;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
