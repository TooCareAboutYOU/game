package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.controls.DaoInsert;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.LoginModel;

public class LoginPresenter {

    private LoginModel mModel;
    private OnPresenterListeners.OnViewListener<UserBean> mView;

    public LoginPresenter(OnPresenterListeners.OnViewListener<UserBean> view) {
        this.mModel=new LoginModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String mobile,@NonNull String pwd){
        this.mModel.login(mobile, pwd, new OnPresenterListeners.OnModelListener<UserBean>() {
            @Override
            public void onSuccess(UserBean result) {
                if (mView != null) {
                    if (result.getCode()== CodeType.CODE_RESPONSE_SUCCESS) {
//                        DaoInsert.insterLogin(true);
                        LoginPresenter.this.mView.onSuccess(result);
                    }else {
                        LoginPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
