package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GameTypeBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;
import com.kachat.game.libdata.mvp.models.CaptchaModel;
import com.kachat.game.libdata.mvp.models.GameListModel;

public class GameListPresenter {

    private GameListModel mModel;
    private OnPresenterListener.OnViewListener<GameTypeBean> mView;

    public GameListPresenter(OnPresenterListener.OnViewListener<GameTypeBean> view) {
        this.mModel=new GameListModel();
        this.mView = view;
    }

    public void attachPresenter(){
        this.mModel.requestGameList(new OnPresenterListener.OnModelListener<GameTypeBean>() {
            @Override
            public void onSuccess(BaseBean<GameTypeBean> result) {
                if (mView != null) {
                    if (result.getCode()== CodeType.REQUEST_SUCCESS) {
                        GameListPresenter.this.mView.onSuccess(result);
                    }else {
                        GameListPresenter.this.mView.onFailed(result);
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
