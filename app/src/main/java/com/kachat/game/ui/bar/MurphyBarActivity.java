package com.kachat.game.ui.bar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.dnion.VAGameAPI;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.Config;
import com.kachat.game.Constant;
import com.kachat.game.R;
import com.kachat.game.SdkApi;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.events.DNGameEventMessage;
import com.kachat.game.events.PublicEventMessage;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.dbmodel.DbLive2DBean;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.RankingListBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.CharmRankPresenter;
import com.kachat.game.ui.bar.fragments.CharmRankListFragment;
import com.kachat.game.ui.game.GameActivity;
import com.kachat.game.ui.game.GameRoomActivity;
import com.kachat.game.utils.OnCheckNetClickListener;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lemon.view.SpaceItemDecoration;

public class MurphyBarActivity extends BaseActivity {

    private static final String TAG = "MurphyBarActivity";

    @BindView(R.id.fl_parent)
    FrameLayout mLayout;
    @BindView(R.id.toolbar_base)
    Toolbar mToolbar;

    @BindView(R.id.fl_LoadingView)
    FrameLayout mFlLoading;
    @BindView(R.id.fl_ChatView)
    FrameLayout mContainer;
    @BindView(R.id.fl_RemoteView)
    FrameLayout mRemoteView;
    @BindView(R.id.fl_LocalView)
    FrameLayout mLocalView;
    @BindView(R.id.fl_Box)
    FrameLayout mBoxView;

    private AppCompatTextView mAcTvTimer;

    private int register=0;
    private boolean isStart=false,timerRunning=false;
   

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, MurphyBarActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int onSetResourceLayout() { return R.layout.activity_murphy_bar; }

