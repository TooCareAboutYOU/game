package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.PropsBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.PropsModel;
import com.kachat.game.libdata.mvp.models.TicketModel;


public class PropsPresenter {

    private PropsModel mModel;
    private OnPresenterListeners.OnViewListener<PropsBean> mView;

    public PropsPresenter(OnPresenterListeners.OnViewListener<PropsBean> view) {
        this.mModel=new PropsModel();
        this.mView = view;
    }

    public void attachPresenter(){
        this.mModel.getUserTicket(new OnPresenterListeners.OnModelListener<BaseBean<PropsBean>>() {
            @Override
            public void onSuccess(BaseBean<PropsBean> result) {
                if (mView != null) {
                    if (result.getCode() == CodeType.CODE_RESPONSE_SUCCESS) {
                        PropsPresenter.this.mView.onSuccess(result.getResult());
                    }else {
                        PropsPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
