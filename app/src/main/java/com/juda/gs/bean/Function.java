package com.juda.gs.bean;

import android.text.SpannableString;

/**
 * @description: Function
 * @author: CodingDa
 * @date : 2019/12/4 14:50
 */
public class Function {

    // 名称
    private String name;
    // 功能
    private int functionPosition;
    // 搜索高亮文字
    private SpannableString spannableStringName;

    public Function() {
    }

    public Function(String name, int functionPosition) {
        this.name = name;
        this.functionPosition = functionPosition;
    }

    public Function(SpannableString spannableStringName, int functionPosition) {
        this.spannableStringName = spannableStringName;
        this.functionPosition = functionPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFunctionPosition() {
        return functionPosition;
    }

    public void setFunctionPosition(int functionPosition) {
        this.functionPosition = functionPosition;
    }

    public SpannableString getSpannableStringName() {
        return spannableStringName;
    }

    public void setSpannableStringName(SpannableString spannableStringName) {
        this.spannableStringName = spannableStringName;
    }
}
