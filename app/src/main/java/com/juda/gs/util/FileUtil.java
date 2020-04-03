package com.juda.gs.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @description: 文件工具类
 * @author: CodingDa
 * @date : 2019/8/27 10:25
 */
public class FileUtil {

    // 判断是否插入SD卡
    public static final boolean getSdState() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    // 判断文件夹是否存在 不存在则创建
    public static final boolean checkDir(String path) {
        File freeAddListDir = new File(path);
        if (!freeAddListDir.exists()) {
            freeAddListDir.mkdirs();
            return false;
        }
        return true;
    }

    /**
     * Description: 获取外部储存卡根目录
     * author: CodingDa
     * Date: 2020-3-5 11:22:15
     */
    public static String getDiskDir(String pathOrName) {
        return Environment.getExternalStorageDirectory() + File.separator + pathOrName;
    }

    /**
     * Description: 获取外部存储卡缓存目录
     * author: CodingDa
     * Date: 2020-3-5 11:22:15
     *
     * @param context 上下文
     * @return 缓存目录地址
     */
    public static String getDiskCacheDir(Context context) {
        if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                !Environment.isExternalStorageRemovable()) && context.getExternalCacheDir() != null) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }

    /**
     * Description: 获取外部存储卡缓存目录
     * author: CodingDa
     * Date: 2020-3-5 11:22:15
     *
     * @param context 上下文
     * @param newPath 目录活文件名
     * @return 缓存目录下文件或文件夹文件对象
     */
    public static String getDiskCacheDir(Context context, String newPath) {
        String cachePath = getDiskCacheDir(context);
        File path = new File(cachePath);
        if (!path.exists()) {
            path.exists();
        }
        return cachePath + File.separator + newPath;
    }

}
