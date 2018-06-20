package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
     level: 等级
     hp: 体力值
     exp_to_level_up: 升级还需经验
     exp: 经验
     number: 唯一游戏号，用于添加好友
     diamond: 钻石
     charm: 魅力值
     gold: 金币数
 */
public class UserDetailBean implements Serializable {

    private int level; // 用户更新不包含在此字段

    private int hp;
    private int exp_to_level_up;
    private int exp;
    private String number;
    private int diamond;
    private int charm;
    private int gold;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
