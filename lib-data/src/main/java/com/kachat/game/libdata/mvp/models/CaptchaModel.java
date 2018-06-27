package com.kachat.game.libdata.mvp.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kachat.game.libdata.apiServices.UserApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.orhanobut.logger.Logger;

import rx.Observer;
import rx.Subscription;


public class CaptchaModel extends BaseModel {

    private static final String TAG = "CaptchaModel";

    private Subscription mSubscription;

    public void getCaptcha(@NonNull String mobile, final OnPresenterListeners.OnModelListener<BaseBean<GetCaptchaBean>> listener){
        mSubscription= UserApi.requestCaptcha(mobile,new Observer<BaseBean<GetCaptchaBean>>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(final Throwable e) {
                if (listener != null) { listener.onError(e);  }
            }

            @Override
            public void onNext(final BaseBean<GetCaptchaBean> bean) {
                if (listener != null) { listener.onSuccess(bean); }
            }
        });

        if (mSubscription != null) {
            addCompositeSubscription(mSubscription);
        }
    }


    public void close(){
        if (mSubscription != null) {
            delCompositeSubscription();
            mSubscription=null;
        }
    }

}
