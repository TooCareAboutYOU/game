package com.as.main.tes_module;

import com.alibaba.fastjson.JSON;
import com.kachat.game.libdata.model.ErrorBean;

import java.io.Serializable;

/**
 *
 */
public class BaseBean<T> implements Serializable {

    private int code;
    private T result;
    private ErrorBeanTest error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ErrorBeanTest getError() {
        return error;
    }
    public void setError(ErrorBeanTest error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}