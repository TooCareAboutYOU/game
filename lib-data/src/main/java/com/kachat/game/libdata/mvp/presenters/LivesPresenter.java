package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.LivesBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.LivesModel;
import com.kachat.game.libdata.mvp.models.TicketModel;

public class LivesPresenter {

    private LivesModel mModel;
    private OnPresenterListeners.OnViewListener<LivesBean> mView;

    public LivesPresenter(OnPresenterListeners.OnViewListener<LivesBean> view) {
        this.mModel=new LivesModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String uid){
        this.mModel.getUserLives(uid, new OnPresenterListeners.OnModelListener<LivesBean>() {
            @Override
            public void onSuccess(LivesBean result) {
                if (mView != null) {
                    if (result.getCode() == CodeType.REQUEST_SUCCESS) {
                        LivesPresenter.this.mView.onSuccess(result);
                    }else {
                        LivesPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
