package com.kachat.game.libdata.mvp.presenters;


import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.CategoryTypeBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.CategoriesModel;

public class CategoriesPresenter {

    private static final String TAG = "ShopActivity";

    private CategoriesModel mModel;
    private OnPresenterListeners.OnViewListener<CategoryTypeBean> mView;

    public CategoriesPresenter(OnPresenterListeners.OnViewListener<CategoryTypeBean> view) {
        this.mModel=new CategoriesModel();
        this.mView = view;
    }

    public void attachPresenter(){
        this.mModel.getCategories(new OnPresenterListeners.OnModelListener<BaseBean<CategoryTypeBean>>() {
            @Override
            public void onSuccess(BaseBean<CategoryTypeBean> result) {
                if (mView != null) {
                    if (result.getCode() == CodeType.CODE_RESPONSE_SUCCESS) {
                        CategoriesPresenter.this.mView.onSuccess(result.getResult());
                    }else {
                        CategoriesPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
