package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class GameRankBean implements Serializable{

        private int count;
        private List<RanksBean> ranks;

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<RanksBean> getRanks() {
            return ranks;
        }

        public void setRanks(List<RanksBean> ranks) {
            this.ranks = ranks;
        }

        public static class RanksBean implements Serializable{
            /**
             * user : {"username":"吕小布","gender":"male","uid":236,"age":33,"detail":{"level":3,"number":"2a6c1a","exp":860,"charm":42}}
             * time : 0
             * game : 901
             * score : 30760
             * create_time : 2018-06-10T13:27:24.619148
             */

            private UserBean user;
            private int time;
            private int game;
            private int score;
            private String create_time;

            @Override
            public String toString() {
                return JSON.toJSONString(this);
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public int getGame() {
                return game;
            }

            public void setGame(int game) {
                this.game = game;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public static class UserBean implements Serializable{
                /**
                 * username : 吕小布
                 * gender : male
                 * uid : 236
                 * age : 33
                 * detail : {"level":3,"number":"2a6c1a","exp":860,"charm":42}
                 */

                private String username;
                private String gender;
                private int uid;
                private int age;
                private DetailBean detail;

                @Override
                public String toString() {
                    return JSON.toJSONString(this);
                }

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

                public static class DetailBean implements Serializable{
                    /**
                     * level : 3
                     * number : 2a6c1a
                     * exp : 860
                     * charm : 42
                     */

                    private int level;
                    private String number;
                    private int exp;
                    private int charm;

                    @Override
                    public String toString() {
                        return JSON.toJSONString(this);
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }

                    public String getNumber() {
                        return number;
                    }

                    public void setNumber(String number) {
                        this.number = number;
                    }

                    public int getExp() {
                        return exp;
                    }

                    public void setExp(int exp) {
                        this.exp = exp;
                    }

                    public int getCharm() {
                        return charm;
                    }

                    public void setCharm(int charm) {
                        this.charm = charm;
                    }
                }
            }
        }

}
