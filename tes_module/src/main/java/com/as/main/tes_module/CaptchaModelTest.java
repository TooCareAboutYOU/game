package com.as.main.tes_module;

import android.support.annotation.NonNull;

import com.kachat.game.libdata.apiServices.UserApi;
import com.kachat.game.libdata.http.BaseModel;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;

import rx.Observer;
import rx.Subscription;


public class CaptchaModelTest extends BaseModel {

    private static final String TAG = "CaptchaPresenterTest";

    private Subscription mSubscription;

    public void getCaptcha(@NonNull String mobile, final OnPresenterListenersTest.OnModelListenerTest<BaseBean<GetCaptchaBeanTest>> listener){
        mSubscription= UserApiTest.requestCaptcha(mobile,new Observer<BaseBean<GetCaptchaBeanTest>>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(final Throwable e) {
                if (listener != null) { listener.onError(e);  }
            }

            @Override
            public void onNext(final BaseBean<GetCaptchaBeanTest> bean) {
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
