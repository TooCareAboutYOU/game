package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;
import com.kachat.game.libdata.mvp.models.LoginModel;
import com.kachat.game.libdata.mvp.models.VerifyCaptchaModel;

public class VerifyCaptchaPresenter {

    private VerifyCaptchaModel mModel;
    private OnPresenterListener.OnViewListener<BaseBean<MessageBean>> mView;

    public VerifyCaptchaPresenter(OnPresenterListener.OnViewListener<BaseBean<MessageBean>> view) {
        this.mModel=new VerifyCaptchaModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String mobile,@NonNull String captcha){
        this.mModel.verifyCaptcha(mobile, captcha, new OnPresenterListener.OnModelListener<BaseBean<MessageBean>>() {
            @Override
            public void onSuccess(BaseBean<MessageBean> result) {
                if (result != null) {
                    if (mView != null) VerifyCaptchaPresenter.this.mView.onSuccess(result);
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
