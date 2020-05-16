package com.juda.gs;

import android.Manifest;

/**
 * @description: 说明
 * @author: CodingDa
 * @date : 2019/12/6 10:12
 */
public interface Constants {


    /**
     * 本地存储的配置
     */
    // 所需的全部权限
    String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,  // 读取存储
            Manifest.permission.WRITE_EXTERNAL_STORAGE, // 写入存储
            Manifest.permission.READ_PHONE_STATE,      // 读取电话状态
            Manifest.permission.CAMERA  // 摄像头权限
    };
    // intent key
    String INTENT_KEY = "intent_key";
    // 图片选择上限
    int MAX_PICTURES = 9;
    // 照相目录
    String PATH_TAKE_PHOTO = "DCIM/GS/";
    // 编辑目录
    String PATH_EDIT = "edit/";
}
