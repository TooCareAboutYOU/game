package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 - diamond: 获取钻石数
 - gold: 获取金币数
 - hp: 获取体力值
 - exp: 获取经验
 */
public class SingsBean extends BaseBeans {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * diamond : 600
         * exp : 50
         * gold : 100
         * hp : 100
         */

        private int diamond;
        private int exp;
        private int gold;
        private int hp;

        public int getDiamond() {
            return diamond;
        }

        public void setDiamond(int diamond) {
            this.diamond = diamond;
        }

        public int getExp() {
            return exp;
        }

        public void setExp(int exp) {
            this.exp = exp;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public int getHp() {
            return hp;
        }

        public void setHp(int hp) {
            this.hp = hp;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
