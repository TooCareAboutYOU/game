package com.kachat.game.libdata.mvp.presenters;


import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.CategoriesBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.CategoriesModel;

public class CategoriesPresenter {

    private static final String TAG = "CheckMobileFragment";

    private CategoriesModel mModel;
    private OnPresenterListeners.OnViewListener<CategoriesBean> mView;

    public CategoriesPresenter(OnPresenterListeners.OnViewListener<CategoriesBean> view) {
        this.mModel=new CategoriesModel();
        this.mView = view;
    }

    public void attachPresenter(){
        this.mModel.getCategories(new OnPresenterListeners.OnModelListener<CategoriesBean>() {
            @Override
            public void onSuccess(CategoriesBean result) {
                if (mView != null) {
                    if (result.getCode() == CodeType.REQUEST_SUCCESS) {
                        CategoriesPresenter.this.mView.onSuccess(result);
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
