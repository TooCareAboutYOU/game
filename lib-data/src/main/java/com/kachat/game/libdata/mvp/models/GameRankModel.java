package com.kachat.game.libdata.mvp.models;

import android.support.annotation.NonNull;

import com.kachat.game.libdata.apiServices.GameApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.GameRankBean;
import com.kachat.game.libdata.model.RankingListBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;


public class GameRankModel extends BaseModel {

    private static final String TAG = "CheckMobileFragment";

    private Subscription mSubscription;

    public void getGameRankList(int gameIndex, int type, final OnPresenterListeners.OnModelListener<GameRankBean> listener){
        mSubscription= GameApi.getGameRankList(gameIndex,type,new Observer<GameRankBean>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(final Throwable e) {
                    if (listener != null) { listener.onError(e); }
            }

            @Override
            public void onNext(final GameRankBean bean) {
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
