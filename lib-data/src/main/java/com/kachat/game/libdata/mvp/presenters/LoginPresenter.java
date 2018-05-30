package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GameTypeBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;
import com.kachat.game.libdata.mvp.models.LoginModel;

public class LoginPresenter {

    private LoginModel mModel;
    private OnPresenterListener.OnViewListener<UserBean> mView;

    public LoginPresenter(OnPresenterListener.OnViewListener<UserBean> view) {
        this.mModel=new LoginModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String mobile,@NonNull String pwd){
        this.mModel.login(mobile, pwd, new OnPresenterListener.OnModelListener<UserBean>() {
            @Override
            public void onSuccess(BaseBean<UserBean> result) {
                if (mView != null) {
                    if (result.getCode()== CodeType.REQUEST_SUCCESS) {
                        LoginPresenter.this.mView.onSuccess(result);
                    }else {
                        LoginPresenter.this.mView.onFailed(result);
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
