package com.kachat.game.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.tv.TvView;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Toast;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.NetworkUtils;
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
import com.kachat.game.utils.OnCheckNetClickListener;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Logger;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onInitView() {
        initMap();
        mStatusPresenter=new SignsStatusPresenter(new SignsStatusCallBack());
        mSignsPresenter=new SignsPresenter(new SignsInCallBack());

        checkLogin();
        findViewById(R.id.sdv_UserLogo).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                MeActivity.newInstance(MainActivity.this);
            }
        });

        findViewById(R.id.sdv_RankingList).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                HomeRankListFragment.getInstance().show(getSupportFragmentManager(),HomeRankListFragment.TAG);
            }
        });
        findViewById(R.id.sdv_SignIn).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (mStatusPresenter != null) {
                    mStatusPresenter.attachPresenter();
                }
            }
        });
        findViewById(R.id.sdv_Shop).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                ShopActivity.newInstance(MainActivity.this);
            }
        });
    }

    private void initMap(){
        RecyclerView recyclerView=findViewById(R.id.rv_Map);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(new MapAdapter());
    }
    private class MapAdapter extends RecyclerView.Adapter<MapAdapter.MapViewHolder> {
        @NonNull
        @Override
        public MapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MapViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_home_map,null));
        }

        @Override
        public void onBindViewHolder(@NonNull MapViewHolder holder, int position) {
            //遗迹
            holder.mSdvRelic.setOnClickListener(new OnCheckNetClickListener() {
                @Override
                public void onMultiClick(View v) {
                    new AlterDialogBuilder(MainActivity.this,new DialogTextView(MainActivity.this,"功能暂未开放，敬请期待!")).hideRootSure();
                }
            });
            //研究院
            holder.mSdvGraduateSchool.setOnClickListener(new OnCheckNetClickListener() {
                @Override
                public void onMultiClick(View v) {
                    GraduateSchoolActivity.newInstance(MainActivity.this);
                }
            });
            holder.mSdvGameTower.setOnClickListener(new OnCheckNetClickListener() {
                @Override
                public void onMultiClick(View v) {
                    GameActivity.newInstance(MainActivity.this);
                }
            });
            holder.mSdvShop.setOnClickListener(new OnCheckNetClickListener() {
                @Override
                public void onMultiClick(View v) {
                    ShopActivity.newInstance(MainActivity.this);
                }
            });
            holder.mSdvScience.setOnClickListener(new OnCheckNetClickListener() {
                @Override
                public void onMultiClick(View v) {
                    new AlterDialogBuilder(MainActivity.this,new DialogTextView(MainActivity.this,"功能暂未开放，敬请期待!")).hideRootSure();
                }
            });
            holder.mSdvBar.setOnClickListener(new OnCheckNetClickListener() {
                @Override
                public void onMultiClick(View v) {
                    MurphyBarActivity.newInstance(MainActivity.this);
                }
            });
        }

        @Override
        public int getItemCount() { return 1; }

        class MapViewHolder extends RecyclerView.ViewHolder{
            SimpleDraweeView mSdvRelic,mSdvGraduateSchool,mSdvGameTower,mSdvShop,mSdvScience,mSdvBar;
            MapViewHolder(View itemView) {
                super(itemView);
                mSdvRelic=itemView.findViewById(R.id.sdv_Relic);
                mSdvGraduateSchool=itemView.findViewById(R.id.sdv_GraduateSchool);
                mSdvGameTower=itemView.findViewById(R.id.sdv_GameTower);
                mSdvShop=itemView.findViewById(R.id.sdv_Shop);
                mSdvScience=itemView.findViewById(R.id.sdv_Science);
                mSdvBar=itemView.findViewById(R.id.sdv_Bar);
            }
        }
    }


    private class SignsStatusCallBack implements OnPresenterListeners.OnViewListener<MessageBean>{
        @SuppressLint("InflateParams")
        @Override
        public void onSuccess(MessageBean result) {
            Log.i(TAG, "onSuccess: "+result.toString());
            Logger(result.toString());
            if (result.getStatus() == 1) {
                AlterDialogBuilder dialogBuilder=new AlterDialogBuilder(MainActivity.this, new DialogTextView(MainActivity.this,"今天已经签到，请明天继续继续!"));
                dialogBuilder.getRootSure().setOnClickListener(v -> dialogBuilder.dismiss());
            }else {
                if (mSignsPresenter != null) {
                    mSignsPresenter.attachPresenter(DeviceUtils.getAndroidID());
                }
            }

        }
        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (error != null && !TextUtils.isEmpty(error.getToast())) {
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
            dialogView(result);
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
                Logger(e.getMessage());
                Toast(e.getMessage());
            }
        }
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    private void dialogView(SingsBean result){

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
