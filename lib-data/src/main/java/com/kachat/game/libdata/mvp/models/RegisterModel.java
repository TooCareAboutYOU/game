package com.kachat.game.libdata.mvp.models;

import android.support.annotation.NonNull;

import com.kachat.game.libdata.ApiServices.UserApi;
import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;

import rx.Observer;
import rx.Subscription;

public class RegisterModel extends BaseModel {

    private Subscription mSubscription;

    public void register(@NonNull String mobile, @NonNull String pwd, @NonNull String gender, @NonNull String age, @NonNull String username,
                      final OnPresenterListener.OnModelListener<UserBean> listener){
        mSubscription= UserApi.requestRegister(mobile,pwd,gender,age,username,0, new Observer<BaseBean<UserBean>>() {
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
            public void onNext(final BaseBean<UserBean> bean) {
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
