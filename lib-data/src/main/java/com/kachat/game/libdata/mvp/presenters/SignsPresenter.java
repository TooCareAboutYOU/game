package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.SingsBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.SignsModel;

public class SignsPresenter {

    private SignsModel mModel;
    private OnPresenterListeners.OnViewListener<SingsBean> mView;

    public SignsPresenter(OnPresenterListeners.OnViewListener<SingsBean> view) {
        this.mModel=new SignsModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String deviceId){
        this.mModel.requestSigns(deviceId, new OnPresenterListeners.OnModelListener<BaseBean<SingsBean>>() {
            @Override
            public void onSuccess(BaseBean<SingsBean> result) {
                if (mView != null) {
                    if (result.getCode()== CodeType.CODE_RESPONSE_SUCCESS) {
                        SignsPresenter.this.mView.onSuccess(result.getResult());
                    }else {
                        SignsPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
