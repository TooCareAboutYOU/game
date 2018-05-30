package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GameTypeBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;
import com.kachat.game.libdata.mvp.models.LoginModel;
import com.kachat.game.libdata.mvp.models.VerifyCaptchaModel;

public class VerifyCaptchaPresenter {

    private VerifyCaptchaModel mModel;
    private OnPresenterListener.OnViewListener<MessageBean> mView;

    public VerifyCaptchaPresenter(OnPresenterListener.OnViewListener<MessageBean> view) {
        this.mModel=new VerifyCaptchaModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String mobile,@NonNull String captcha){
        this.mModel.verifyCaptcha(mobile, captcha, new OnPresenterListener.OnModelListener<MessageBean>() {
            @Override
            public void onSuccess(BaseBean<MessageBean> result) {
                if (mView != null) {
                    if (result.getCode()== CodeType.REQUEST_SUCCESS) {
                        VerifyCaptchaPresenter.this.mView.onSuccess(result);
                    }else {
                        VerifyCaptchaPresenter.this.mView.onFailed(result);
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
