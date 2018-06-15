package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.GameRankBean;
import com.kachat.game.libdata.model.RankingListBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.ExperienceRankModel;
import com.kachat.game.libdata.mvp.models.GameRankModel;

public class GameRankPresenter {

    private static final String TAG = "CheckMobileFragment";

    private GameRankModel mModel;
    private OnPresenterListeners.OnViewListener<GameRankBean> mView;

    public GameRankPresenter(OnPresenterListeners.OnViewListener<GameRankBean> view) {
        this.mModel=new GameRankModel();
        this.mView = view;
    }

    public void attachPresenter(@IntegerRes int gameIndex, @IntegerRes int type){
        this.mModel.getGameRankList(gameIndex,type,new OnPresenterListeners.OnModelListener<GameRankBean>() {
            @Override
            public void onSuccess(GameRankBean result) {
                if (mView != null) {
                    if (result.getCode() == CodeType.REQUEST_SUCCESS) {
                        GameRankPresenter.this.mView.onSuccess(result);
                    }else {
                        GameRankPresenter.this.mView.onFailed(result.getCode(),result.getError());
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (throwable != null) {
                    if (mView != null) mView.onError(throwable);
                }
            }
        });
    }

    public void detachPresenter(){
        if (mModel != null) {
            mModel.close();
        }

        if (mView != null) {
            mView = null;
        }
    }
}
