package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;


public class UserBean extends BaseBeans {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {

        private String token;
        private UserInfoBean user;

        public ResultBean() {
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserInfoBean getUser() {
            return user;
        }

        public void setUser(UserInfoBean user) {
            this.user = user;
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
