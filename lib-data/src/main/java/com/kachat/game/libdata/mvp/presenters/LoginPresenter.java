package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;
import com.kachat.game.libdata.mvp.models.LoginModel;

public class LoginPresenter {

    private LoginModel mModel;
    private OnPresenterListener.OnViewListener<BaseBean<UserBean>> mView;

    public LoginPresenter(OnPresenterListener.OnViewListener<BaseBean<UserBean>> view) {
        this.mModel=new LoginModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String mobile,@NonNull String pwd){
        this.mModel.login(mobile, pwd, new OnPresenterListener.OnModelListener<BaseBean<UserBean>>() {
            @Override
            public void onSuccess(BaseBean<UserBean> result) {
                if (result != null) {
                    if (mView != null) LoginPresenter.this.mView.onSuccess(result);
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
