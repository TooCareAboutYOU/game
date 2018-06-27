package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;


public class UserBean implements Serializable {

    public UserBean() {
    }

    public UserBean(String token, UserInfoBean user) {
        this.token = token;
        this.user = user;
    }

    private String token;
    private UserInfoBean user;

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
