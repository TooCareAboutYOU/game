package com.kachat.game.libdata.mvp.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kachat.game.libdata.apiServices.UserApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.FeedBacksBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;

/**
 *
 */
public class FeedbacksModel extends BaseModel {

    private static final String TAG = "LoginModel";

    private Subscription mSubscription;

    public void requestFeedBacks(@NonNull String content,final OnPresenterListeners.OnModelListener<FeedBacksBean> listener){
        mSubscription= UserApi.requestFeedBacks(content, new Observer<FeedBacksBean>() {
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
            public void onNext(final FeedBacksBean result) {
                Log.i(TAG, "onNext: "+result.getResult().toString());
                LocalHandler().post(() -> {
                    if (listener != null) {
                        listener.onSuccess(result);
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
