package com.kachat.game.ui.user.login.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseFragment;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.LoginPresenter;
import com.kachat.game.ui.user.login.LoginActivity;

import java.util.Objects;

import butterknife.BindView;

/**
 * 账号存在 ，输入密码登录
 */
public class InputPwdFragment extends BaseFragment {

    private static final String TAG = "InputPwdFragment";

    @SuppressLint("StaticFieldLeak")
    private static InputPwdFragment instance = new InputPwdFragment();
    public static InputPwdFragment getInstance() {  return instance;  }

    @BindView(R.id.acTv_ErrorMsg)
    AppCompatTextView mAcTvErrorMsg;
    @BindView(R.id.acTv_mobile)
    AppCompatTextView mAcTvMobile;
    @BindView(R.id.sdv_go)
    SimpleDraweeView mSdvGo;
    @BindView(R.id.acEt_pwd)
    AppCompatEditText mAcEtPwd;

    private LoginPresenter mPresenter;

    private LoginActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity= (LoginActivity) getActivity();
    }

    @Override
    public int onSetResourceLayout() {
        return R.layout.fragment_input_pwd;
    }

    @Override
    public void onInitView(@NonNull View view) {
        String mobile= Objects.requireNonNull(DaoQuery.queryUserData()).getMobile();
        mAcTvMobile.setText(mobile);
        mAcEtPwd.setText("qwer1234");

        mPresenter=new LoginPresenter(new LoginCallBack());

        view.findViewById(R.id.back_task).setOnClickListener(v -> Objects.requireNonNull(getFragmentManager()).popBackStack());

        view.findViewById(R.id.sdv_go).setOnClickListener(v->{
            String pwd=mAcEtPwd.getText().toString();
            if (TextUtils.isEmpty(pwd)) {
                Toast(R.string.toast_pwd_is_null);
                return;
            }
            Log.i(TAG, "onMultiClick: "+mobile+"\t\t"+pwd);
            mPresenter.attachPresenter(mobile,pwd);
            mSdvGo.setEnabled(false);
        });
    }

    private class LoginCallBack implements OnPresenterListeners.OnViewListener<UserBean>{

        @Override
        public void onSuccess(UserBean result) {
            // TODO: 2018/6/5 保存用户信息大本地数据库
            Log.i(TAG, "onSuccess: "+result.toString());
            mAcTvErrorMsg.setVisibility(View.GONE);
            mSdvGo.setEnabled(true);
            mActivity.JumpToMain();
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
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
            mSdvGo.setEnabled(true);
            if (e != null) {
                Log.e(TAG, "onError: "+e.getMessage());
                Toast(e.getMessage());
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mAcTvErrorMsg.setText("");
        mAcTvMobile.setText("");
        mAcEtPwd.setText("");
        mActivity=null;
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachPresenter();
            mPresenter=null;
        }
        super.onDestroyView();
    }

}
