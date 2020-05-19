package com.juda.gs.bean;

import android.graphics.drawable.Drawable;

/**
 * @Description: 选择图片
 * @author: CodingDa
 * @Date : 2020/3/23 16:15
 */
public class SelectImage {

    // 路径
    private String path;
    // 是否默认图片
    private boolean isDefault;
    //默认图片
    private Drawable defaultAdd;

    public SelectImage() {
    }

    public SelectImage(String path, boolean isDefault) {
        this.path = path;
        this.isDefault = isDefault;
    }

    public SelectImage(boolean isDefault, Drawable defaultAdd) {
        this.isDefault = isDefault;
        this.defaultAdd = defaultAdd;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Drawable getDefaultAdd() {
        return defaultAdd;
    }

    public void setDefaultAdd(Drawable defaultAdd) {
        this.defaultAdd = defaultAdd;
    }
}
