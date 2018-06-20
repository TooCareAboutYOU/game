package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.ChatGiftsModel;
import com.kachat.game.libdata.mvp.models.SignsStatusModel;

public class ChatGiftPresenter {

    private ChatGiftsModel mModel;
    private OnPresenterListeners.OnViewListener<MessageBean> mView;

    public ChatGiftPresenter(OnPresenterListeners.OnViewListener<MessageBean> view) {
        this.mModel=new ChatGiftsModel();
        this.mView = view;
    }

    public void attachPresenter(@IntegerRes int userFromId, @IntegerRes int userToId, @IntegerRes int prop){
        this.mModel.postChatGifts(userFromId,userToId,prop, new OnPresenterListeners.OnModelListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (mView != null) {
                    if (result.getCode() == CodeType.CODE_RESPONSE_SUCCESS) {
                        ChatGiftPresenter.this.mView.onSuccess(result);
                    }else {
                        ChatGiftPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
