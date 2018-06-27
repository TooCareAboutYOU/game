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
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.Log;
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
import java.util.PrimitiveIterator;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar_base)
    Toolbar mToolbarBase;
    @BindView(R.id.ll_)
    LinearLayoutCompat mLl;

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

    private CountDownTimer mTimer = null;
    private void timer(){
        mTimer = new CountDownTimer(60 * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                mAcTvTimer.setText("重新发送：" + (millisUntilFinished / 1000) + "\ts");
            }

            @Override
            public void onFinish() {
                mAcTvTimer.setText("重新获取验证码");
                mAcTvTimer.setAutoLinkMask(Linkify.ALL);
                mAcTvTimer.setEnabled(true);
                mAcTvTimer.setTextColor(Color.GREEN);
                mTimer=null;

            }
        };
    }

    private VerifyCaptchaPresenter mPresenter=null;
    private CaptchaPresenter mCaptchaPresenter=null;

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
        getToolBarBack().setOnClickListener(v -> finish());

//        ((AppCompatTextView) findViewById(R.id.atv_ToolBar_Base_Title)).setText("注 册");

        mPresenter = new VerifyCaptchaPresenter(new VerifyCaptchaCallBack());
        mCaptchaPresenter =new CaptchaPresenter(new RegisterCallBack());

        mAcTvMobile.setText("短信已发送至:" + mobile);

        getCaptcha();
        mAcTvTimer.setOnClickListener(v -> {
            mCaptchaPresenter.attachPresenter(mobile);
            getCaptcha();
        });

        mAcEtCaptcha1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1 && !TextUtils.isEmpty(s)) {
//                    mAcEtCaptcha2.setFocusable(true);
//                    mAcEtCaptcha2.setFocusableInTouchMode(true);
//                    mAcEtCaptcha2.findFocus();
                    mAcEtCaptcha2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        mAcEtCaptcha2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1 && !TextUtils.isEmpty(s)) {
//                    mAcEtCaptcha3.setFocusable(true);
//                    mAcEtCaptcha3.setFocusableInTouchMode(true);
//                    mAcEtCaptcha3.findFocus();
                    mAcEtCaptcha3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        mAcEtCaptcha3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1 && !TextUtils.isEmpty(s)) {
//                    mAcEtCaptcha4.setFocusable(true);
//                    mAcEtCaptcha4.setFocusableInTouchMode(true);
//                    mAcEtCaptcha4.findFocus();
                    mAcEtCaptcha4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        mAcEtCaptcha4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1 && !TextUtils.isEmpty(s)) {
                    check();
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void getCaptcha(){
        mAcTvTimer.setEnabled(false);
        timer();
        mTimer.start();
        mAcTvTimer.setTextColor(Color.GRAY);
    }


    private class RegisterCallBack implements OnPresenterListeners.OnViewListener<GetCaptchaBean>{
        @Override
        public void onSuccess(GetCaptchaBean result) {
            Log.i(TAG, "onSuccess: "+result.getCaptcha());
            Toast(result.getCaptcha());
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            Toast(error.getToast());
        }

        @Override
        public void onError(Throwable e) {
            if (e != null) {
                Toast(e.getMessage());
            }
        }
    }


    //去完善信息
    private void check() {
        String VCode = "";
        if (mAcEtCaptcha1.getText().toString().trim().isEmpty() || mAcEtCaptcha2.getText().toString().trim().isEmpty()
                || mAcEtCaptcha3.getText().toString().trim().isEmpty() || mAcEtCaptcha4.getText().toString().trim().isEmpty()) {

            Toast("请输入正确验证码！");

            mAcEtCaptcha1.setText("");
            mAcEtCaptcha2.setText("");
            mAcEtCaptcha3.setText("");
            mAcEtCaptcha4.setText("");

//            mAcEtCaptcha1.setFocusable(true);
//            mAcEtCaptcha1.setFocusableInTouchMode(true);
//            mAcEtCaptcha1.findFocus();
            mAcEtCaptcha1.requestFocus();
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
            PersonInfoActivity.newInstance(RegisterActivity.this);
            finish();
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (error != null && !TextUtils.isEmpty(error.getToast())) {
                Toast(error.getToast());
            }

            mAcEtCaptcha1.setText("");
            mAcEtCaptcha2.setText("");
            mAcEtCaptcha3.setText("");
            mAcEtCaptcha4.setText("");
            mAcEtCaptcha1.requestFocus();
        }

        @Override
        public void onError(Throwable e) {

            mAcEtCaptcha1.setText("");
            mAcEtCaptcha2.setText("");
            mAcEtCaptcha3.setText("");
            mAcEtCaptcha4.setText("");
            mAcEtCaptcha1.requestFocus();
            if (e != null) {
                Toast(e.getMessage());
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer=null;
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
