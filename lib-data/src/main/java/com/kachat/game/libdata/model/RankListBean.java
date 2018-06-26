package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.util.List;


public class RankListBean extends BaseBeans {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {

        private int count;
        private RankBean rank;
        private List<RanksBean> ranks;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public RankBean getRank() {
            return rank;
        }

        public void setRank(RankBean rank) {
            this.rank = rank;
        }

        public List<RanksBean> getRanks() {
            return ranks;
        }

        public void setRanks(List<RanksBean> ranks) {
            this.ranks = ranks;
        }

        public static class RankBean {

            private UserBean user;
            private int time;
            private int game;
            private int score;
            private int index;
            private String create_time;

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

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public static class UserBean {

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

                    private int charm;
                    private String number;
                    private int level;
                    private int exp;

                    public int getCharm() {
                        return charm;
                    }

                    public void setCharm(int charm) {
                        this.charm = charm;
                    }

                    public String getNumber() {
                        return number;
                    }

                    public void setNumber(String number) {
                        this.number = number;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }

                    public int getExp() {
                        return exp;
                    }

                    public void setExp(int exp) {
                        this.exp = exp;
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

        public static class RanksBean {

            private UserBeanX user;
            private int time;
            private int game;
            private int score;
            private int index;
            private String create_time;

            public UserBeanX getUser() {
                return user;
            }

            public void setUser(UserBeanX user) {
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

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public static class UserBeanX {
                private String username;
                private String gender;
                private int uid;
                private int age;
                private DetailBeanX detail;

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

                public DetailBeanX getDetail() {
                    return detail;
                }

                public void setDetail(DetailBeanX detail) {
                    this.detail = detail;
                }

                public static class DetailBeanX {

                    private int charm;
                    private String number;
                    private int level;
                    private int exp;

                    public int getCharm() {
                        return charm;
                    }

                    public void setCharm(int charm) {
                        this.charm = charm;
                    }

                    public String getNumber() {
                        return number;
                    }

                    public void setNumber(String number) {
                        this.number = number;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }

                    public int getExp() {
                        return exp;
                    }

                    public void setExp(int exp) {
                        this.exp = exp;
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
