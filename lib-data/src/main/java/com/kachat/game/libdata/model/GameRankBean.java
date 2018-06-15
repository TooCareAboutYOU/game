package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class GameRankBean extends BaseBeans {

    /**
     * result : {"ranks":[{"user":{"username":"吕小布","gender":"male","uid":236,"age":33,"detail":{"level":3,"number":"2a6c1a","exp":860,"charm":42}},"time":0,"game":901,"score":30760,"create_time":"2018-06-10T13:27:24.619148"},{"user":{"username":"zhangjianjun","gender":"male","uid":3,"age":20,"detail":{"level":3,"number":"97c1da","exp":760,"charm":34}},"time":0,"game":901,"score":28175,"create_time":"2018-06-12T13:55:55.003137"},{"user":{"username":"pear","gender":"female","uid":237,"age":25,"detail":{"level":2,"number":"c63bb4","exp":270,"charm":21}},"time":0,"game":901,"score":28095,"create_time":"2018-06-10T22:44:47.334120"},{"user":{"username":"吕小布","gender":"male","uid":236,"age":33,"detail":{"level":3,"number":"2a6c1a","exp":860,"charm":42}},"time":0,"game":901,"score":25440,"create_time":"2018-06-10T10:55:15.883685"},{"user":{"username":"小丽","gender":"female","uid":15,"age":19,"detail":{"level":4,"number":"3b11d8","exp":1380,"charm":152}},"time":0,"game":901,"score":25120,"create_time":"2018-06-10T13:27:26.186192"},{"user":{"username":"在路上","gender":"female","uid":239,"age":28,"detail":{"level":2,"number":"bcb98c","exp":270,"charm":8}},"time":0,"game":901,"score":23565,"create_time":"2018-06-08T21:46:23.740182"},{"user":{"username":"pear","gender":"female","uid":237,"age":25,"detail":{"level":2,"number":"c63bb4","exp":270,"charm":21}},"time":0,"game":901,"score":20575,"create_time":"2018-06-14T20:45:20.406693"},{"user":{"username":"小林是个好青年","gender":"male","uid":251,"age":29,"detail":{"level":2,"number":"b886f6","exp":340,"charm":9}},"time":0,"game":901,"score":20430,"create_time":"2018-06-10T22:44:48.642523"},{"user":{"username":"小丽","gender":"female","uid":15,"age":19,"detail":{"level":4,"number":"3b11d8","exp":1380,"charm":152}},"time":0,"game":901,"score":20150,"create_time":"2018-06-07T16:39:22.907095"},{"user":{"username":"小明","gender":"male","uid":13,"age":18,"detail":{"level":4,"number":"262936","exp":1320,"charm":55}},"time":0,"game":901,"score":19895,"create_time":"2018-06-12T13:55:03.565185"},{"user":{"username":"Kitty","gender":"female","uid":254,"age":29,"detail":{"level":1,"number":"73efbe","exp":140,"charm":2}},"time":0,"game":901,"score":18415,"create_time":"2018-06-14T20:45:19.834810"},{"user":{"username":"pear","gender":"female","uid":237,"age":25,"detail":{"level":2,"number":"c63bb4","exp":270,"charm":21}},"time":0,"game":901,"score":18330,"create_time":"2018-06-10T23:29:27.429439"},{"user":{"username":"pear","gender":"female","uid":237,"age":25,"detail":{"level":2,"number":"c63bb4","exp":270,"charm":21}},"time":0,"game":901,"score":18130,"create_time":"2018-06-11T15:55:46.764418"},{"user":{"username":"eeerrr","gender":"male","uid":241,"age":18,"detail":{"level":2,"number":"75fc5e","exp":410,"charm":25}},"time":0,"game":901,"score":18005,"create_time":"2018-06-10T10:52:55.689360"},{"user":{"username":"小红","gender":"female","uid":22,"age":31,"detail":{"level":5,"number":"a8f7f5","exp":1873,"charm":168}},"time":0,"game":901,"score":16670,"create_time":"2018-06-13T12:21:45.328138"},{"user":{"username":"轻如风","gender":"male","uid":244,"age":18,"detail":{"level":1,"number":"9f3512","exp":60,"charm":3}},"time":0,"game":901,"score":16075,"create_time":"2018-06-08T21:53:54.704416"},{"user":{"username":"快了","gender":"male","uid":9,"age":0,"detail":{"level":3,"number":"31fe42","exp":860,"charm":47}},"time":0,"game":901,"score":16030,"create_time":"2018-06-08T21:44:56.415772"},{"user":{"username":"快了","gender":"male","uid":9,"age":0,"detail":{"level":3,"number":"31fe42","exp":860,"charm":47}},"time":0,"game":901,"score":16020,"create_time":"2018-06-11T15:22:00.270626"},{"user":{"username":"rick","gender":"male","uid":289,"age":35,"detail":{"level":4,"number":"901eed","exp":1290,"charm":73}},"time":0,"game":901,"score":15770,"create_time":"2018-06-13T23:18:45.909299"},{"user":{"username":"小林是个好青年","gender":"male","uid":251,"age":29,"detail":{"level":2,"number":"b886f6","exp":340,"charm":9}},"time":0,"game":901,"score":15440,"create_time":"2018-06-10T23:29:30.006391"}],"count":20}
     */

    private ResultBean result;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * ranks : [{"user":{"username":"吕小布","gender":"male","uid":236,"age":33,"detail":{"level":3,"number":"2a6c1a","exp":860,"charm":42}},"time":0,"game":901,"score":30760,"create_time":"2018-06-10T13:27:24.619148"},{"user":{"username":"zhangjianjun","gender":"male","uid":3,"age":20,"detail":{"level":3,"number":"97c1da","exp":760,"charm":34}},"time":0,"game":901,"score":28175,"create_time":"2018-06-12T13:55:55.003137"},{"user":{"username":"pear","gender":"female","uid":237,"age":25,"detail":{"level":2,"number":"c63bb4","exp":270,"charm":21}},"time":0,"game":901,"score":28095,"create_time":"2018-06-10T22:44:47.334120"},{"user":{"username":"吕小布","gender":"male","uid":236,"age":33,"detail":{"level":3,"number":"2a6c1a","exp":860,"charm":42}},"time":0,"game":901,"score":25440,"create_time":"2018-06-10T10:55:15.883685"},{"user":{"username":"小丽","gender":"female","uid":15,"age":19,"detail":{"level":4,"number":"3b11d8","exp":1380,"charm":152}},"time":0,"game":901,"score":25120,"create_time":"2018-06-10T13:27:26.186192"},{"user":{"username":"在路上","gender":"female","uid":239,"age":28,"detail":{"level":2,"number":"bcb98c","exp":270,"charm":8}},"time":0,"game":901,"score":23565,"create_time":"2018-06-08T21:46:23.740182"},{"user":{"username":"pear","gender":"female","uid":237,"age":25,"detail":{"level":2,"number":"c63bb4","exp":270,"charm":21}},"time":0,"game":901,"score":20575,"create_time":"2018-06-14T20:45:20.406693"},{"user":{"username":"小林是个好青年","gender":"male","uid":251,"age":29,"detail":{"level":2,"number":"b886f6","exp":340,"charm":9}},"time":0,"game":901,"score":20430,"create_time":"2018-06-10T22:44:48.642523"},{"user":{"username":"小丽","gender":"female","uid":15,"age":19,"detail":{"level":4,"number":"3b11d8","exp":1380,"charm":152}},"time":0,"game":901,"score":20150,"create_time":"2018-06-07T16:39:22.907095"},{"user":{"username":"小明","gender":"male","uid":13,"age":18,"detail":{"level":4,"number":"262936","exp":1320,"charm":55}},"time":0,"game":901,"score":19895,"create_time":"2018-06-12T13:55:03.565185"},{"user":{"username":"Kitty","gender":"female","uid":254,"age":29,"detail":{"level":1,"number":"73efbe","exp":140,"charm":2}},"time":0,"game":901,"score":18415,"create_time":"2018-06-14T20:45:19.834810"},{"user":{"username":"pear","gender":"female","uid":237,"age":25,"detail":{"level":2,"number":"c63bb4","exp":270,"charm":21}},"time":0,"game":901,"score":18330,"create_time":"2018-06-10T23:29:27.429439"},{"user":{"username":"pear","gender":"female","uid":237,"age":25,"detail":{"level":2,"number":"c63bb4","exp":270,"charm":21}},"time":0,"game":901,"score":18130,"create_time":"2018-06-11T15:55:46.764418"},{"user":{"username":"eeerrr","gender":"male","uid":241,"age":18,"detail":{"level":2,"number":"75fc5e","exp":410,"charm":25}},"time":0,"game":901,"score":18005,"create_time":"2018-06-10T10:52:55.689360"},{"user":{"username":"小红","gender":"female","uid":22,"age":31,"detail":{"level":5,"number":"a8f7f5","exp":1873,"charm":168}},"time":0,"game":901,"score":16670,"create_time":"2018-06-13T12:21:45.328138"},{"user":{"username":"轻如风","gender":"male","uid":244,"age":18,"detail":{"level":1,"number":"9f3512","exp":60,"charm":3}},"time":0,"game":901,"score":16075,"create_time":"2018-06-08T21:53:54.704416"},{"user":{"username":"快了","gender":"male","uid":9,"age":0,"detail":{"level":3,"number":"31fe42","exp":860,"charm":47}},"time":0,"game":901,"score":16030,"create_time":"2018-06-08T21:44:56.415772"},{"user":{"username":"快了","gender":"male","uid":9,"age":0,"detail":{"level":3,"number":"31fe42","exp":860,"charm":47}},"time":0,"game":901,"score":16020,"create_time":"2018-06-11T15:22:00.270626"},{"user":{"username":"rick","gender":"male","uid":289,"age":35,"detail":{"level":4,"number":"901eed","exp":1290,"charm":73}},"time":0,"game":901,"score":15770,"create_time":"2018-06-13T23:18:45.909299"},{"user":{"username":"小林是个好青年","gender":"male","uid":251,"age":29,"detail":{"level":2,"number":"b886f6","exp":340,"charm":9}},"time":0,"game":901,"score":15440,"create_time":"2018-06-10T23:29:30.006391"}]
         * count : 20
         */

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
}
