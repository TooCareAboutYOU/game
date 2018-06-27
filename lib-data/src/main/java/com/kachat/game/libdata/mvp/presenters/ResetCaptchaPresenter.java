package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.ResetCaptchaModel;

public class ResetCaptchaPresenter {

    private ResetCaptchaModel mModel;
    private OnPresenterListeners.OnViewListener<GetCaptchaBean> mView;

    public ResetCaptchaPresenter(OnPresenterListeners.OnViewListener<GetCaptchaBean> view) {
        this.mModel=new ResetCaptchaModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String mobile){
        this.mModel.getResetCaptcha(mobile, new OnPresenterListeners.OnModelListener<BaseBean<GetCaptchaBean>>() {
            @Override
            public void onSuccess(BaseBean<GetCaptchaBean> result) {
                if (mView != null) {
                    if (result.getCode()== CodeType.CODE_RESPONSE_SUCCESS) {
                        ResetCaptchaPresenter.this.mView.onSuccess(result.getResult());
                    }else {
                        ResetCaptchaPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
