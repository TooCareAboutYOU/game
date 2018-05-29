package com.kachat.game.libdata.mvp.models;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.kachat.game.libdata.ApiServices.UserApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;

import rx.Observer;
import rx.Subscription;

/**
 *
 */
public class CaptchaModel extends BaseModel {
    private Subscription mSubscription;

    public void getCaptcha(@NonNull String mobile, final OnPresenterListener.OnModelListener<BaseBean<GetCaptchaBean>> listener){

        mSubscription= UserApi.requestCaptcha(mobile,new Observer<BaseBean<GetCaptchaBean>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(final Throwable e) {
                LocalHandler().post(() -> {
                    if (listener != null) {
                        listener.onFailed(e);
                    }
                });

            }

            @Override
            public void onNext(final BaseBean<GetCaptchaBean> bean) {
                LocalHandler().post(() -> {
                if (listener != null) {
                    listener.onSuccess(bean);
                }
                });
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
