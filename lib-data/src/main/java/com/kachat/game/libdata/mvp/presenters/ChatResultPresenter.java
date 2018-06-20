package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.IntegerRes;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.ChatGiftsModel;
import com.kachat.game.libdata.mvp.models.ChatResultModel;

public class ChatResultPresenter {

    private ChatResultModel mModel;
    private OnPresenterListeners.OnViewListener<MessageBean> mView;

    public ChatResultPresenter(OnPresenterListeners.OnViewListener<MessageBean> view) {
        this.mModel=new ChatResultModel();
        this.mView = view;
    }

    public void attachPresenter(@IntegerRes int userFromId, @IntegerRes int userToId, @IntegerRes int time){
        this.mModel.postChatResult(userFromId,userToId,time, new OnPresenterListeners.OnModelListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (mView != null) {
                    if (result.getCode() == CodeType.CODE_RESPONSE_SUCCESS) {
                        ChatResultPresenter.this.mView.onSuccess(result);
                    }else {
                        ChatResultPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
