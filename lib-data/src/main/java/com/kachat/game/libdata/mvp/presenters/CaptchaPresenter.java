package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;
import com.kachat.game.libdata.mvp.models.CaptchaModel;
import com.kachat.game.libdata.mvp.models.LoginModel;

public class CaptchaPresenter {

    private CaptchaModel mModel;
    private OnPresenterListener.OnViewListener<GetCaptchaBean> mView;

    public CaptchaPresenter(OnPresenterListener.OnViewListener<GetCaptchaBean> view) {
        this.mModel=new CaptchaModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String mobile){
        this.mModel.getCaptcha(mobile, new OnPresenterListener.OnModelListener<GetCaptchaBean>() {
            @Override
            public void onSuccess(BaseBean<GetCaptchaBean> result) {
                if (mView != null) {
                    if (result.getCode()== CodeType.REQUEST_SUCCESS) {
                        CaptchaPresenter.this.mView.onSuccess(result);
                    }else {
                        CaptchaPresenter.this.mView.onFailed(result);
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
