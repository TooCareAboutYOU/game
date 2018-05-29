package com.kachat.game.libdata.mvp.models;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.kachat.game.libdata.ApiServices.UserApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;

import rx.Observer;
import rx.Subscription;

/**
 *
 */
public class LoginModel extends BaseModel {

    private Subscription mSubscription;

    public void login(@NonNull String mobile,@NonNull String pwd, final OnPresenterListener.OnModelListener<BaseBean<UserBean>> listener){
        mSubscription= UserApi.requestLogin(mobile, pwd, new Observer<BaseBean<UserBean>>() {
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
            public void onNext(final BaseBean<UserBean> userBeanBaseBean) {
                LocalHandler().post(() -> {
                    if (listener != null) {
                        listener.onSuccess(userBeanBaseBean);
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
