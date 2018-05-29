package com.kachat.game.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.model.BaseBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListener;
import com.kachat.game.libdata.mvp.presenters.LoginPresenter;
import com.kachat.game.ui.MainActivity;
import com.kachat.game.utils.MyUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {


    @BindView(R.id.sdv_Mobile_Icon) SimpleDraweeView mSdvMobileIcon;
    @BindView(R.id.tv_Mobile) AppCompatEditText mTvMobile;
    @BindView(R.id.sdv_passWord_Icon) SimpleDraweeView mSdvPassWordIcon;
    @BindView(R.id.tv_PassWord) AppCompatEditText mTvPassWord;
    @BindView(R.id.sdv_weChat_Login) SimpleDraweeView mSdvWeChatLogin;
    @BindView(R.id.sdv_TenCent_Login) SimpleDraweeView mSdvTenCentLogin;
    private LoginPresenter mLoginPresenter;


    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onInitView() {
        mLoginPresenter=new LoginPresenter(new RequestLogin());
    }

    @Override
    protected void onInitData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.cv_login)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_login:{
                requestLogin();
                break;
            }
        }
    }


    private void requestLogin(){
        String mobile=mTvMobile.getText().toString().trim();
        String password=mTvPassWord.getText().toString().trim();

        if (TextUtils.isEmpty(mobile)) {
            Toast("手机号码不能为空!");
            return;
        }
        if (!MyUtils.checkMobileNumber(mobile)){
            Toast("手机号码格式错误!");
            mTvMobile.setText("");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast("密码不能为空!");
            return;
        }

        mLoginPresenter.attachPresenter(mobile,password);
    }

    private class RequestLogin implements OnPresenterListener.OnViewListener<BaseBean<UserBean>>{

        @Override
        public void onSuccess(BaseBean<UserBean> result) {
            if (result != null) {
                if (result.getCode() == CodeType.REQUEST_SUCCESS) {
                    // TODO: 2018/5/29 保存用户信息 关键是token 
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }else {
                    if (result.getError() != null) {
                        for (String s : result.getError().getMessage()) {
                            Toast(s);
                        }
                    }
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
        if (mLoginPresenter != null) {
            mLoginPresenter.detachPresenter();
            mLoginPresenter=null;
        }
        super.onDestroy();
    }
}

