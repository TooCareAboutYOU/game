package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.HpModel;
import com.kachat.game.libdata.mvp.models.TicketModel;

public class HpPresenter {

    private static final String TAG = "CheckMobileFragment";

    private HpModel mModel;
    private OnPresenterListeners.OnViewListener<MessageBean> mView;

    public HpPresenter(OnPresenterListeners.OnViewListener<MessageBean> view) {
        this.mModel=new HpModel();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String game, @NonNull String uid){
        this.mModel.postHp(game,uid, new OnPresenterListeners.OnModelListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (mView != null) {
                    if (result.getCode() == CodeType.REQUEST_SUCCESS) {
                        HpPresenter.this.mView.onSuccess(result); // result.getResult().getHp();
                    }else {
                        HpPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
