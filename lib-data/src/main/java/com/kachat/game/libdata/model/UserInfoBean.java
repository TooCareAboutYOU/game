package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 *
 */
public class UserInfoBean implements Serializable {

    private String username;
    private String gender;
    private int uid;
    private int age;
    private UserDetailBean detail;

    public UserInfoBean() {
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

    public UserDetailBean getDetail() {
        return detail;
    }

    public void setDetail(UserDetailBean detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
