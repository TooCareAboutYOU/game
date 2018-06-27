package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.CategoryTypeBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.BuyGoodsModel;
import com.kachat.game.libdata.mvp.models.CategoriesModel;

public class BuyGoodsPresenter {

    private static final String TAG = "BuyGoodsPresenter";

    private BuyGoodsModel mModel;
    private OnPresenterListeners.OnViewListener<MessageBean> mView;

    public BuyGoodsPresenter(OnPresenterListeners.OnViewListener<MessageBean> view) {
        this.mModel=new BuyGoodsModel();
        this.mView = view;
    }

    public void attachPresenter(int goodId, int amount){
        this.mModel.postGoods(goodId,amount,new OnPresenterListeners.OnModelListener<BaseBean<MessageBean>>() {
            @Override
            public void onSuccess(BaseBean<MessageBean> result) {
                if (mView != null) {
                    if (result.getCode() == CodeType.CODE_RESPONSE_SUCCESS) {
                        BuyGoodsPresenter.this.mView.onSuccess(result.getResult());
                    }else {
                        BuyGoodsPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
