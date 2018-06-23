package com.kachat.game.model;

import com.alibaba.fastjson.JSON;


public class Live2DModel {

    private String name;
    private int img;
    private int ive_chip_number;  //拥有该遮罩碎片个数
    private int unlock_chip;  // 解锁遮罩需要的碎片数量
    private boolean isFlag;  //是否选中
    private boolean isClocked; //是否解锁



    public Live2DModel(String name, int img, boolean isFlag,boolean isClocked,int have, int total) {
        this.name = name;
        this.img = img;
        this.isFlag = isFlag;
        this.isClocked=isClocked;
        this.ive_chip_number=have;
        this.unlock_chip=total;
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

    public boolean isClocked() {
        return isClocked;
    }

    public void setClocked(boolean clocked) {
        isClocked = clocked;
    }

    public int getIve_chip_number() {
        return ive_chip_number;
    }

    public void setIve_chip_number(int ive_chip_number) {
        this.ive_chip_number = ive_chip_number;
    }

    public int getUnlock_chip() {
        return unlock_chip;
    }

    public void setUnlock_chip(int unlock_chip) {
        this.unlock_chip = unlock_chip;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
