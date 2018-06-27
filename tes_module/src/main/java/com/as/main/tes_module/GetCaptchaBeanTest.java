package com.as.main.tes_module;

import com.alibaba.fastjson.JSON;
import com.kachat.game.libdata.model.BaseBeans;

import java.io.Serializable;

/**
 *
 */
public class GetCaptchaBeanTest implements Serializable{

    private String mobile;
    private int expire;
    private String captcha;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
