package com.kachat.game.libdata.mvp.models;

import android.support.annotation.NonNull;

import com.kachat.game.libdata.ApiServices.GameApi;
import com.kachat.game.libdata.ApiServices.UserApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GameTypeBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;

import rx.Observer;
import rx.Subscription;

/**
 *
 */
public class GameListModel extends BaseModel {

    private Subscription mSubscription;

    public void requestGameList(final OnPresenterListener.OnModelListener<BaseBean<GameTypeBean>> listener){
        mSubscription= GameApi.requestGameList(new Observer<BaseBean<GameTypeBean>>() {
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
            public void onNext(final BaseBean<GameTypeBean> bean) {
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
