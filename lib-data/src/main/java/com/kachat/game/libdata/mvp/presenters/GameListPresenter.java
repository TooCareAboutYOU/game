package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GameTypeBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;
import com.kachat.game.libdata.mvp.models.CaptchaModel;
import com.kachat.game.libdata.mvp.models.GameListModel;

public class GameListPresenter {

    private GameListModel mModel;
    private OnPresenterListener.OnViewListener<BaseBean<GameTypeBean>> mView;

    public GameListPresenter(OnPresenterListener.OnViewListener<BaseBean<GameTypeBean>> view) {
        this.mModel=new GameListModel();
        this.mView = view;
    }

    public void attachPresenter(){
        this.mModel.requestGameList(new OnPresenterListener.OnModelListener<BaseBean<GameTypeBean>>() {
            @Override
            public void onSuccess(BaseBean<GameTypeBean> result) {
                if (result != null) {
                    if (mView != null) GameListPresenter.this.mView.onSuccess(result);
                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                if (throwable != null) {
                    if (mView != null) mView.onFailed(throwable);
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
