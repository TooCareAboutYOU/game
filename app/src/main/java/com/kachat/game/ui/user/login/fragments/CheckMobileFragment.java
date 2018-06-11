package com.kachat.game.ui.user.login.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.kachat.game.R;
import com.kachat.game.base.BaseFragment;
import com.kachat.game.libdata.CodeType;
import com.kachat.game.libdata.dbmodel.DbUserBean;
import com.kachat.game.libdata.controls.DaoInsert;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.GetCaptchaBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.CaptchaPresenter;
import com.kachat.game.ui.user.login.LoginActivity;
import com.kachat.game.utils.MyUtils;

import butterknife.BindView;

/**
 * 账号验证
 */
public class CheckMobileFragment extends BaseFragment {

    @SuppressLint("StaticFieldLeak")
    private static CheckMobileFragment instance = new CheckMobileFragment();

    public static CheckMobileFragment getInstance() {
        return instance;
    }

    private static final String TAG = "CheckMobileFragment";

    @BindView(R.id.acEt_mobile)
    AppCompatEditText mAcEtMobile;

    private CaptchaPresenter mCaptchaPresenter;

    private LoginActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (LoginActivity) getActivity();
    }

    @Override
    public int onSetResourceLayout() {
        return R.layout.fragment_check_mobile;
    }

    @Override
    public void onInitView(@NonNull View view) {
        mCaptchaPresenter = new CaptchaPresenter(new CheckAccount());
        view.findViewById(R.id.sdv_go).setOnClickListener(v-> Check());

    }

    @Override
    public void onActivityCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mAcEtMobile.setText("15000000000");
    }

    String mobile;
    private void Check() {
        mobile = mAcEtMobile.getText().toString().trim();

        if (TextUtils.isEmpty(mobile)) {
            Toast(R.string.toast_mobile_is_null);
            return;
        }
        if (!MyUtils.checkMobileNumber(mobile)) {
            Toast(R.string.toast_mobile_format_is_error);
            mAcEtMobile.setText("");
            return;
        }

        DaoInsert.insertUser(new DbUserBean(null,"",mobile,"","",
                0,0,0,0,
                0,0,0,"",0,0,0));

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
            if (result != null && result.getResult() != null) {
                mActivity.JumpToRegister();
            }
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            Log.w(TAG, "onFailed: " + errorCode);
            if (error != null) {
                if (errorCode == CodeType.REQUEST_INPUT_PWD) {  //  已注册,输入密码登录
                    mActivity.initInputPwd();
                }else {
                    Toast(error.getToast());
                }
            }
        }

        @Override
        public void onError(Throwable e) {
            if (e != null) {
                Log.e(TAG, "onError: " + e.getMessage());
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
    public void onDestroyView() {
        if (mCaptchaPresenter != null) {
            mCaptchaPresenter.detachPresenter();
            mCaptchaPresenter = null;
        }
        super.onDestroyView();

    }
}
