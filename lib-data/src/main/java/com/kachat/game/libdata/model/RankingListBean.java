package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class RankingListBean implements Serializable {


        private int count;
        private List<RanksBean> ranks;
        private RankBean rank;

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

        public RankBean getRank() {
            return rank;
        }

        public void setRank(RankBean rank) {
            this.rank = rank;
        }

        public static class RanksBean implements Serializable{

            private String username;
            private String gender;
            private int uid;
            private int age;
            private int index;
            private DetailBean user_detail;

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

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public DetailBean getUser_detail() {
                return user_detail;
            }

            public void setUser_detail(DetailBean user_detail) {
                this.user_detail = user_detail;
            }

            public static class DetailBean implements Serializable{
                /**
                 * exp : 4090
                 * charm : 225
                 * level : 8
                 * number : 14e8e7
                 */

                private int exp;
                private int charm;
                private int level;
                private String number;

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

        public static class RankBean implements Serializable{

            private String username;
            private String gender;
            private int uid;
            private int age;
            private int index;
            private DetailBean user_detail;

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

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public DetailBean getUser_detail() {
                return user_detail;
            }

            public void setUser_detail(DetailBean user_detail) {
                this.user_detail = user_detail;
            }

            public static class DetailBean implements Serializable{
                /**
                 * exp : 4090
                 * charm : 225
                 * level : 8
                 * number : 14e8e7
                 */

                private int exp;
                private int charm;
                private int level;
                private String number;

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
