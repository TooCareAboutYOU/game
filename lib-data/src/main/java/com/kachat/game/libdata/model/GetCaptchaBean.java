package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 *
 */
public class GetCaptchaBean implements Serializable {

    private String captcha;
    private String mobile;
    private String expire;

    public GetCaptchaBean() {
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
