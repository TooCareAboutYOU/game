package com.kachat.game.libdata.mvp.presenters;


import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.RankingListBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.CharmRankModel;

public class CharmRankPresenter {

    private static final String TAG = "CheckMobileFragment";

    private CharmRankModel mModel;
    private OnPresenterListeners.OnViewListener<RankingListBean> mView;

    public CharmRankPresenter(OnPresenterListeners.OnViewListener<RankingListBean> view) {
        this.mModel=new CharmRankModel();
        this.mView = view;
    }

    public void attachPresenter(int type){
        this.mModel.getCharm(type,new OnPresenterListeners.OnModelListener<BaseBean<RankingListBean>>() {
            @Override
            public void onSuccess(BaseBean<RankingListBean> result) {
                if (mView != null) {
                    if (result.getCode() == CodeType.CODE_RESPONSE_SUCCESS) {
                        CharmRankPresenter.this.mView.onSuccess(result.getResult());
                    }else {
                        CharmRankPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
