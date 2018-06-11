package com.kachat.game.libdata.model;

import java.io.Serializable;

/**
 *
 */
public abstract class BaseBeans implements Serializable {

    private int code;
    private ErrorBean error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":")
                .append(code);
        sb.append(",\"error\":")
                .append(error);
        sb.append('}');
        return sb.toString();
    }
}
