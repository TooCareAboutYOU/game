package com.kachat.game.ui.user.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.kachat.game.Config;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.CaptchaPresenter;
import com.kachat.game.ui.user.register.RegisterActivity;
import com.kachat.game.utils.MyUtils;
import com.kachat.game.utils.OnCheckNetClickListener;
import butterknife.BindView;


public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    public static void newInstance(Context context){
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.cl_Container)
    ConstraintLayout mClContainer;
    @BindView(R.id.acEt_mobile)
    AppCompatEditText mAcEtMobile;

    private CaptchaPresenter mCaptchaPresenter;

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_check_mobile;
    }

    @Override
    protected boolean onSetStatusBar() {
        return false;
    }

    @SuppressLint("CommitTransaction")
    @Override
    protected void onInitView() {
        Config.setMobile("");
        Config.setIsFiguresMask(false);
        mCaptchaPresenter = new CaptchaPresenter(new CheckAccount());
        findViewById(R.id.sdv_go).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                check();
            }
        });
        getLoadView().setOnClickListener(v -> {
            hideLoadView();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mAcEtMobile.setText("15821239216");
//        mAcEtMobile.setText("13585540060");
        mAcEtMobile.setText("13585540054");
    }


    private String mobile;
    private void check() {
        mobile = mAcEtMobile.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            mClContainer.setBackgroundResource(R.drawable.img_bg_login_wrong);
            Toast(R.string.toast_mobile_is_null);
            return;
        }
        if (!MyUtils.checkMobileNumber(mobile)) {
            mClContainer.setBackgroundResource(R.drawable.img_bg_login_wrong);
            Toast(R.string.toast_mobile_format_is_error);
            mAcEtMobile.setText("");
            return;
        }
        showLoadView();
        mCaptchaPresenter.attachPresenter(mobile);
    }

    //0   验证码发送成功   --调到输入验证码
    //10001 用户已注册       --调到登录
    //10002 手机号格式不正确  --提示手机错误
    //10003 发送过快    --这个应该很少会出现
    private class CheckAccount implements OnPresenterListeners.OnViewListener<GetCaptchaBean> {
        @Override
        public void onSuccess(GetCaptchaBean result) {  //不存在,发送验证码
            Toast("验证码：" + result.getCaptcha());
            hideLoadView();
            if (result != null) {
                Config.setMobile(mobile);
                RegisterActivity.newInstance(LoginActivity.this);
                finish();
            }
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            Log.i(TAG, "onFailed: "+error.toString());
            hideLoadView();
            if (errorCode == CodeType.CODE_RESPONSE_INPUT_PWD) {  //  已注册,输入密码登录
                mClContainer.setBackgroundResource(R.drawable.img_bg_login_ok);
                Config.setMobile(mobile);
                CheckPwdActivity.newInstance(LoginActivity.this);
                finish();
            }else {
                mClContainer.setBackgroundResource(R.drawable.img_bg_login_wrong);
                if (error != null && !TextUtils.isEmpty(error.getToast())) {
                    Toast(error.getToast());
                }
            }

        }

        @Override
        public void onError(Throwable e) {
            hideLoadView();
            mClContainer.setBackgroundResource(R.drawable.img_bg_login_wrong);
            if (e != null) {
                Log.i(TAG, "onError: "+e.getMessage());
                Toast(e.getMessage());
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mAcEtMobile.setText("");
    }

    @Override
    public void onDestroy() {
        if (mCaptchaPresenter != null) {
            mCaptchaPresenter.detachPresenter();
            mCaptchaPresenter = null;
        }
        super.onDestroy();
    }


    //记录用户首次点击返回键的时间
    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                Toast("再按一次退出程序");
                firstTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}

