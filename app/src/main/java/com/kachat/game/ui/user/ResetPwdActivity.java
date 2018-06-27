package com.kachat.game.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.NetworkUtils;
import com.kachat.game.Config;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.ResetCaptchaPresenter;
import com.kachat.game.libdata.mvp.presenters.ResetPwdPresenter;
import com.kachat.game.utils.OnCheckNetClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResetPwdActivity extends BaseActivity {

    private static final String TAG = "ResetPwdActivity";

    @BindView(R.id.toolbar_base)
    Toolbar mToolbarBase;
    @BindView(R.id.acEt_NewPassWord)
    AppCompatEditText mAcEtNewPassWord;
    @BindView(R.id.acEt_Captcha)
    AppCompatEditText mAcEtCaptcha;

    public static void newInstance(Context context){
        Intent intent =new Intent(context,ResetPwdActivity.class);
        context.startActivity(intent);
    }

    private ResetPwdPresenter mPwdPresenter = null;
    private ResetCaptchaPresenter mCaptchaPresenter =null;

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_reset_pass_word;
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

    @Override
    protected void onInitView() {
        getToolBarBack().setOnClickListener(v -> finish());
        mToolbarBase.setBackgroundResource(R.color.colorNormal);

        mCaptchaPresenter=new ResetCaptchaPresenter(new GetCaptchaCallBack());
        mCaptchaPresenter.attachPresenter(Config.getMobile());

        mPwdPresenter = new ResetPwdPresenter(new ResetPwdCallBack());
        findViewById(R.id.acTv_Finish).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                String newPwd=mAcEtNewPassWord.getText().toString();
                String captcha=mAcEtCaptcha.getText().toString();
                if (TextUtils.isEmpty(newPwd)) {
                    Toast("新密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(captcha)) {
                    Toast("验证码不能为空");
                    return;
                }
                mPwdPresenter.attachPresenter(Config.getMobile(),captcha,newPwd);
            }
        });
    }

    private class GetCaptchaCallBack implements OnPresenterListeners.OnViewListener<GetCaptchaBean>{

        @Override
        public void onSuccess(GetCaptchaBean result) {
            Log.i(TAG, "onSuccess: "+result.getCaptcha());
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            Toast(error.getToast());
        }

        @Override
        public void onError(Throwable e) {
            Toast(e.getMessage());
        }
    }

    private class ResetPwdCallBack implements OnPresenterListeners.OnViewListener<MessageBean> {

        @Override
        public void onSuccess(MessageBean result) {
            Toast(result.getMessage());
            finish();
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            Log.i(TAG, "onFailed: " + error.getToast());
            Toast(error.getToast());
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
        if (mPwdPresenter != null) {
            mPwdPresenter.detachPresenter();
            mPwdPresenter = null;
        }

        if (mCaptchaPresenter != null) {
            mCaptchaPresenter.detachPresenter();
            mCaptchaPresenter=null;
        }

        super.onDestroy();
    }
}
