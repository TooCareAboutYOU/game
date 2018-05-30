package com.kachat.game.libdata.mvp;


import com.kachat.game.libdata.model.BaseBean;

public interface OnPresenterListener {

    interface OnModelListener<T> {
        void onSuccess(BaseBean<T> result);
        void onError(Throwable throwable);
    }

    interface OnViewListener<T>{
        void onSuccess(BaseBean<T> result);
        void onFailed(BaseBean<T> result);
        void onError(Throwable throwable);
    }

}
