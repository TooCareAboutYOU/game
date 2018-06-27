package com.kachat.game.libdata.mvp.models;

import android.support.annotation.NonNull;

import com.kachat.game.libdata.apiServices.UserApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;

/**
 *
 */
public class ResetCaptchaModel extends BaseModel {
    private Subscription mSubscription;

    public void getResetCaptcha(@NonNull String mobile, final OnPresenterListeners.OnModelListener<BaseBean<GetCaptchaBean>> listener){

        mSubscription= UserApi.requestResetCaptcha(mobile,new Observer<BaseBean<GetCaptchaBean>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(final Throwable e) {
                LocalHandler().post(() -> {
                    if (listener != null) {
                        listener.onError(e);
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
