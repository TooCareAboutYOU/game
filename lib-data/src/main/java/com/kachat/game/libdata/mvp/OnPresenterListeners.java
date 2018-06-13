package com.kachat.game.libdata.mvp;


import com.kachat.game.libdata.model.ErrorBean;

public interface OnPresenterListeners {

    interface OnModelListener<T> {
        void onSuccess(T result);
        void onError(Throwable e);
    }

    interface OnViewListener<T>{
        void onSuccess(T result);
        void onFailed(int errorCode, ErrorBean error);
        void onError(Throwable e);
    }

}
