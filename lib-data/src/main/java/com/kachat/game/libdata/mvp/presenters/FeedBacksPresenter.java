package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;
import android.util.Log;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.FeedBacksBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.FeedBacksModel;

public class FeedBacksPresenter {

    private static final String TAG = "FeedBacksPresenter";

    private FeedBacksModel mModel;
    private OnPresenterListeners.OnViewListener<FeedBacksBean> mView;

    public FeedBacksPresenter(OnPresenterListeners.OnViewListener<FeedBacksBean> view) {
        this.mModel=new FeedBacksModel();
        this.mView = view;
    }

    public void attachPresenter(int type, @NonNull String content){
        Log.i(TAG, "type== "+type+"\t\t"+content);
        this.mModel.requestFeedBacks(type,content, new OnPresenterListeners.OnModelListener<BaseBean<FeedBacksBean>>() {
            @Override
            public void onSuccess(BaseBean<FeedBacksBean> result) {
                if (mView != null) {
                    if (result.getCode()== CodeType.CODE_RESPONSE_SUCCESS) {
                        FeedBacksPresenter.this.mView.onSuccess(result.getResult());
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
