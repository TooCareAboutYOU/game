package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class RankingListBean extends BaseBeans {

    /**
     * result : {"ranks":[{"username":"test1","gender":"male","uid":16,"age":20,"detail":{"exp":4090,"charm":225,"level":8,"number":"14e8e7"}},{"username":"test","gender":"male","uid":17,"age":20,"detail":{"exp":3380,"charm":169,"level":7,"number":"97aafe"}},{"username":"小红","gender":"female","uid":22,"age":31,"detail":{"exp":1873,"charm":168,"level":5,"number":"a8f7f5"}},{"username":"小丽","gender":"female","uid":15,"age":19,"detail":{"exp":1380,"charm":152,"level":4,"number":"3b11d8"}},{"username":"rick","gender":"male","uid":289,"age":35,"detail":{"exp":1240,"charm":73,"level":4,"number":"901eed"}},{"username":"小明","gender":"male","uid":13,"age":18,"detail":{"exp":1320,"charm":55,"level":4,"number":"262936"}},{"username":"lg","gender":"male","uid":10,"age":0,"detail":{"exp":650,"charm":48,"level":3,"number":"658d29"}},{"username":"快了","gender":"male","uid":9,"age":0,"detail":{"exp":860,"charm":47,"level":3,"number":"31fe42"}},{"username":"吕小布","gender":"male","uid":236,"age":33,"detail":{"exp":860,"charm":42,"level":3,"number":"2a6c1a"}},{"username":"zhangjianjun","gender":"male","uid":3,"age":20,"detail":{"exp":760,"charm":34,"level":3,"number":"97c1da"}},{"username":"zhangyin","gender":"male","uid":290,"age":24,"detail":{"exp":290,"charm":28,"level":2,"number":"ae8021"}},{"username":"eeerrr","gender":"male","uid":241,"age":18,"detail":{"exp":410,"charm":25,"level":2,"number":"75fc5e"}},{"username":"wee","gender":"female","uid":287,"age":19,"detail":{"exp":600,"charm":24,"level":3,"number":"7ee693"}},{"username":"pear","gender":"female","uid":237,"age":25,"detail":{"exp":270,"charm":21,"level":2,"number":"c63bb4"}},{"username":"haha","gender":"male","uid":288,"age":27,"detail":{"exp":310,"charm":20,"level":2,"number":"d98270"}},{"username":"Xxxholic","gender":"female","uid":268,"age":18,"detail":{"exp":80,"charm":9,"level":1,"number":"3c2919"}},{"username":"小林是个好青年","gender":"male","uid":251,"age":29,"detail":{"exp":340,"charm":9,"level":2,"number":"b886f6"}},{"username":"在路上","gender":"female","uid":239,"age":28,"detail":{"exp":270,"charm":8,"level":2,"number":"bcb98c"}},{"username":"momoko","gender":"female","uid":279,"age":28,"detail":{"exp":160,"charm":8,"level":1,"number":"f1ad40"}},{"username":"好看吗","gender":"male","uid":233,"age":30,"detail":{"exp":120,"charm":6,"level":1,"number":"8a6cb8"}}],"count":20}
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
         * ranks : [{"username":"test1","gender":"male","uid":16,"age":20,"detail":{"exp":4090,"charm":225,"level":8,"number":"14e8e7"}},{"username":"test","gender":"male","uid":17,"age":20,"detail":{"exp":3380,"charm":169,"level":7,"number":"97aafe"}},{"username":"小红","gender":"female","uid":22,"age":31,"detail":{"exp":1873,"charm":168,"level":5,"number":"a8f7f5"}},{"username":"小丽","gender":"female","uid":15,"age":19,"detail":{"exp":1380,"charm":152,"level":4,"number":"3b11d8"}},{"username":"rick","gender":"male","uid":289,"age":35,"detail":{"exp":1240,"charm":73,"level":4,"number":"901eed"}},{"username":"小明","gender":"male","uid":13,"age":18,"detail":{"exp":1320,"charm":55,"level":4,"number":"262936"}},{"username":"lg","gender":"male","uid":10,"age":0,"detail":{"exp":650,"charm":48,"level":3,"number":"658d29"}},{"username":"快了","gender":"male","uid":9,"age":0,"detail":{"exp":860,"charm":47,"level":3,"number":"31fe42"}},{"username":"吕小布","gender":"male","uid":236,"age":33,"detail":{"exp":860,"charm":42,"level":3,"number":"2a6c1a"}},{"username":"zhangjianjun","gender":"male","uid":3,"age":20,"detail":{"exp":760,"charm":34,"level":3,"number":"97c1da"}},{"username":"zhangyin","gender":"male","uid":290,"age":24,"detail":{"exp":290,"charm":28,"level":2,"number":"ae8021"}},{"username":"eeerrr","gender":"male","uid":241,"age":18,"detail":{"exp":410,"charm":25,"level":2,"number":"75fc5e"}},{"username":"wee","gender":"female","uid":287,"age":19,"detail":{"exp":600,"charm":24,"level":3,"number":"7ee693"}},{"username":"pear","gender":"female","uid":237,"age":25,"detail":{"exp":270,"charm":21,"level":2,"number":"c63bb4"}},{"username":"haha","gender":"male","uid":288,"age":27,"detail":{"exp":310,"charm":20,"level":2,"number":"d98270"}},{"username":"Xxxholic","gender":"female","uid":268,"age":18,"detail":{"exp":80,"charm":9,"level":1,"number":"3c2919"}},{"username":"小林是个好青年","gender":"male","uid":251,"age":29,"detail":{"exp":340,"charm":9,"level":2,"number":"b886f6"}},{"username":"在路上","gender":"female","uid":239,"age":28,"detail":{"exp":270,"charm":8,"level":2,"number":"bcb98c"}},{"username":"momoko","gender":"female","uid":279,"age":28,"detail":{"exp":160,"charm":8,"level":1,"number":"f1ad40"}},{"username":"好看吗","gender":"male","uid":233,"age":30,"detail":{"exp":120,"charm":6,"level":1,"number":"8a6cb8"}}]
         * count : 20
         */

        private int count;
        private List<RanksBean> ranks;

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
             * username : test1
             * gender : male
             * uid : 16
             * age : 20
             * detail : {"exp":4090,"charm":225,"level":8,"number":"14e8e7"}
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
