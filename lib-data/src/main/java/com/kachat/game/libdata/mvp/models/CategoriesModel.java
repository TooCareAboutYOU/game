package com.kachat.game.libdata.mvp.models;

import com.kachat.game.libdata.apiServices.GameApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.CategoryTypeBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;


public class CategoriesModel extends BaseModel {

    private static final String TAG = "ShopActivity";

    private Subscription mSubscription;

    public void getCategories(final OnPresenterListeners.OnModelListener<CategoryTypeBean> listener){
        mSubscription= GameApi.getCategories(new Observer<CategoryTypeBean>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(final Throwable e) {
                if (listener != null) { listener.onError(e); }
            }

            @Override
            public void onNext(final CategoryTypeBean bean) {
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
