package com.as.main.tes_module;


import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class ErrorBeanTest implements Serializable {

    private String toast;
    private List<String> message;

    public String getToast() {
        return toast;
    }

    public void setToast(String toast) {
        this.toast = toast;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
