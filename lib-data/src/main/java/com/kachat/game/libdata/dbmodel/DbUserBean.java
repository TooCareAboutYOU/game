package com.kachat.game.libdata.dbmodel;

import com.alibaba.fastjson.JSON;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 *
 */
@Entity
public class DbUserBean {
    @Id
    private Long id;
    private String token;
    private String mobile;
    private String username;
    private String gender;
    private int uid;
    private int age;
    private int system;
    private int level;
    private int hp;
    private int exp_to_level_up;
    private int exp;
    private String number;
    private int diamond;
    private int charm;
    private int gold;

    public DbUserBean(String mobile) {
        this.mobile = mobile;
    }

    

    @Generated(hash = 669936410)
    public DbUserBean() {
    }



    @Generated(hash = 1211944498)
    public DbUserBean(Long id, String token, String mobile, String username,
            String gender, int uid, int age, int system, int level, int hp,
            int exp_to_level_up, int exp, String number, int diamond, int charm,
            int gold) {
        this.id = id;
        this.token = token;
        this.mobile = mobile;
        this.username = username;
        this.gender = gender;
        this.uid = uid;
        this.age = age;
        this.system = system;
        this.level = level;
        this.hp = hp;
        this.exp_to_level_up = exp_to_level_up;
        this.exp = exp;
        this.number = number;
        this.diamond = diamond;
        this.charm = charm;
        this.gold = gold;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getMobile() {
        return this.mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public int getUid() {
        return this.uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getSystem() {
        return this.system;
    }
    public void setSystem(int system) {
        this.system = system;
    }
    public int getLevel() {
        return this.level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getHp() {
        return this.hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public int getExp_to_level_up() {
        return this.exp_to_level_up;
    }
    public void setExp_to_level_up(int exp_to_level_up) {
        this.exp_to_level_up = exp_to_level_up;
    }
    public int getExp() {
        return this.exp;
    }
    public void setExp(int exp) {
        this.exp = exp;
    }
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public int getDiamond() {
        return this.diamond;
    }
    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }
    public int getCharm() {
        return this.charm;
    }
    public void setCharm(int charm) {
        this.charm = charm;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGold() {
        return this.gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
