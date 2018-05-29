package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 *
 */
public class MessageBean implements Serializable {
    private String message;

    public MessageBean() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
