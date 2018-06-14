package com.kachat.game.libdata.mvp.models;

import com.kachat.game.libdata.apiServices.GameApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.ToyRoomsBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;


public class ToyRoomsModel extends BaseModel {

    private static final String TAG = "CheckMobileFragment";

    private Subscription mSubscription;

    public void getToyRooms(final OnPresenterListeners.OnModelListener<ToyRoomsBean> listener){
        mSubscription= GameApi.getToyRooms(new Observer<ToyRoomsBean>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(final Throwable e) {
//                LocalHandler().post(() -> {
                    if (listener != null) {
                        listener.onError(e);
                    }
//                });
            }

            @Override
            public void onNext(final ToyRoomsBean bean) {
//                LocalHandler().post(() -> {
                    if (listener != null) {
                        listener.onSuccess(bean);
                    }
//                });
            }
        });

        if (mSubscription != null) {
            addCompositeSubscription(mSubscription);
        }
    }


    public void close(){
        if (mSubscription != null) {
            delCompositeSubscription();
            mSubscription=null;
        }
    }

}
