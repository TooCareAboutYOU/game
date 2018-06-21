package com.kachat.game.model;

import com.alibaba.fastjson.JSON;


public class Live2DModel {
    private String name;
    private int img;
    private boolean isFlag;


    public Live2DModel() {
    }


    public Live2DModel(String name, int img, boolean isFlag) {
        this.name = name;
        this.img = img;
        this.isFlag = isFlag;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
