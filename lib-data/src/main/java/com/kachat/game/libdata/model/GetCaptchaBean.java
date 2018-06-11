package com.kachat.game.libdata.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class GetCaptchaBean extends BaseBeans {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {

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
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"mobile\":\"")
                    .append(mobile).append('\"');
            sb.append(",\"expire\":")
                    .append(expire);
            sb.append(",\"captcha\":\"")
                    .append(captcha).append('\"');
            sb.append('}');
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"result\":")
                .append(result);
        sb.append("\"code\":")
                .append(getCode());
        sb.append(",\"error\":")
                .append(getError());
        sb.append('}');
        return sb.toString();
    }
}
