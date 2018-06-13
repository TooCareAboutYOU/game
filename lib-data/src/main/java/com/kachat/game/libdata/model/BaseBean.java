package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 *
 */
public class BaseBean<T> implements Serializable {

    private int code;
    private T result;
    private ErrorBean error;

    public int getCode() {
        return code;
    }

    public T getResult() {
        return result;
    }

    public ErrorBean getError() {
        return error;
    }


}
