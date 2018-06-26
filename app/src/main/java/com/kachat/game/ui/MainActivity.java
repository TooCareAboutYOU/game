package com.kachat.game.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.tv.TvView;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.blankj.utilcode.util.DeviceUtils;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.SdkApi;
import com.kachat.game.events.DNGameEventMessage;
import com.kachat.game.events.PublicEventMessage;
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
import com.kachat.game.ui.bar.MurphyBarActivity;
import com.kachat.game.ui.fragments.HomeRankListFragment;
import com.kachat.game.ui.game.GameActivity;
import com.kachat.game.ui.graduate.GraduateSchoolActivity;
import com.kachat.game.ui.shop.ShopActivity;
import com.kachat.game.ui.user.MeActivity;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.fl_Root)
    FrameLayout rootView;
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
        mStatusPresenter=new SignsStatusPresenter(new SignsStatusCallBack());
        mSignsPresenter=new SignsPresenter(new SignsInCallBack());
        checkLogin();
    }

    public void onGameClick(View v){ GameActivity.newInstance(this); }

    public void onSignClick(View v){ GraduateSchoolActivity.newInstance(this); }

    public void onBarClick(View view) { MurphyBarActivity.newInstance(this); }

    @OnClick({R.id.sdv_UserLogo, R.id.sdv_RankingList, R.id.sdv_SignIn, R.id.sdv_Shop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sdv_UserLogo:
                MeActivity.newInstance(this);
//                TestActivity.newInstance(this);
                break;
            case R.id.sdv_RankingList:
                HomeRankListFragment.getInstance().show(getSupportFragmentManager(),HomeRankListFragment.TAG);
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
        EventBus.getDefault().register(this);
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

    @SuppressLint({"CheckResult","InflateParams"})
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
        Log.i(TAG, "checkLogin: ");
        SdkApi.getInstance().sdkLogin(mDbUserBean.getUid(), mDbUserBean.getToken());
    }

    int broken=0;
    @SuppressLint("InflateParams")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DNGameEventMessage event) {
        switch (event.getEvent()) {
            case SESSION_BROKEN: {
                Log.i(TAG, "onEvent: SESSION_BROKEN");
                broken++;
                if (broken==7) {
                    AlterDialogBuilder dialogOccupy=new AlterDialogBuilder(this, new DialogTextView(this, "连接异常，请重新登录！"),"退出").hideClose();
                    dialogOccupy.getRootSure().setOnClickListener(v -> {
                        broken=0;
                        dialogOccupy.dismiss();
                        PublicEventMessage.ExitAccount(this);
                        finish();
                    });
                }
                break;
            }
            case SESSION_OCCUPY: {
                Log.i(TAG, "onEvent: SESSION_OCCUPY");
                AlterDialogBuilder dialogOccupy=new AlterDialogBuilder(this, new DialogTextView(this, "账号异地登录，请重新登录！"),"退出").hideClose();
                dialogOccupy.getRootSure().setOnClickListener(v -> {
                    dialogOccupy.dismiss();
                    PublicEventMessage.ExitAccount(this);
                    finish();
                });
                break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void onDestroy() {

        if (mStatusPresenter != null) {
            mStatusPresenter.detachPresenter();
            mStatusPresenter=null;
        }
        if (mSignsPresenter!= null) {
            mSignsPresenter.detachPresenter();
            mSignsPresenter=null;
        }
        super.onDestroy();
        SdkApi.getInstance().sdkExit();
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        broken=0;
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
