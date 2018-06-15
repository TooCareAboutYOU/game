package com.kachat.game.libdata.mvp.models;

import android.support.annotation.IntegerRes;

import com.kachat.game.libdata.apiServices.UserApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;


public class ChatResultModel extends BaseModel {

    private Subscription mSubscription;

    public void postChatResult(@IntegerRes int userFromId, @IntegerRes int userToId, @IntegerRes int time, final OnPresenterListeners.OnModelListener<MessageBean> listener){
        mSubscription= UserApi.postChatResult(userFromId,userToId,time,new Observer<MessageBean>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(final Throwable e) {
                if (listener != null) { listener.onError(e); }
            }

            @Override
            public void onNext(final MessageBean bean) {
                if (listener != null) { listener.onSuccess(bean); }
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
