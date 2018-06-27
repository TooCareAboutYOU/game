package com.as.main.tes_module;


import com.kachat.game.libdata.model.ErrorBean;

public interface OnPresenterListenersTest {

    interface OnModelListenerTest<T> {
        void onSuccess(T result);
        void onError(Throwable e);
    }

    interface OnViewListenerTest<T>{
        void onSuccess(T result);
        void onFailed(int errorCode, ErrorBeanTest error);
        void onError(Throwable e);
    }

}
