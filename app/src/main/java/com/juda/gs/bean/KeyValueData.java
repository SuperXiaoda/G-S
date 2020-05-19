package com.juda.gs.bean;

/**
 * @Description:
 * @author: CodingDa
 * @Date : 2020/5/7 15:12
 */
public class KeyValueData {

    // id
    private String id;
    // 名称
    private String name;
    // 是否选中
    private boolean isCheck;

    public KeyValueData() {
    }

    public KeyValueData(String id, String name, boolean isCheck) {
        this.id = id;
        this.name = name;
        this.isCheck = isCheck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
