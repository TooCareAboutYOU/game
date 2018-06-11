package com.kachat.game.libdata.model;


import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class ErrorBean implements Serializable {

    private List<String> message;
    private String toast;

    public ErrorBean() {
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getToast() {
        return toast;
    }

    public void setToast(String toast) {
        this.toast = toast;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"message\":")
                .append(message);
        sb.append(",\"toast\":\"")
                .append(toast).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
