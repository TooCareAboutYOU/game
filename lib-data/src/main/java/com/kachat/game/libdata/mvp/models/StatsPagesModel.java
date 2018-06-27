package com.kachat.game.libdata.mvp.models;

import android.support.annotation.NonNull;

import com.kachat.game.libdata.apiServices.GameApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;


public class StatsPagesModel extends BaseModel {

    private static final String TAG = "CheckMobileFragment";

    private Subscription mSubscription;

    public void postStatPages(@NonNull String type, @NonNull String uid,
                              final OnPresenterListeners.OnModelListener<BaseBean<MessageBean>> listener){
        mSubscription= GameApi.postStatPages(type,uid,new Observer<BaseBean<MessageBean>>() {
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
            public void onNext(final BaseBean<MessageBean> bean) {
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