    @Override
    protected boolean onSetStatusBar() { return true; }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (getImmersionBar() != null) {
            getImmersionBar().navigationBarColor(R.color.black).titleBar(mToolbar).transparentStatusBar().init();
        }
    }

    @SuppressLint("InflateParams")
    @Override
    protected void onInitView() {
        setToolBarTitle("");
        getToolBarTitle().setVisibility(View.GONE);
        getToolBarBack().setOnClickListener(v -> {
            if (mFlLoading.getVisibility() == View.VISIBLE) {
                Log.i(TAG, "onInitView: VISIBLE");
                mFlLoading.setVisibility(View.GONE);
                mContainer.setVisibility(View.GONE);
                removeView();
                isStart=false;
            } else {
                if (isStart) {
                    Log.i(TAG, "onInitView: GONE--> true");
                    mFlLoading.setVisibility(View.GONE);
                    mContainer.setVisibility(View.GONE);
                    removeView();
                    isStart=false;
                    return;
                }
                Log.i(TAG, "onInitView: GONE--> false");
                finish();
            }
        });

        mAcTvTimer =findViewById(R.id.acTv_Timer);
        mBoxView.setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (mAcTvTimer.getVisibility() == View.GONE) {

                }
            }
        });

        findViewById(R.id.sdv1).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                Log.i(TAG, "onMultiClick: ");
                if (Config.getIsFiguresMask()) {
                    mFlLoading.setVisibility(View.VISIBLE);
                    SdkApi.getInstance().create(MurphyBarActivity.this);
                    SdkApi.getInstance().loadLocalView(MurphyBarActivity.this, mLocalView);
                    SdkApi.getInstance().loadRemoteView(MurphyBarActivity.this, mRemoteView);
                    SdkApi.getInstance().enableVideoView();
                    DbLive2DBean dbLive2DBean = Objects.requireNonNull(DaoQuery.queryModelListData()).get(0);
                    SdkApi.getInstance().loadFaceRigItf(dbLive2DBean.getLiveFilePath(), dbLive2DBean.getLiveFileName(), dbLive2DBean.getBgFilePath(), dbLive2DBean.getBgFileName(), Constant.MATCH_TYPE_CHAT);
                    SdkApi.getInstance().startGameMatch(Constant.MATCH_TYPE_CHAT);
                    isStart=true;
                }else {
                    new AlterDialogBuilder(MurphyBarActivity.this,new DialogTextView(MurphyBarActivity.this,"暂无人物形象，请前往 '研究院' 创建人物！")).hideRootSure();
                }
            }
        });


        findViewById(R.id.sdv2).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                CharmRankListFragment.getInstance().show(getSupportFragmentManager(),CharmRankListFragment.TAG);
            }
        });

        findViewById(R.id.sdv_Report).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {

            }
        });
    }

    private CountDownTimer mCount=new CountDownTimer(30000,1000) {
        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long currentTime) {
            mAcTvTimer.setText(currentTime/1000+"s");
            timerRunning=true;
        }

        @Override
        public void onFinish() {
            timerRunning=false;
            mAcTvTimer.setVisibility(View.GONE);
            mBoxView.setBackgroundResource(R.drawable.icon_bar_box_opened);
            
        }
    };

    @SuppressLint("InflateParams")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DNGameEventMessage event) {
        switch (event.getEvent()) {
            case SESSION_READY:
                Log.i(TAG, "onEvent: SESSION_READY");
                break;
            case SESSION_BROKEN:
//                if (NetworkUtils.isConnected()) {
//                    if (!NetworkUtils.isAvailableByPing()) {
//                        ToastUtils.showShort("当前网络不可用！");
//                    }
//                }else {
//                    if (mRemoteView.getChildCount() > 0) {
//                        mRemoteView.removeAllViews();
//                    }
//                    ToastUtils.showShort("网络已断开！");
//                }
                Log.i(TAG, "onEvent: SESSION_BROKEN");
                register++;
                if (register == 7) {
                    AlterDialogBuilder dialogBroken=new AlterDialogBuilder(this,"退出",new DialogTextView(MurphyBarActivity.this,"连接异常，请重新登录！")).hideClose();
                    dialogBroken.getRootSure().setOnClickListener(v -> {
                        dialogBroken.dismiss();
                        PublicEventMessage.ExitAccount(this);
                        finish();
                    });
                }
                break;
            case SESSION_OCCUPY:
                Log.i(TAG, "onEvent: SESSION_OCCUPY");
                AlterDialogBuilder dialogOccupy=new AlterDialogBuilder(this, new DialogTextView(this, "账号异地登录，请重新登录！"),"退出").hideClose();
                dialogOccupy.getRootSure().setOnClickListener(v -> {
                    dialogOccupy.dismiss();
                    PublicEventMessage.ExitAccount(this);
                    finish();
                });
                break;
            case SESSION_KEEP_ALIVE:
                Log.i(TAG, "onEvent: SESSION_KEEP_ALIVE");
                break;
            case JOIN_SUCCESS:
                Log.i(TAG, "onEvent: JOIN_SUCCESS");
                register=0;
                mFlLoading.setVisibility(View.GONE);
                mContainer.setVisibility(View.VISIBLE);
                break;
            case JOIN_FAILED:
                Log.i(TAG, "onEvent: JOIN_FAILED");
                break;
            case MATCH_SUCCESS:
                Log.i(TAG, "onEvent: MATCH_SUCCESS");
                break;
            case GAME_MESSAGE:
                Log.i(TAG, "onEvent: GAME_MESSAGE");
                break;
            case GAME_STAT:
                Log.i(TAG, "onEvent: GAME_STAT");
                break;
            case VIDEO_CHAT_START:
                Log.i(TAG, "onEvent: VIDEO_CHAT_START");
                mCount.start();
                break;
            case VIDEO_CHAT_FINISH:
                Log.i(TAG, "onEvent: VIDEO_CHAT_FINISH");
                break;
            case VIDEO_CHAT_TERMINATE:
                Log.i(TAG, "onEvent: VIDEO_CHAT_TERMINATE");
//                mFlLoading.setVisibility(View.GONE);
//                mContainer.setVisibility(View.GONE);
//                AlterDialogBuilder dialogBuilder1=new AlterDialogBuilder(MurphyBarActivity.this,new DialogTextView(MurphyBarActivity.this,"对方已下线！！！")).hideClose();
//                dialogBuilder1.getRootSure().setOnClickListener(v -> {
//                    isStart=false;
//                    dialogBuilder1.dismiss();
//                    removeView();
//                });
                break;
            case VIDEO_CHAT_FAIL:  //流断
                Log.i(TAG, "onEvent: VIDEO_CHAT_FAIL");
                AlterDialogBuilder dialogFail=new AlterDialogBuilder(this,new DialogTextView(MurphyBarActivity.this,"对方已下线!")).hideClose();
                dialogFail.getRootSure().setOnClickListener(v -> {
                    mFlLoading.setVisibility(View.GONE);
                    mContainer.setVisibility(View.GONE);
                    isStart=false;
                    dialogFail.dismiss();
                    removeView();
                });
                break;
            case GOT_GIFT:
                Log.i(TAG, "onEvent: GOT_GIFT");
                break;
            case ERROR_MESSAGE:
                Log.i(TAG, "onEvent: ERROR_MESSAGE");
                break;
        }
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        
        if (isStart) {
            Log.i(TAG, "onDestroy: ->>>");
            removeView();
        }
        
        super.onDestroy();
    }

    private void removeView(){
        if (timerRunning) {
            mCount.cancel();
            timerRunning=false;
        }
        register=0;
        SdkApi.getInstance().destroy(true);
        if (mRemoteView.getChildCount() > 0) {
            Log.i(TAG, "removeView: ");
            mRemoteView.removeAllViews();
        }
        if (mLocalView.getChildCount() > 0){
            mLocalView.removeAllViews();
        }
    }
}
