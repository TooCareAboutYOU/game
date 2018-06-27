package com.as.main.tes_module;


import android.support.annotation.NonNull;
import android.util.Log;

import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.models.CaptchaModel;
import com.kachat.game.libdata.mvp.presenters.CaptchaPresenter;

public class CaptchaPresenterTest {

    private static final String TAG = "CaptchaPresenterTest";

    private CaptchaModelTest mModel;
    private OnPresenterListenersTest.OnViewListenerTest<GetCaptchaBeanTest> mView;

    public CaptchaPresenterTest(OnPresenterListenersTest.OnViewListenerTest<GetCaptchaBeanTest> view) {
        this.mModel=new CaptchaModelTest();
        this.mView = view;
    }

    public void attachPresenter(@NonNull String mobile) {
        this.mModel.getCaptcha(mobile, new OnPresenterListenersTest.OnModelListenerTest<BaseBean<GetCaptchaBeanTest>>() {
            @Override
            public void onSuccess(BaseBean<GetCaptchaBeanTest> result) {
                Log.i(TAG, "onSuccess: 总 "+result.toString());
                if (CaptchaPresenterTest.this.mView != null) {
                    Log.i(TAG, "onSuccess: ");
                    if (result.getCode() == CodeType.CODE_RESPONSE_SUCCESS ) {
                        if (result.getResult() != null) {
                            Log.i(TAG, "onSuccess: 成功 "+result.getResult().toString());
                            CaptchaPresenterTest.this.mView.onSuccess(result.getResult());
                        }
                    }else {
                        if (result.getError() != null) {
                            Log.i(TAG, "onSuccess: 失败 "+result.getError().toString());
                            CaptchaPresenterTest.this.mView.onFailed(result.getCode(),result.getError());
                        }
                    }
                }

            }

            @Override
            public void onError(Throwable e) {
                CaptchaPresenterTest.this.mView.onError(e);
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
