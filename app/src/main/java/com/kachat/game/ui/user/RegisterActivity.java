package com.kachat.game.ui.user;


import android.os.Bundle;
import android.support.annotation.Nullable;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;
import com.kachat.game.libdata.mvp.presenters.CaptchaPresenter;


public class RegisterActivity extends BaseActivity {

    private CaptchaPresenter mCaptchaPresenter;

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void onInitView() {
//        mCaptchaPresenter=new CaptchaPresenter(new RequestCaptcha());
//        mCaptchaPresenter.attachPresenter("");
    }

    @Override
    protected void onInitData(@Nullable Bundle savedInstanceState) {

    }

    private class RequestCaptcha implements OnPresenterListener.OnViewListener<BaseBean<GetCaptchaBean>>{
        @Override
        public void onSuccess(BaseBean<GetCaptchaBean> result) {
            if (result != null) {
                if (result.getCode() == CodeType.REQUEST_SUCCESS) {

                }else {
                    if (result.getError() != null) {
                        for (String s : result.getError().getMessage()) {
                            Toast(s);
                        }
                    }
                    // 10001,10002,10003,10400
                }
            }
        }

        @Override
        public void onFailed(Throwable throwable) {
            if (throwable != null) {
                Toast(throwable.getMessage());
            }
        }
    }


    @Override
    protected void onDestroy() {
//        if (mCaptchaPresenter != null) {
//            mCaptchaPresenter.detachPresenter();
//            mCaptchaPresenter=null;
//        }
        super.onDestroy();
    }

}
