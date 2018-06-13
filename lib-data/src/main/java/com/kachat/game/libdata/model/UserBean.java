package com.kachat.game.libdata.model;

import java.io.Serializable;


public class UserBean extends BaseBeans {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {

        private String token;
        private UserInfoBean user;

        public ResultBean() {
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserInfoBean getUser() {
            return user;
        }

        public void setUser(UserInfoBean user) {
            this.user = user;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"token\":\"")
                    .append(token).append('\"');
            sb.append(",\"user\":")
                    .append(user.toString());
            sb.append('}');
            return sb.toString();
        }
    }
}
