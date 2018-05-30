package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GameTypeBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;
import com.kachat.game.libdata.mvp.models.ResetPwdModel;
import com.kachat.game.libdata.mvp.models.VerifyCaptchaModel;

import java.util.HashMap;

public class ResetPwdPresenter {

    private ResetPwdModel mModel;
    private OnPresenterListener.OnViewListener<MessageBean> mView;

    public ResetPwdPresenter(OnPresenterListener.OnViewListener<MessageBean> view) {
        this.mModel = new ResetPwdModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String mobile, @NonNull String captcha, @NonNull String pwd) {
        this.mModel.resetPwd(mobile, captcha, pwd, new OnPresenterListener.OnModelListener<MessageBean>() {
            @Override
            public void onSuccess(BaseBean<MessageBean> result) {
                if (mView != null) {
                    if (result.getCode()== CodeType.REQUEST_SUCCESS) {
                        ResetPwdPresenter.this.mView.onSuccess(result);
                    }else {
                        ResetPwdPresenter.this.mView.onFailed(result);
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
