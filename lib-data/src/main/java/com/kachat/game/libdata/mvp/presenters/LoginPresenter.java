package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;
import android.util.Log;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.controls.DaoInsert;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.LoginModel;

public class LoginPresenter {

    private static final String TAG = "MyLogin";

    private LoginModel mModel;
    private OnPresenterListeners.OnViewListener<UserBean> mView;

    public LoginPresenter(OnPresenterListeners.OnViewListener<UserBean> view) {
        this.mModel=new LoginModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String mobile,@NonNull String pwd){
        this.mModel.login(mobile, pwd, new OnPresenterListeners.OnModelListener<BaseBean<UserBean>>() {
            @Override
            public void onSuccess(BaseBean<UserBean> result) {
                Log.i(TAG, "onSuccess:总 "+result.toString());
                if (mView != null) {
                    if (result.getCode()== CodeType.CODE_RESPONSE_SUCCESS) {
                        Log.i(TAG, "onSuccess: 成功");
                        if (result.getResult() != null) {
                            LoginPresenter.this.mView.onSuccess(result.getResult());
                        }
                    }else {
                        if (result.getError() != null) {
                            Log.i(TAG, "onSuccess: 失败"+result.getError().toString());
                            LoginPresenter.this.mView.onFailed(result.getCode(),result.getError());
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (throwable != null) {
                    Log.i(TAG, "onError: "+throwable.getMessage());
                    if (mView != null) LoginPresenter.this.mView.onError(throwable);
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
