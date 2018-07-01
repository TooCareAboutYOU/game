package com.kachat.game.libdata.mvp.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kachat.game.libdata.apiServices.UserApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.FeedBacksBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;

/**
 *
 */
public class FeedBacksModel extends BaseModel {

    private static final String TAG = "LoginModel";

    private Subscription mSubscription;

    public void requestFeedBacks(int type,@NonNull String content,
                                 final OnPresenterListeners.OnModelListener<BaseBean<FeedBacksBean>> listener){
        mSubscription= UserApi.requestFeedBacks(type,content, new Observer<BaseBean<FeedBacksBean>>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(final Throwable e) {
                if (listener != null) { listener.onError(e); }
            }

            @Override
            public void onNext(final BaseBean<FeedBacksBean> result) {
                if (listener != null) { listener.onSuccess(result); }
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
