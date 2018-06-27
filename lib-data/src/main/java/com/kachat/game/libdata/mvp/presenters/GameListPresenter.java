package com.kachat.game.libdata.mvp.presenters;


import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GamesBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.GameListModel;

public class GameListPresenter {

    private GameListModel mModel;
    private OnPresenterListeners.OnViewListener<GamesBean> mView;

    public GameListPresenter(OnPresenterListeners.OnViewListener<GamesBean> view) {
        this.mModel=new GameListModel();
        this.mView = view;
    }

    public void attachPresenter(){
        this.mModel.requestGameList(new OnPresenterListeners.OnModelListener<BaseBean<GamesBean>>() {
            @Override
            public void onSuccess(BaseBean<GamesBean> result) {
                if (mView != null) {
                    if (result.getCode()== CodeType.CODE_RESPONSE_SUCCESS) {
                        GameListPresenter.this.mView.onSuccess(result.getResult());
                    }else {
                        GameListPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
