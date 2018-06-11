package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.ToyRoomsBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.TicketModel;
import com.kachat.game.libdata.mvp.models.ToyRoomsModel;

public class ToyRoomsPresenter {

    private static final String TAG = "CheckMobileFragment";

    private ToyRoomsModel mModel;
    private OnPresenterListeners.OnViewListener<ToyRoomsBean> mView;

    public ToyRoomsPresenter(OnPresenterListeners.OnViewListener<ToyRoomsBean> view) {
        this.mModel=new ToyRoomsModel();
        this.mView = view;
    }

    public void attachPresenter(){
        this.mModel.getToyRooms(new OnPresenterListeners.OnModelListener<ToyRoomsBean>() {
            @Override
            public void onSuccess(ToyRoomsBean result) {
                if (mView != null) {
                    if (result.getCode() == CodeType.REQUEST_SUCCESS) {
                        ToyRoomsPresenter.this.mView.onSuccess(result);
                    }else {
                        ToyRoomsPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
