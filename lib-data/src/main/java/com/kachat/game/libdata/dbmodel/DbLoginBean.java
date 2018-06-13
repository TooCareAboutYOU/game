package com.kachat.game.libdata.dbmodel;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DbLoginBean {
    @Id
    private Long id;
    private boolean isLogin;
    @Generated(hash = 84309276)
    public DbLoginBean(Long id, boolean isLogin) {
        this.id = id;
        this.isLogin = isLogin;
    }
    @Generated(hash = 929309223)
    public DbLoginBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean getIsLogin() {
        return this.isLogin;
    }
    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
