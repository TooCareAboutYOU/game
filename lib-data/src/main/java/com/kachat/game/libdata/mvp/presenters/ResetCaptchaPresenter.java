package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;
import com.kachat.game.libdata.mvp.models.CaptchaModel;
import com.kachat.game.libdata.mvp.models.ResetCaptchaModel;

public class ResetCaptchaPresenter {

    private ResetCaptchaModel mModel;
    private OnPresenterListener.OnViewListener<BaseBean<GetCaptchaBean>> mView;

    public ResetCaptchaPresenter(OnPresenterListener.OnViewListener<BaseBean<GetCaptchaBean>> view) {
        this.mModel=new ResetCaptchaModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String mobile){
        this.mModel.getCaptcha(mobile, new OnPresenterListener.OnModelListener<BaseBean<GetCaptchaBean>>() {
            @Override
            public void onSuccess(BaseBean<GetCaptchaBean> result) {
                if (result != null) {
                    if (mView != null) ResetCaptchaPresenter.this.mView.onSuccess(result);
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
