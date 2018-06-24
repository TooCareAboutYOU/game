package com.kachat.game.ui.user.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kachat.game.Config;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.events.services.NetConnectService;
import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.controls.DaoDelete;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.CaptchaPresenter;
import com.kachat.game.ui.user.register.RegisterActivity;
import com.kachat.game.utils.MyUtils;
import com.kachat.game.utils.OnClickListener;

import butterknife.BindView;


public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private static FragmentTransaction mTransaction;

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
        return true;
    }

    @SuppressLint("CommitTransaction")
    @Override
    protected void onInitView() {
        mCaptchaPresenter = new CaptchaPresenter(new CheckAccount());
        findViewById(R.id.sdv_go).setOnClickListener(v-> {
            Check();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAcEtMobile.setText("15000000000");
    }

    String mobile;
    private void Check() {
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

        mCaptchaPresenter.attachPresenter(mobile);

    }

    //0   验证码发送成功   --调到输入验证码
    //10001 用户已注册       --调到登录
    //10002 手机号格式不正确  --提示手机错误
    //10003 发送过快    --这个应该很少会出现
    private class CheckAccount implements OnPresenterListeners.OnViewListener<GetCaptchaBean> {
        @Override
        public void onSuccess(GetCaptchaBean result) {  //不存在,发送验证码
            Toast("验证码：" + result.getResult().getCaptcha());
            if (result.getResult() != null) {
                Config.setMobile(mobile);
                RegisterActivity.newInstance(LoginActivity.this);
                finish();
            }
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            Log.i(TAG, "onFailed: "+error.toString());

            if (errorCode == CodeType.CODE_RESPONSE_INPUT_PWD) {  //  已注册,输入密码登录
                mClContainer.setBackgroundResource(R.drawable.img_bg_login_ok);
                Config.setMobile(mobile);
                CheckPwdActivity.newInstance(LoginActivity.this);
                finish();
            }else {
                mClContainer.setBackgroundResource(R.drawable.img_bg_login_wrong);
                Toast(error.getToast());
            }

        }

        @Override
        public void onError(Throwable e) {
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "返回", Toast.LENGTH_SHORT).show();
        if (DaoQuery.queryUserListSize() == 0) {
            Toast.makeText(this, "退出", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }
    }



}

