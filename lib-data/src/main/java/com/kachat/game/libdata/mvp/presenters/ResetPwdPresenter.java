package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;
import com.kachat.game.libdata.mvp.models.ResetPwdModel;
import com.kachat.game.libdata.mvp.models.VerifyCaptchaModel;

import java.util.HashMap;

public class ResetPwdPresenter {

    private ResetPwdModel mModel;
    private OnPresenterListener.OnViewListener<BaseBean<MessageBean>> mView;

    public ResetPwdPresenter(OnPresenterListener.OnViewListener<BaseBean<MessageBean>> view) {
        this.mModel = new ResetPwdModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String mobile, @NonNull String captcha, @NonNull String pwd) {
        this.mModel.resetPwd(mobile, captcha, pwd, new OnPresenterListener.OnModelListener<BaseBean<MessageBean>>() {
            @Override
            public void onSuccess(BaseBean<MessageBean> result) {
                if (result != null) {
                    if (mView != null) ResetPwdPresenter.this.mView.onSuccess(result);
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

    public void detachPresenter() {
        if (mModel != null) {
            mModel.close();
        }
        HashMap<String, Boolean> map = new HashMap<>();

        if (mView != null) {
            mView = null;
        }
    }
}
