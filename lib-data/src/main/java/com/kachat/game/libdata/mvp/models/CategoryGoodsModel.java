package com.kachat.game.libdata.mvp.models;

import android.support.annotation.NonNull;

import com.kachat.game.libdata.apiServices.GameApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.CategoryListBean;
import com.kachat.game.libdata.model.CategoryTypeBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;


public class CategoryGoodsModel extends BaseModel {

    private static final String TAG = "ShopActivity";

    private Subscription mSubscription;

    public void getGoods(int index, final OnPresenterListeners.OnModelListener<CategoryListBean> listener){
        mSubscription= GameApi.getGoods(index,new Observer<CategoryListBean>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(final Throwable e) {
                if (listener != null) { listener.onError(e); }
            }

            @Override
            public void onNext(final CategoryListBean bean) {
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
