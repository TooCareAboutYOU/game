package com.kachat.game.libdata.model;

import java.io.Serializable;

/**
 *
 */
public class PostFileBean extends BaseBeans {


    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {

    }
}
