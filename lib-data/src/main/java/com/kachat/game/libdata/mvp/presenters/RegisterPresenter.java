package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GameTypeBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;
import com.kachat.game.libdata.mvp.models.LoginModel;
import com.kachat.game.libdata.mvp.models.RegisterModel;

public class RegisterPresenter {

    private RegisterModel mModel;
    private OnPresenterListener.OnViewListener<UserBean> mView;

    public RegisterPresenter(OnPresenterListener.OnViewListener<UserBean> view) {
        this.mModel=new RegisterModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String mobile, @NonNull String pwd, @NonNull String gender,
                                @NonNull String age, @NonNull String username){
        this.mModel.register(mobile,pwd,gender,age,username, new OnPresenterListener.OnModelListener<UserBean>() {
            @Override
            public void onSuccess(BaseBean<UserBean> result) {
                if (mView != null) {
                    if (result.getCode()== CodeType.REQUEST_SUCCESS) {
                        RegisterPresenter.this.mView.onSuccess(result);
                    }else {
                        RegisterPresenter.this.mView.onFailed(result);
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
