package com.kachat.game.libdata.mvp.presenters;


import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.RankingListBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.ExperienceRankModel;

public class ExperienceRankPresenter {

    private static final String TAG = "CheckMobileFragment";

    private ExperienceRankModel mModel;
    private OnPresenterListeners.OnViewListener<RankingListBean> mView;

    public ExperienceRankPresenter(OnPresenterListeners.OnViewListener<RankingListBean> view) {
        this.mModel=new ExperienceRankModel();
        this.mView = view;
    }

    public void attachPresenter(){
        this.mModel.getExperience(new OnPresenterListeners.OnModelListener<RankingListBean>() {
            @Override
            public void onSuccess(RankingListBean result) {
                if (mView != null) {
                    if (result.getCode() == CodeType.CODE_RESPONSE_SUCCESS) {
                        ExperienceRankPresenter.this.mView.onSuccess(result);
                    }else {
                        ExperienceRankPresenter.this.mView.onFailed(result.getCode(),result.getError());
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
