package com.kachat.game.libdata.mvp.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kachat.game.libdata.apiServices.UserApi;
import com.kachat.game.libdata.controls.DaoUpdate;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.UpdateUserData;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;

/**
 *
 */
public class UpdateUserModel extends BaseModel {

    private static final String TAG = "LoginModel";

    private Subscription mSubscription;

    public void updateUserData(final OnPresenterListeners.OnModelListener<BaseBean<UpdateUserData>> listener){
        mSubscription= UserApi.updateUserData(new Observer<BaseBean<UpdateUserData>>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(final Throwable e) {
                if (listener != null) { listener.onError(e); }
            }

            @Override
            public void onNext(final BaseBean<UpdateUserData> result) {
                Log.i(TAG, "onNext: "+result.getResult().toString());
                    if (listener != null) {
                        listener.onSuccess(result);
                    }
                    if (result.getResult() != null) {
                        if (result.getResult().getDetail() != null) {
                            DaoUpdate.updateUser(
                                    result.getResult().getUsername(),
                                    result.getResult().getGender(),
                                    result.getResult().getUid(),
                                    result.getResult().getAge(),
                                    result.getResult().getDetail().getLevel(),
                                    result.getResult().getDetail().getHp(),
                                    result.getResult().getDetail().getExp_to_level_up(),
                                    result.getResult().getDetail().getExp(),
                                    result.getResult().getDetail().getNumber(),
                                    result.getResult().getDetail().getDiamond(),
                                    result.getResult().getDetail().getCharm(),
                                    result.getResult().getDetail().getGold());
                        }
                    }
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
