package com.kachat.game.utils;

/**
 *
 */
public class CalculateException extends Exception {

    public CalculateException() {
    }

    public CalculateException(String text) {
        super(text);
    }

    public CalculateException(Throwable throwable) {
        super(throwable);
    }

    public CalculateException(String text,Throwable throwable) {
        super(text,throwable);
    }

}
