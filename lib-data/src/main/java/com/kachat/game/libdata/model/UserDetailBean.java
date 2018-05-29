package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 *
 */
public class UserDetailBean implements Serializable {

    private int level;
    private int hp;
    private int exp_to_level_up;
    private int exp;
    private int number;
    private int diamond;
    private int charm;

    public UserDetailBean() {
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getExp_to_level_up() {
        return exp_to_level_up;
    }

    public void setExp_to_level_up(int exp_to_level_up) {
        this.exp_to_level_up = exp_to_level_up;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public int getCharm() {
        return charm;
    }

    public void setCharm(int charm) {
        this.charm = charm;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
