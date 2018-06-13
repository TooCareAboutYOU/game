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
    private int system;
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

    public int getSystem() {
        return system;
    }

    public void setSystem(int system) {
        this.system = system;
    }

    public UserDetailBean getDetail() {
        return detail;
    }

    public void setDetail(UserDetailBean detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"username\":\"")
                .append(username).append('\"');
        sb.append(",\"gender\":\"")
                .append(gender).append('\"');
        sb.append(",\"uid\":")
                .append(uid);
        sb.append(",\"age\":")
                .append(age);
        sb.append(",\"system\":")
                .append(system);
        sb.append(",\"detail\":")
                .append(detail);
        sb.append('}');
        return sb.toString();
    }
}
