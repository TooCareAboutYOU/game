package com.kachat.game.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.DeviceUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.base.videos.SdkApi;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.dbmodel.DbUserBean;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.RankingListBean;
import com.kachat.game.libdata.model.SingsBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.ExperienceRankPresenter;
import com.kachat.game.libdata.mvp.presenters.SignsPresenter;
import com.kachat.game.libdata.mvp.presenters.SignsStatusPresenter;
import com.kachat.game.ui.fragments.HomeRankListFragment;
import com.kachat.game.ui.graduate.GraduateSchoolActivity;
import com.kachat.game.ui.shop.ShopActivity;
import com.kachat.game.ui.user.MeActivity;
import com.kachat.game.ui.user.login.LoginActivity;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar_Home)
    Toolbar mToolbar;
    @BindView(R.id.sdv_UserLogo)
    SimpleDraweeView mSdvUserLogo;
    @BindView(R.id.acTv_UserName)
    AppCompatTextView mAcTvUserName;
    @BindView(R.id.acTv_UserLevel)
    AppCompatTextView mAcTvUserLevel;
    @BindView(R.id.acTv_UserSport)
    AppCompatTextView mAcTvUserSport;
    @BindView(R.id.acTv_UserGold)
    AppCompatTextView mAcTvUserGold;
    @BindView(R.id.acTv_UserDiamonds)
    AppCompatTextView mAcTvUserDiamonds;

    private List<RankingListBean.ResultBean.RanksBean> mRankList = null;
    private ExperienceRankPresenter mPresenter = null;

    private SignsStatusPresenter mStatusPresenter=null;
    private SignsPresenter mSignsPresenter=null;

    public static void getInstance(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private DbUserBean mDbUserBean = DaoQuery.queryUserData();

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean onSetStatusBar() {
        return true;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (getImmersionBar() != null) {
            getImmersionBar().titleBar(mToolbar).transparentStatusBar().init();
        }
    }


    @Override
    protected void onInitView() {
        mRankList = new ArrayList<>();
        mPresenter = new ExperienceRankPresenter(new ExpLevelCallBack());
        mStatusPresenter=new SignsStatusPresenter(new SignsStatusCallBack());
        mSignsPresenter=new SignsPresenter(new SignsInCallBack());
//        findViewById(R.id.btn_graduate).setOnClickListener(v -> {
//            GraduateSchoolActivity.newInstance(this);
//        });
//
//        findViewById(R.id.btn_Live2D).setOnClickListener(v -> {
//            ChatActivity.newInstance(this);
//        });
//
//        findViewById(R.id.btn_H5Game).setOnClickListener(v -> {
//            GameActivity.newInstance(this);
//        });
//        findViewById(R.id.btn_Me).setOnClickListener(v -> {
//            MeActivity.newInstance(this);
//        });
//        findViewById(R.id.btn_Shop).setOnClickListener(v -> {
//            ShopActivity.newInstance(this);
//            for (int i = 1; i < 11; i++) {
//                UpLoadBugLogService.writeLog(Process.myPid(),Process.myTid(), UpLoadBugLogService.DeBugType.info,"这是第"+i+"条新增信息");
//            }
//            UpLoadBugLogService.toZip(Objects.requireNonNull(DaoQuery.queryUserData()).getMobile());
//        });
        checkLogin();
    }



    @OnClick({R.id.sdv_UserLogo, R.id.sdv_RankingList, R.id.sdv_SignIn, R.id.sdv_Shop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sdv_UserLogo:
//                MeActivity.newInstance(this);
                GraduateSchoolActivity.newInstance(this);
                break;
            case R.id.sdv_RankingList:
                if (mPresenter != null) {
                    mPresenter.attachPresenter();
                }
                break;
            case R.id.sdv_SignIn:
                if (mStatusPresenter != null) {
                    mStatusPresenter.attachPresenter();
                }
                break;
            case R.id.sdv_Shop:
                ShopActivity.newInstance(this);
                break;
        }
    }

    private class ExpLevelCallBack implements OnPresenterListeners.OnViewListener<RankingListBean> {

        @Override
        public void onSuccess(RankingListBean result) {
            Log.i(TAG, "onSuccess: "+result.toString());
            if (result.getResult() != null && result.getResult().getRanks() != null && result.getResult().getRanks().size() > 0) {
                HomeRankListFragment.getInstance("等级", result.getResult().getRanks()).show(getSupportFragmentManager(), HomeRankListFragment.TAG);
            } else {
                HomeRankListFragment.getInstance("等级", mRankList).show(getSupportFragmentManager(), HomeRankListFragment.TAG);
            }

        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (error != null) {
                Logger(error.getToast());
                Toast(error.getToast());
            }
        }

        @Override
        public void onError(Throwable e) {
            if (e != null) {
                Logger(e.getMessage());
                Toast(e.getMessage());
            }
        }
    }

    private class SignsStatusCallBack implements OnPresenterListeners.OnViewListener<MessageBean>{
        @SuppressLint("InflateParams")
        @Override
        public void onSuccess(MessageBean result) {
            Log.i(TAG, "onSuccess: "+result.toString());
            if (result.getResult() != null) {
                if (result.getResult().getStatus() == 1) {
                    AlterDialogBuilder dialogBuilder=new AlterDialogBuilder(MainActivity.this, new DialogTextView(MainActivity.this,"今天已经签到，请明天继续继续!"));
                    dialogBuilder.getRootSure().setOnClickListener(v -> dialogBuilder.dismiss());
                }else {
                    if (mSignsPresenter != null) {
                        mSignsPresenter.attachPresenter(DeviceUtils.getAndroidID());
                    }
                }
            }
        }
        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (error != null) {
                Logger(error.getToast());
                Toast(error.getToast());
            }
        }
        @Override
        public void onError(Throwable e) {
            if (e != null) {
                Logger(e.getMessage());
                Toast(e.getMessage());
            }
        }
    }

    private class SignsInCallBack implements OnPresenterListeners.OnViewListener<SingsBean>{
        @SuppressLint("InflateParams")
        @Override
        public void onSuccess(SingsBean result) {
            Log.i(TAG, "onSuccess: "+result.toString());
            if (result.getResult() != null) {
                dialogView(result.getResult());
            }
        }
        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (error != null) {
                Logger(error.getToast());
                Toast(error.getToast());
            }
        }
        @Override
        public void onError(Throwable e) {
            if (e != null) {
                Logger(e.getMessage());
                Toast(e.getMessage());
            }
        }
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    private void dialogView(SingsBean.ResultBean result){

        AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.AlertDialogStyle);
        builder.setCancelable(false);
        builder.create();

        View view=LayoutInflater.from(this).inflate(R.layout.layout_signin,null);
        SimpleDraweeView closeView=view.findViewById(R.id.acIV_Close);
        AppCompatTextView info=view.findViewById(R.id.acTv_Info);
        builder.setView(view);

        AlertDialog dialog=builder.show();

        closeView.setOnClickListener(v-> dialog.dismiss());

        info.setText(result.getDiamond()+"钻石\n"+
                result.getGold()+"金币\n"+
                result.getHp()+"体力\n"+
                result.getExp()+"经验");
    }



    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
        DbUserBean dbUserBean = DaoQuery.queryUserData();
        if (dbUserBean != null) {
            mAcTvUserName.setText(dbUserBean.getUsername());
            if (dbUserBean.getGender().equals("male")) {
                mSdvUserLogo.setBackgroundResource(R.drawable.icon_male_logo);
            } else {
                mSdvUserLogo.setBackgroundResource(R.drawable.icon_female_logo);
            }
            mAcTvUserLevel.setText("LV" + dbUserBean.getLevel());
            mAcTvUserDiamonds.setText("钻石:" + dbUserBean.getDiamond());
            mAcTvUserGold.setText("金币:" + dbUserBean.getGold());
            mAcTvUserSport.setText("体力:" + dbUserBean.getCharm());
        }
    }


    private void checkLogin() {
        Log.i(TAG, "初始化连接一次: ");
        if (mDbUserBean == null) {
            Toast.makeText(this, "个人信息获取失败", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mDbUserBean.getUid() == 0 || TextUtils.isEmpty(mDbUserBean.getToken())) {
            Toast.makeText(this, "用户账号异常", Toast.LENGTH_SHORT).show();
            return;
        }
        SdkApi.getInstance().sdkLogin(mDbUserBean.getUid(), mDbUserBean.getToken());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachPresenter();
            mPresenter = null;
        }

        if (mStatusPresenter != null) {
            mStatusPresenter.detachPresenter();
            mStatusPresenter=null;
        }
        if (mSignsPresenter!= null) {
            mSignsPresenter.detachPresenter();
            mSignsPresenter=null;
        }

        super.onDestroy();
//        KaChatApplication.getInstance().stop();
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            } else {
                SdkApi.getInstance().sdkExit();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
