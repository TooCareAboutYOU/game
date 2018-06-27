package com.kachat.game.libdata.mvp.models;

import com.kachat.game.libdata.apiServices.GameApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GamesBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;

/**
 *
 */
public class GameListModel extends BaseModel {

    private Subscription mSubscription;

    public void requestGameList(final OnPresenterListeners.OnModelListener<BaseBean<GamesBean>> listener){
        mSubscription= GameApi.requestGameList(new Observer<BaseBean<GamesBean>>() {
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
            public void onNext(final BaseBean<GamesBean> bean) {
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
