package com.juda.gs.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * @Description: 通用表单类
 * @author: CodingDa
 * @Date : 2020/5/7 14:24
 */
public class CurrencyFormBean implements MultiItemEntity {

    // 标签
    private String label;
    // 提示语
    private String hint;
    // 跳转选择id
    private int checkId;
    // 跳转选择回传值
    private String jumpValue;
    // 跳转选择回传id
    private String jumpId;
    // 单个视频路径
    private String videoPath;


    // 数据
    private List<KeyValueData> datas;
    // 条目类型
    private int itemType;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    public List<KeyValueData> getDatas() {
        return datas;
    }

    public void setDatas(List<KeyValueData> datas) {
        this.datas = datas;
    }

    public String getJumpValue() {
        return jumpValue;
    }

    public void setJumpValue(String jumpValue) {
        this.jumpValue = jumpValue;
    }

    public String getJumpId() {
        return jumpId;
    }

    public void setJumpId(String jumpId) {
        this.jumpId = jumpId;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
