package com.kachat.game.libdata.model;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class TestBean {
    private String name;

    @Generated(hash = 1303329926)
    public TestBean(String name) {
        this.name = name;
    }

    @Generated(hash = 2087637710)
    public TestBean() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
