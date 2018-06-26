package com.kachat.game.ui.user.login;


import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.Config;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.LoginPresenter;
import com.kachat.game.ui.MainActivity;

import java.util.Objects;

import butterknife.BindView;


public class CheckPwdActivity extends BaseActivity {

    private static final String TAG = "CheckPwdActivity";

    public static void newInstance(Context context){
        Intent intent=new Intent(context,CheckPwdActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.cl_Container)
    ConstraintLayout mClContainer;
    @BindView(R.id.acTv_ErrorMsg)
    AppCompatTextView mAcTvErrorMsg;
    @BindView(R.id.acTv_mobile)
    AppCompatTextView mAcTvMobile;
    @BindView(R.id.sdv_go)
    SimpleDraweeView mSdvGo;
    @BindView(R.id.acEt_pwd)
    AppCompatEditText mAcEtPwd;

    private LoginPresenter mPresenter;

    @Override
    public int onSetResourceLayout() {
        return R.layout.activity_check_pwd;
    }

    @Override
    protected boolean onSetStatusBar() {
        return false;
    }

    @Override
    protected void onInitView() {
        String mobile= Config.getMobile();
        Log.i(TAG, "onInitView: "+mobile);
        mAcTvMobile.setText(mobile);
        mAcEtPwd.setText("qwer1234");

        mPresenter=new LoginPresenter(new LoginCallBack());

        findViewById(R.id.sdv_go).setOnClickListener(v->{
            String pwd=mAcEtPwd.getText().toString();
            if (TextUtils.isEmpty(pwd)) {
                mClContainer.setBackgroundResource(R.drawable.img_bg_login_wrong);
                Toast(R.string.toast_pwd_is_null);
                return;
            }
            mPresenter.attachPresenter(mobile,pwd);
            mSdvGo.setEnabled(false);
        });
    }

    private class LoginCallBack implements OnPresenterListeners.OnViewListener<UserBean>{

        @Override
        public void onSuccess(UserBean result) {
            Log.i(TAG, "onSuccess: "+result.toString());
            mClContainer.setBackgroundResource(R.drawable.img_bg_login_ok);
            // TODO: 2018/6/5 保存用户信息大本地数据库
            Log.i(TAG, "onSuccess: "+result.toString());
            mAcTvErrorMsg.setVisibility(View.GONE);
            mSdvGo.setEnabled(true);
            MainActivity.getInstance(CheckPwdActivity.this);
            finish();
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            Log.i(TAG, "onFailed: "+error.getToast());
            mClContainer.setBackgroundResource(R.drawable.img_bg_login_wrong);
            mSdvGo.setEnabled(true);
            if (error != null) {
                Log.i(TAG, "onFailed: "+errorCode+"\t\t"+error.getToast());
                mAcTvErrorMsg.setVisibility(View.VISIBLE);
                mAcTvErrorMsg.setText(error.getToast());
                Toast(error.getToast());
            }
        }

        @Override
        public void onError(Throwable e) {
            Log.i(TAG, "onError: "+e.getMessage());
            mClContainer.setBackgroundResource(R.drawable.img_bg_login_wrong);
            mSdvGo.setEnabled(true);
            if (e != null) {
                Log.e(TAG, "onError: "+e.getMessage());
                Toast(e.getMessage());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoginActivity.newInstance(this);
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAcTvErrorMsg.setText("");
        mAcTvMobile.setText("");
        mAcEtPwd.setText("");
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachPresenter();
            mPresenter=null;
        }
        super.onDestroy();
    }

}
