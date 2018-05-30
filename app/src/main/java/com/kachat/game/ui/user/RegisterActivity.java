package com.kachat.game.ui.user;


import android.os.Bundle;
import android.support.annotation.Nullable;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;
import com.kachat.game.libdata.mvp.presenters.CaptchaPresenter;
import com.kachat.game.libdata.mvp.presenters.RegisterPresenter;


public class RegisterActivity extends BaseActivity {

    private CaptchaPresenter mCaptchaPresenter;
    private RegisterPresenter mRegisterPresenter;

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void onInitView() {
//        mCaptchaPresenter=new CaptchaPresenter(new RequestCaptcha());
//        mCaptchaPresenter.attachPresenter("");
        mRegisterPresenter=new RegisterPresenter(new RegisterCallBack());
        mRegisterPresenter.attachPresenter("","","","","");
    }

    @Override
    protected void onInitData(@Nullable Bundle savedInstanceState) {

    }

    private class RequestCaptcha implements OnPresenterListener.OnViewListener<GetCaptchaBean>{

        @Override
        public void onSuccess(BaseBean<GetCaptchaBean> result) {

        }

        @Override
        public void onFailed(BaseBean<GetCaptchaBean> result) {

        }

        @Override
        public void onError(Throwable throwable) {

        }
    }

    private class RegisterCallBack implements OnPresenterListener.OnViewListener<UserBean>{
        @Override
        public void onSuccess(BaseBean<UserBean> result) {

        }

        @Override
        public void onFailed(BaseBean<UserBean> result) {

        }

        @Override
        public void onError(Throwable throwable) {

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
