package com.kachat.game.libdata.mvp.models;

import com.kachat.game.libdata.apiServices.GameApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.CategoryTypeBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;


public class BuyGoodsModel extends BaseModel {

    private static final String TAG = "BuyGoodsModel";

    private Subscription mSubscription;

    public void postGoods(int goodId, int amount, final OnPresenterListeners.OnModelListener<BaseBean<MessageBean>> listener){
        mSubscription= GameApi.postGoods(goodId,amount,new Observer<BaseBean<MessageBean>>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(final Throwable e) {
                if (listener != null) { listener.onError(e); }
            }

            @Override
            public void onNext(final BaseBean<MessageBean> bean) {
                if (listener != null) { listener.onSuccess(bean); }
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
