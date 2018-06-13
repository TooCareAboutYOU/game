package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.FeedBacksBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.FeedbacksModel;
import com.kachat.game.libdata.mvp.models.LoginModel;

public class FeedBacksPresenter {

    private FeedbacksModel mModel;
    private OnPresenterListeners.OnViewListener<FeedBacksBean> mView;

    public FeedBacksPresenter(OnPresenterListeners.OnViewListener<FeedBacksBean> view) {
        this.mModel=new FeedbacksModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String content){
        this.mModel.requestFeedBacks(content, new OnPresenterListeners.OnModelListener<FeedBacksBean>() {
            @Override
            public void onSuccess(FeedBacksBean result) {
                if (mView != null) {
                    if (result.getCode()== CodeType.REQUEST_SUCCESS) {
                        FeedBacksPresenter.this.mView.onSuccess(result);
                    }else {
                        FeedBacksPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
