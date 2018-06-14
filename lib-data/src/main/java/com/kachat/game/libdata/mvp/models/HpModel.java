package com.kachat.game.libdata.mvp.models;

import android.support.annotation.NonNull;

import com.kachat.game.libdata.apiServices.GameApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;


public class HpModel extends BaseModel {

    private static final String TAG = "CheckMobileFragment";

    private Subscription mSubscription;

    public void postHp(@NonNull String game, @NonNull String uid, final OnPresenterListeners.OnModelListener<MessageBean> listener){
        mSubscription= GameApi.postHp(game,uid,new Observer<MessageBean>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(final Throwable e) {
//                LocalHandler().post(() -> {
                    if (listener != null) {
                        listener.onError(e);
                    }
//                });
            }

            @Override
            public void onNext(final MessageBean bean) {
//                LocalHandler().post(() -> {
                    if (listener != null) {
                        listener.onSuccess(bean);
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
