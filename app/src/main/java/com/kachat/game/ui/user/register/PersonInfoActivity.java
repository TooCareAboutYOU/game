package com.kachat.game.ui.user.register;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.Config;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.UserBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.LoginPresenter;
import com.kachat.game.libdata.mvp.presenters.RegisterPresenter;
import com.kachat.game.ui.MainActivity;
import com.kachat.game.ui.user.login.LoginActivity;
import com.kachat.game.utils.OnMultiClickListener;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;
import com.kachat.game.utils.widgets.wheelview.BottomDialog;
import com.kachat.game.utils.widgets.wheelview.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;

public class PersonInfoActivity extends BaseActivity {

    private static final String TAG = "PersonInfoActivity";

    @BindView(R.id.sdv_ToolBar_Base_Back)
    SimpleDraweeView mSdvToolBarBaseBack;
    @BindView(R.id.atv_ToolBar_Base_Title)
    AppCompatTextView mAtvToolBarBaseTitle;
    @BindView(R.id.acEt_NewNickName)
    AppCompatEditText mAcEtNewNickName;
    @BindView(R.id.datePicker)
    AppCompatTextView mDatePicker;
    @BindView(R.id.rBtn_Women)
    AppCompatRadioButton mRBtnWomen;
    @BindView(R.id.rBtn_Men)
    AppCompatRadioButton mRBtnMen;
    @BindView(R.id.rg_Sex)
    RadioGroup mRgSex;
    @BindView(R.id.acCb_Agreement)
    AppCompatCheckBox mAcCbAgreement;
    @BindView(R.id.acEt_PassWord)
    AppCompatEditText mAcEtPassWord;
    @BindView(R.id.acIv_Register)
    AppCompatTextView mAcIvRegister;
    @BindView(R.id.toolbar_base)
    Toolbar mToolbarBase;

    private int userAge = 0;
    private String gender = "female";
    private RegisterPresenter mRegisterPresenter;
    private LoginPresenter mPresenter;


    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_person_info;
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
        mToolbarBase.setBackgroundResource(R.color.colorNormal);
        getToolBarBack().setOnClickListener(v -> finish());

        mRegisterPresenter = new RegisterPresenter(new RegisterCallBack());

        mPresenter = new LoginPresenter(new LoginCallBack());

        findViewById(R.id.sdv_ToolBar_Base_Back).setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                LoginActivity.newInstance(PersonInfoActivity.this);
                finish();
            }
        });

        mDatePicker.setOnClickListener(v -> showWheelView());

        mAcIvRegister.setOnClickListener(v -> {
            String name = mAcEtNewNickName.getText().toString();
            String pwd = mAcEtPassWord.getText().toString();
            Log.i(TAG, "onMultiClick: " + name + "\t\t" + pwd);
            if (Check(name, pwd)) {
                String mobile = Config.getMobile();
                Log.i(TAG, "onMultiClick: " + "mobile");
                mRegisterPresenter.attachPresenter(mobile, pwd, gender, userAge + "", name);
            }
        });
    }

    private boolean Check(String name, String pwd) {
        if (TextUtils.isEmpty(name)) {
            Toast("昵称不能为空!");
            return false;
        }

        if (TextUtils.isEmpty(pwd)) {
            Toast("密码不能为空!");
            return false;
        }

        if (!mAcCbAgreement.isChecked()) {
            Toast("请勾选用户协议");
            return false;
        }

        if (mRgSex.getCheckedRadioButtonId() == R.id.rBtn_Women) {
            gender = "female";
        } else {
            gender = "male";
        }

        return true;
    }


    private class RegisterCallBack implements OnPresenterListeners.OnViewListener<UserBean> {
        @Override
        public void onSuccess(UserBean result) {
            Log.i(TAG, "onSuccess: " + result.toString());
            if (result.getResult() != null) {
                if (result.getResult().getUser().getDetail() != null) {
                    SuccessDialog();
                }
            }

        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            Log.i(TAG, "onFailed: " + error);
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

    private void SuccessDialog() {
        AlterDialogBuilder dialogBuilder=new AlterDialogBuilder(PersonInfoActivity.this, new DialogTextView(PersonInfoActivity.this,"恭喜你注册成功!"));
        dialogBuilder.getRootSure().setOnClickListener(v1 ->dialogBuilder.dismiss());
    }

    // TODO: 2018/6/11 自动跳转登录
    private void AutoLogin(AlterDialogBuilder alterDialog) {
        String mobile = Config.getMobile();
        mPresenter.attachPresenter(mobile, mAcEtPassWord.getText().toString());
        alterDialog.dismiss();
    }

    private class LoginCallBack implements OnPresenterListeners.OnViewListener<UserBean> {

        @Override
        public void onSuccess(UserBean result) {
            if (result != null) {
                MainActivity.getInstance(PersonInfoActivity.this);
                PersonInfoActivity.this.finish();
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

    BottomDialog mBottomDialog;
    View mView;

    @SuppressLint({"SetTextI18n", "CutPasteId", "InflateParams"})
    public void showWheelView() {
        mView = LayoutInflater.from(this).inflate(R.layout.dialog_wheel_choice_age, null);
        WheelView wheelview = mView.findViewById(R.id.wheel_view);
        TextView tvCancel = mView.findViewById(R.id.tv_cancel);
        TextView tvSure = mView.findViewById(R.id.tv_sure);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1950; i <= 2009; i++) {
            list.add(i + "");
        }
        wheelview.setItems(list, list.size() - 1);

        wheelview.setOnItemSelectedListener((selectedIndex, item) -> {
            int year = Integer.parseInt(item.substring(0, 4));
            Calendar c = Calendar.getInstance();
            int currentYear = c.get(Calendar.YEAR); // 获取当前年份
            userAge = currentYear - year;
        });

        tvSure.setOnClickListener(v -> {
            mDatePicker.setText(userAge + "");
            mBottomDialog.dismiss();
        });

        tvCancel.setOnClickListener(v -> mBottomDialog.dismiss());

        //防止弹出两个窗口
        if (mBottomDialog != null && mBottomDialog.isShowing()) {
            return;
        }
        mBottomDialog = new BottomDialog(this, R.style.ActionSheetDialogStyle);
        //将布局设置给Dialog
        mBottomDialog.setContentView(mView);
        mBottomDialog.setCanceledOnTouchOutside(false);
        mBottomDialog.show();
    }

    @Override
    public void onBackPressed() {
        LoginActivity.newInstance(PersonInfoActivity.this);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (mRegisterPresenter != null) {
            mRegisterPresenter.detachPresenter();
            mRegisterPresenter = null;
        }
        super.onDestroy();
    }
}
