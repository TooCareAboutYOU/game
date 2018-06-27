package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;
import android.util.Log;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.CaptchaModel;

public class CaptchaPresenter {

    private static final String TAG = "LoginActivity";

    private CaptchaModel mModel;
    private OnPresenterListeners.OnViewListener<GetCaptchaBean> mView;

    public CaptchaPresenter(OnPresenterListeners.OnViewListener<GetCaptchaBean> view) {
        this.mModel=new CaptchaModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String mobile){
        this.mModel.getCaptcha(mobile, new OnPresenterListeners.OnModelListener<BaseBean<GetCaptchaBean>>() {
            @Override
            public void onSuccess(BaseBean<GetCaptchaBean> result) {
                Log.i(TAG, "onSuccess: "+result.toString());
                if (mView != null) {
                    if (result.getCode() == CodeType.CODE_RESPONSE_SUCCESS) {
                        CaptchaPresenter.this.mView.onSuccess(result.getResult());
                    }else {
                        CaptchaPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
