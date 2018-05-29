package com.kachat.game.libdata.model;

import java.io.Serializable;


public class UserBean implements Serializable {

    private String token;
    private UserInfoBean user;

    public UserBean() {
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
}
