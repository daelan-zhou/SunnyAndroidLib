package com.ikkong.adgo.entity;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/5/9
 * Description:
 */
public class IdNameArray {
    private String id[];
    private String name[];

    public String[] getId() {
        return id;
    }

    public void setId(String[] id) {
        this.id = id;
    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public IdNameArray(String[] id, String[] name) {
        this.id = id;
        this.name = name;
    }
}
