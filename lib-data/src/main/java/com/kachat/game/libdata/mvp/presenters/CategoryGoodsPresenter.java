package com.kachat.game.libdata.mvp.presenters;


import android.support.annotation.NonNull;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.CategoryListBean;
import com.kachat.game.libdata.model.CategoryTypeBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.CategoriesModel;
import com.kachat.game.libdata.mvp.models.CategoryGoodsModel;

public class CategoryGoodsPresenter {

    private static final String TAG = "ShopActivity";

    private CategoryGoodsModel mModel;
    private OnPresenterListeners.OnViewListener<CategoryListBean> mView;

    public CategoryGoodsPresenter(OnPresenterListeners.OnViewListener<CategoryListBean> view) {
        this.mModel=new CategoryGoodsModel();
        this.mView = view;
    }

    public void attachPresenter(int index){
        this.mModel.getGoods(index,new OnPresenterListeners.OnModelListener<BaseBean<CategoryListBean>>() {
            @Override
            public void onSuccess(BaseBean<CategoryListBean> result) {
                if (mView != null) {
                    if (result.getCode() == CodeType.CODE_RESPONSE_SUCCESS) {
                        CategoryGoodsPresenter.this.mView.onSuccess(result.getResult());
                    }else {
                        CategoryGoodsPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
