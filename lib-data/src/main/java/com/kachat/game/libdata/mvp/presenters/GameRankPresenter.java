package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GameRankBean;
import com.kachat.game.libdata.model.RankListBean;
import com.kachat.game.libdata.model.RankingListBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.ExperienceRankModel;
import com.kachat.game.libdata.mvp.models.GameRankModel;

public class GameRankPresenter {

    private static final String TAG = "CheckMobileFragment";

    private GameRankModel mModel;
    private OnPresenterListeners.OnViewListener<RankListBean> mView;

    public GameRankPresenter(OnPresenterListeners.OnViewListener<RankListBean> view) {
        this.mModel=new GameRankModel();
        this.mView = view;
    }

    public void attachPresenter(int gameIndex, int type){
        this.mModel.getGameRankList(gameIndex,type,new OnPresenterListeners.OnModelListener<BaseBean<RankListBean>>() {
            @Override
            public void onSuccess(BaseBean<RankListBean> result) {
                if (mView != null) {
                    if (result.getCode() == CodeType.CODE_RESPONSE_SUCCESS) {
                        GameRankPresenter.this.mView.onSuccess(result.getResult());
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
