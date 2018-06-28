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
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.LoginPresenter;
import com.kachat.game.libdata.mvp.presenters.ResetCaptchaPresenter;
import com.kachat.game.libdata.mvp.presenters.ResetPwdPresenter;
import com.kachat.game.ui.MainActivity;
import com.kachat.game.ui.user.login.CheckPwdActivity;
import com.kachat.game.ui.user.login.LoginActivity;
import com.kachat.game.utils.OnCheckNetClickListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResetPwdActivity extends BaseActivity {

    private static final String TAG = "ResetPwdActivity";

    @BindView(R.id.toolbar_base)
    Toolbar mToolbarBase;
    @BindView(R.id.acEt_NewPassWord)
    AppCompatEditText mAcEtNewPassWord;

    public static void newInstance(Context context,Bundle bundle){
        Intent intent =new Intent(context,ResetPwdActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private ResetPwdPresenter mPwdPresenter = null;
    private LoginPresenter mPresenter=null;
    private String newPwd=null;

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
        getToolBarBack().setOnClickListener(v -> {
            LoginActivity.newInstance(this);
            finish();
        });
        mToolbarBase.setBackgroundResource(R.color.colorNormal);

        String captcha= Objects.requireNonNull(getIntent().getExtras()).getString(TAG);
        Log.i(TAG, "onInitView: "+captcha);

        mPresenter = new LoginPresenter(new LoginCallBack());
        mPwdPresenter = new ResetPwdPresenter(new ResetPwdCallBack());
        findViewById(R.id.acTv_Finish).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                newPwd=mAcEtNewPassWord.getText().toString();
                if (TextUtils.isEmpty(newPwd)) {
                    Toast("新密码不能为空");
                    return;
                }
                mPwdPresenter.attachPresenter(Config.getMobile(),captcha,newPwd);
            }
        });
    }

    private class ResetPwdCallBack implements OnPresenterListeners.OnViewListener<MessageBean> {

        @Override
        public void onSuccess(MessageBean result) {
            AutoLogin();
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

    // TODO: 2018/6/11 自动跳转登录
    private void AutoLogin() {
        String mobile = Config.getMobile();
        mPresenter.attachPresenter(mobile, newPwd);
    }

    private class LoginCallBack implements OnPresenterListeners.OnViewListener<UserBean> {

        @Override
        public void onSuccess(UserBean result) {
            if (result != null) {
                if (!TextUtils.isEmpty(result.getUser().getUsername())) {
                    MainActivity.getInstance(ResetPwdActivity.this);
                    ResetPwdActivity.this.finish();
                }
            }
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (error != null && !TextUtils.isEmpty(error.getToast())) {
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
    public void onBackPressed() {
        LoginActivity.newInstance(this);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (mPwdPresenter != null) {
            mPwdPresenter.detachPresenter();
            mPwdPresenter = null;
        }

        super.onDestroy();
    }
}
