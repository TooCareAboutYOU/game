package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 *
 */
public class UpdateUserData extends BaseBeans {


    /**
     * result : {"username":"张帅","gender":"female","uid":19,"age":0,"detail":{"gold":400,"hp":100,"exp":0,"number":"fbf23a","exp_to_level_up":200,"diamond":9960,"charm":0,"level":1}}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * username : 张帅
         * gender : female
         * uid : 19
         * age : 0
         * detail : {"gold":400,"hp":100,"exp":0,"number":"fbf23a","exp_to_level_up":200,"diamond":9960,"charm":0,"level":1}
         */

        private String username;
        private String gender;
        private int uid;
        private int age;
        private DetailBean detail;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public static class DetailBean {
            /**
             * gold : 400
             * hp : 100
             * exp : 0
             * number : fbf23a
             * exp_to_level_up : 200
             * diamond : 9960
             * charm : 0
             * level : 1
             */

            private int gold;
            private int hp;
            private int exp;
            private String number;
            private int exp_to_level_up;
            private int diamond;
            private int charm;
            private int level;

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

            public int getExp_to_level_up() {
                return exp_to_level_up;
            }

            public void setExp_to_level_up(int exp_to_level_up) {
                this.exp_to_level_up = exp_to_level_up;
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

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


}
