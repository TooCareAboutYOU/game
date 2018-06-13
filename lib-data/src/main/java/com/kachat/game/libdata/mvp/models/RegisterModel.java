package com.kachat.game.libdata.mvp.models;

import android.support.annotation.NonNull;

import com.kachat.game.libdata.apiServices.UserApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;

public class RegisterModel extends BaseModel {

    private Subscription mSubscription;

    public void register(@NonNull String mobile, @NonNull String pwd, @NonNull String gender, @NonNull String age, @NonNull String username,
                      final OnPresenterListeners.OnModelListener<UserBean> listener){
        mSubscription= UserApi.requestRegister(mobile,pwd,gender,age,username,0, new Observer<UserBean>() {
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
            public void onNext(final UserBean result) {
                LocalHandler().post(() -> {
                    if (listener != null) {
                        listener.onSuccess(result);
//                        if (result.getResult() != null) {
//                            if (result.getResult().getUser().getDetail() != null) {
//                                DaoUpdate.updateUser(result.getResult().getToken(),
//                                        result.getResult().getUser().getUsername(),
//                                        result.getResult().getUser().getGender(),
//                                        result.getResult().getUser().getUid(),
//                                        result.getResult().getUser().getAge(),
//                                        result.getResult().getUser().getSystem(),
//                                        result.getResult().getUser().getDetail().getLevel(),
//                                        result.getResult().getUser().getDetail().getHp(),
//                                        result.getResult().getUser().getDetail().getExp_to_level_up(),
//                                        result.getResult().getUser().getDetail().getExp(),
//                                        result.getResult().getUser().getDetail().getNumber(),
//                                        result.getResult().getUser().getDetail().getDiamond(),
//                                        result.getResult().getUser().getDetail().getCharm());
//                            }
//                        }
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
