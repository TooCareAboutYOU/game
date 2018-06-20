package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.VerifyCaptchaModel;

public class VerifyCaptchaPresenter {

    private VerifyCaptchaModel mModel;
    private OnPresenterListeners.OnViewListener<MessageBean> mView;

    public VerifyCaptchaPresenter(OnPresenterListeners.OnViewListener<MessageBean> view) {
        this.mModel=new VerifyCaptchaModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String mobile,@NonNull String captcha){
        this.mModel.verifyCaptcha(mobile, captcha, new OnPresenterListeners.OnModelListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (mView != null) {
                    if (result.getCode()== CodeType.CODE_RESPONSE_SUCCESS) {
                        VerifyCaptchaPresenter.this.mView.onSuccess(result);
                    }else {
                        VerifyCaptchaPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
