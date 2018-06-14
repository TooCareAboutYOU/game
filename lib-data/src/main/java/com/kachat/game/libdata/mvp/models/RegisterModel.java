package com.kachat.game.libdata.mvp.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kachat.game.libdata.apiServices.UserApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;

public class RegisterModel extends BaseModel {

    private static final String TAG = "RegisterModel";

    private Subscription mSubscription;

    public void register(@NonNull String mobile, @NonNull String pwd, @NonNull String gender, @NonNull String age, @NonNull String username,
                      final OnPresenterListeners.OnModelListener<UserBean> listener){
        mSubscription= UserApi.requestRegister(mobile,pwd,gender,age,username,0, new Observer<UserBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(final Throwable e) {
//                LocalHandler().post(() -> {
                    if (listener != null) {
                        listener.onError(e);
                    }
//                });

            }

            @Override
            public void onNext(final UserBean result) {
//                LocalHandler().post(() -> {
                    if (listener != null) {
                        listener.onSuccess(result);
                    }
//                });
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
