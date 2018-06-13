package com.kachat.game.libdata.mvp.presenters;



public class ResetCaptchaPresenter {

//    private ResetCaptchaModel mModel;
//    private OnPresenterListener.OnViewListener<GetCaptchaBean> mView;
//
//    public ResetCaptchaPresenter(OnPresenterListener.OnViewListener<GetCaptchaBean> view) {
//        this.mModel=new ResetCaptchaModel();
//        this.mView = view;
//    }
//
//    public void attachPresenter(@NonNull String mobile){
//        this.mModel.getCaptcha(mobile, new OnPresenterListener.OnModelListener<GetCaptchaBean>() {
//            @Override
//            public void onSuccess(BaseBean<GetCaptchaBean> result) {
//                if (mView != null) {
//                    if (result.getCode()== CodeType.REQUEST_SUCCESS) {
//                        ResetCaptchaPresenter.this.mView.onSuccess(result.getResult());
//                    }else {
//                        ResetCaptchaPresenter.this.mView.onFailed(result.getCode(),result.getError());
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                if (throwable != null) {
//                    if (mView != null) mView.onError(throwable);
//                }
//            }
//        });
//    }
//
//    public void detachPresenter(){
//        if (mModel != null) {
//            mModel.close();
//        }
//
//        if (mView != null) {
//            mView = null;
//        }
//    }
}
