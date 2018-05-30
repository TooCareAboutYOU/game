package com.kachat.game.libdata.mvp.models;

import android.support.annotation.NonNull;

import com.kachat.game.libdata.ApiServices.UserApi;
import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;

import rx.Observer;
import rx.Subscription;

/**
 *
 */
public class ResetPwdModel extends BaseModel {

    private Subscription mSubscription;

    public void resetPwd(@NonNull String mobile, @NonNull String captcha, @NonNull String password,
                         final OnPresenterListener.OnModelListener<MessageBean> listener){
        mSubscription= UserApi.requestResetPwd(mobile, captcha, password, new Observer<BaseBean<MessageBean>>() {
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
            public void onNext(final BaseBean<MessageBean> bean) {
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
