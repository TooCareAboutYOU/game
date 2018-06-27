package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.controls.DaoInsert;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.UpdateUserData;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.LoginModel;
import com.kachat.game.libdata.mvp.models.UpdateUserModel;

public class UpdateUserPresenter {

    private UpdateUserModel mModel;
    private OnPresenterListeners.OnViewListener<UpdateUserData> mView;

    public UpdateUserPresenter(OnPresenterListeners.OnViewListener<UpdateUserData> view) {
        this.mModel=new UpdateUserModel();
        this.mView = view;
    }

    public void attachPresenter(){
        this.mModel.updateUserData(new OnPresenterListeners.OnModelListener<BaseBean<UpdateUserData>>() {
            @Override
            public void onSuccess(BaseBean<UpdateUserData> result) {
                if (mView != null) {
                    if (result.getCode()== CodeType.CODE_RESPONSE_SUCCESS) {
//                        DaoInsert.insterLogin(true);
                        UpdateUserPresenter.this.mView.onSuccess(result.getResult());
                    }else {
                        UpdateUserPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
