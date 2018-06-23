package com.kachat.game.ui.user.register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.kachat.game.Config;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.CaptchaPresenter;
import com.kachat.game.libdata.mvp.presenters.VerifyCaptchaPresenter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegisterActivity extends BaseActivity {

    @BindView(R.id.toolbar_base)
    Toolbar mToolbarBase;
    @BindView(R.id.ll_)
    LinearLayoutCompat mLl;

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.acTv_mobile)
    AppCompatTextView mAcTvMobile;
    @BindView(R.id.acEt_captcha1)
    AppCompatEditText mAcEtCaptcha1;
    @BindView(R.id.acEt_captcha2)
    AppCompatEditText mAcEtCaptcha2;
    @BindView(R.id.acEt_captcha3)
    AppCompatEditText mAcEtCaptcha3;
    @BindView(R.id.acEt_captcha4)
    AppCompatEditText mAcEtCaptcha4;

    @BindView(R.id.acTv_Timer)
    AppCompatTextView mAcTvTimer;

    private String mobile = Config.getMobile();

    CountDownTimer mTimer = new CountDownTimer(60 * 1000, 1000) {
        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {
            mAcTvTimer.setText("重新发送：" + (millisUntilFinished / 1000) + "\ts");
        }

        @Override
        public void onFinish() {
            mAcTvTimer.setText("重新获取验证码");
            mAcTvTimer.setEnabled(true);
            mAcTvTimer.setTextColor(Color.GREEN);
        }
    };

    private VerifyCaptchaPresenter mPresenter;
    private CaptchaPresenter mCaptchaPresenter;

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected boolean onSetStatusBar() {
        return true;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (getImmersionBar() != null) {
            getImmersionBar().titleBar(mToolbarBase).transparentStatusBar().init();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onInitView() {
        mToolbarBase.setBackgroundResource(R.color.colorNormal);
        getToolBarBack().setOnClickListener(View::forceLayout);


        ((AppCompatTextView) findViewById(R.id.atv_ToolBar_Base_Title)).setText("注册");

        mPresenter = new VerifyCaptchaPresenter(new VerifyCaptchaCallBack());


        mAcTvMobile.setText("短信已发送至:" + mobile);


        mAcTvTimer.setEnabled(false);
        mTimer.start();

        mAcTvTimer.setOnClickListener(v -> {
            mCaptchaPresenter.attachPresenter(mobile);
            mAcTvTimer.setEnabled(false);
            mAcTvTimer.setTextColor(Color.GRAY);
        });

        mAcEtCaptcha4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void check() {
        String VCode = "";
        if (mAcEtCaptcha1.getText().toString().trim().isEmpty()) {
            Toast("验证码不能为空!");
            return;
        }
        if (mAcEtCaptcha2.getText().toString().trim().isEmpty()) {
            Toast("验证码不能为空!");
            return;
        }
        if (mAcEtCaptcha3.getText().toString().trim().isEmpty()) {
            Toast("验证码不能为空!");
            return;
        }
        if (mAcEtCaptcha4.getText().toString().trim().isEmpty()) {
            Toast("验证码不能为空!");
            return;
        }

        VCode = mAcEtCaptcha1.getText().toString().trim() +
                mAcEtCaptcha2.getText().toString().trim() +
                mAcEtCaptcha3.getText().toString().trim() +
                mAcEtCaptcha4.getText().toString().trim();

        mPresenter.attachPresenter(mobile, VCode);
    }


    private class VerifyCaptchaCallBack implements OnPresenterListeners.OnViewListener<MessageBean> {
        @Override
        public void onSuccess(MessageBean result) {
            mTimer.cancel();
            startActivity(new Intent(RegisterActivity.this, PersonInfoActivity.class));
            finish();
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (error != null) {
                Toast(error.getToast());
            }

            mAcEtCaptcha1.setText("");
            mAcEtCaptcha2.setText("");
            mAcEtCaptcha3.setText("");
            mAcEtCaptcha4.setText("");
        }

        @Override
        public void onError(Throwable e) {

            mAcEtCaptcha1.setText("");
            mAcEtCaptcha2.setText("");
            mAcEtCaptcha3.setText("");
            mAcEtCaptcha4.setText("");
            if (e != null) {
                Toast(e.getMessage());
            }
        }
    }


    private class CheckAccount implements OnPresenterListeners.OnViewListener<GetCaptchaBean> {
        @Override
        public void onSuccess(GetCaptchaBean result) {  //不存在,发送验证码
            if (result != null && result.getResult() != null) {
                Toast("验证码：" + result.getResult().getCaptcha());
            }
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (error != null) {
                Toast(error.getToast());
            }
        }

        @Override
        public void onError(Throwable e) {
            if (e != null) {
                Toast(e.getMessage());
            }
        }

    }

    @Override
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
        }

        if (mCaptchaPresenter != null) {
            mCaptchaPresenter.detachPresenter();
            mCaptchaPresenter = null;
        }

        if (mPresenter != null) {
            mPresenter.detachPresenter();
            mPresenter = null;
        }
        super.onDestroy();
    }

}
