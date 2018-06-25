package com.kachat.game.ui.bar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.Config;
import com.kachat.game.R;
import com.kachat.game.SdkApi;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.events.DNGameEventMessage;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.dbmodel.DbLive2DBean;
import com.kachat.game.ui.game.GameActivity;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import butterknife.BindView;

public class MurphyBarActivity extends BaseActivity {

    private static final String TAG = "MurphyBarActivity";

    @BindView(R.id.toolbar_base)
    Toolbar mToolbar;

    private boolean isLoadVideo;
    @BindView(R.id.fl_LoadingView)
    FrameLayout mFlLoading;
    @BindView(R.id.fl_ChatView)
    FrameLayout mContainer;
    @BindView(R.id.fl_RemoteView)
    FrameLayout mRemoteView;
    @BindView(R.id.fl_LocalView)
    FrameLayout mLocalView;


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

    @Override
    protected void onInitView() {
        setToolBarTitle("");
        getToolBarTitle().setVisibility(View.GONE);
        getToolBarBack().setOnClickListener(v -> {
            if (isLoadVideo) {
                mFlLoading.setVisibility(View.GONE);
                mContainer.setVisibility(View.GONE);
                SdkApi.getInstance().destroy(isLoadVideo);
                isLoadVideo=!isLoadVideo;
            }else {
                finish();
            }
        });

    }

    public void onStartMatchClick(View v){
        if (Config.getFirst() == 200) {
            isLoadVideo=!isLoadVideo;
            mFlLoading.setVisibility(View.VISIBLE);
            SdkApi.getInstance().create(this);
            SdkApi.getInstance().loadLocalView(this, mLocalView);
            SdkApi.getInstance().loadRemoteView(this, mRemoteView);
            SdkApi.getInstance().enableVideoView();
            DbLive2DBean dbLive2DBean = Objects.requireNonNull(DaoQuery.queryModelListData()).get(0);
            SdkApi.getInstance().loadFaceRigItf(dbLive2DBean.getLiveFilePath(), dbLive2DBean.getLiveFileName(),
                    dbLive2DBean.getBgFilePath(), dbLive2DBean.getBgFileName(), 3);
            SdkApi.getInstance().startGameMatch(3);
        }else {
            new AlterDialogBuilder(MurphyBarActivity.this,new DialogTextView(MurphyBarActivity.this,"暂无人物形象，请前往 '研究院' 创建人物！")).hideRootSure();
            return;
        }
    }

    @SuppressLint("InflateParams")
    public void onRankListClick(View v){
        View rankView=LayoutInflater.from(this).inflate(R.layout.layout_bar_charmlist,null);
        new AlterDialogBuilder(MurphyBarActivity.this,"魅力",rankView).hideRootSure();
    }

    @SuppressLint("InflateParams")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DNGameEventMessage event) {
        switch (event.getEvent()) {
            case SESSION_READY:
                Logger("onEvent:SESSION_READY");
                break;
            case SESSION_BROKEN:
                Log.i(TAG, "onEvent: SESSION_BROKEN");
                break;
            case SESSION_OCCUPY:
                Log.i(TAG, "onEvent: SESSION_OCCUPY");
                break;
            case SESSION_KEEP_ALIVE:
                Log.i(TAG, "onEvent: SESSION_KEEP_ALIVE");
                break;
            case JOIN_SUCCESS:
                Log.i(TAG, "onEvent: JOIN_SUCCESS");
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
                mFlLoading.setVisibility(View.GONE);
                mContainer.setVisibility(View.VISIBLE);
                break;
            case VIDEO_CHAT_FINISH:
                Log.i(TAG, "onEvent: VIDEO_CHAT_FINISH");
                break;
            case VIDEO_CHAT_TERMINATE:
                Log.i(TAG, "onEvent: VIDEO_CHAT_TERMINATE");
                AlterDialogBuilder dialogBuilder=new AlterDialogBuilder(MurphyBarActivity.this,new DialogTextView(MurphyBarActivity.this,"对方已下线！！！"));
                dialogBuilder.getRootSure().setOnClickListener(v -> {
                    SdkApi.getInstance().destroy(isLoadVideo);
                    isLoadVideo=!isLoadVideo;
                    mFlLoading.setVisibility(View.GONE);
                    mContainer.setVisibility(View.GONE);
                    dialogBuilder.dismiss();
                });
                break;
            case VIDEO_CHAT_FAIL:
                Log.i(TAG, "onEvent: VIDEO_CHAT_FAIL");
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
        if (isLoadVideo) {
            SdkApi.getInstance().destroy(isLoadVideo);
        }
        super.onDestroy();
    }
}
