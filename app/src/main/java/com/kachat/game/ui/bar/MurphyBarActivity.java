package com.kachat.game.ui.bar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
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
import com.kachat.game.libdata.model.SingsBean;
import com.kachat.game.ui.bar.fragments.CharmRankListFragment;
import com.kachat.game.utils.OnCheckNetClickListener;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.Objects;
import butterknife.BindView;


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
    private View containerView=null;
    private PopupWindow popupWindow=null;
    private AppCompatTextView acTvGiftOneNum,acTvGiftTwoNum,acTvGiftThreeNum;

    private int register=0;
    private boolean isStartChat=false,timerRunning=false;

    private CountDownTimer mCount=null;
    private void timerView(){
        mBoxView.setVisibility(View.VISIBLE);
        mAcTvTimer.setVisibility(View.VISIBLE);
        mCount=new CountDownTimer(30000,1000) {
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
    }

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
                removeView();
                isStartChat=false;
            } else {
                if (isStartChat) {
                    Log.i(TAG, "onInitView: GONE--> true");
                    removeView();
                    isStartChat=false;
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
                    getBoxView(new SingsBean(1,1,1,1));
                }
            }
        });

        //开启聊天
        findViewById(R.id.sdv1).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                Log.i(TAG, "onMultiClick: ");
                if (Config.getIsFiguresMask()) {
                    mFlLoading.setVisibility(View.VISIBLE);
                    SdkApi.getInstance().create(MurphyBarActivity.this);
                    SdkApi.getInstance().loadLocalView(MurphyBarActivity.this, mLocalView);
                    SdkApi.getInstance().getLocalView().setZOrderOnTop(true);
                    SdkApi.getInstance().loadRemoteView(MurphyBarActivity.this, mRemoteView);
                    SdkApi.getInstance().enableVideoView();
                    DbLive2DBean dbLive2DBean = Objects.requireNonNull(DaoQuery.queryModelListData()).get(0);
                    Log.i("SdkApi", "onMultiClick: "+dbLive2DBean.getLiveFilePath()+"\t\t"+dbLive2DBean.getLiveFileName()+"\t\t"+dbLive2DBean.getBgFilePath()+"\t\t"+dbLive2DBean.getBgFileName()+"\t\t"+dbLive2DBean.getPitchLevel());
                    SdkApi.getInstance().loadFaceRigItf(dbLive2DBean.getLiveFilePath(), dbLive2DBean.getLiveFileName(), dbLive2DBean.getBgFilePath(), dbLive2DBean.getBgFileName(),dbLive2DBean.getPitchLevel(), Constant.MATCH_TYPE_CHAT);
                    SdkApi.getInstance().startGameMatch(Constant.MATCH_TYPE_CHAT);
                    isStartChat=true;
                    timerView();
                }else {
                    new AlterDialogBuilder(MurphyBarActivity.this,new DialogTextView(MurphyBarActivity.this,"暂无人物形象，请前往 '研究院' 创建人物！")).hideRootSure();
                }
            }
        });

        //魅力列表
        findViewById(R.id.sdv2).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                CharmRankListFragment.getInstance().show(getSupportFragmentManager(),CharmRankListFragment.TAG);
            }
        });

        //举报
        findViewById(R.id.sdv_Report).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                new AlterDialogBuilder(MurphyBarActivity.this,new DialogTextView(MurphyBarActivity.this,"举报成功!")).hideRootSure();
            }
        });

        //赠送礼物
        findViewById(R.id.sdv_ChoiceGift).setOnClickListener(v -> {
            createOwnGiftView();
        });
    }

    @SuppressLint({"ClickableViewAccessibility", "InflateParams"})
    private void createOwnGiftView(){
        if (containerView == null) {
            containerView= LayoutInflater.from(this).inflate(R.layout.layout_bar_owngift,null);
            popupWindow=new PopupWindow(containerView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setContentView(containerView);
            popupWindow.setTouchInterceptor((v, event) -> {
//                popupWindow.dismiss();
                return false;
            });
            popupWindow.setOnDismissListener(() -> containerView=null);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
//            showAsDropDown(popupWindow,mLayout, 0,0);
            popupWindow.showAsDropDown(mLayout, 0,0,Gravity.BOTTOM);
            acTvGiftOneNum=containerView.findViewById(R.id.acTv_OneNum);
            acTvGiftTwoNum=containerView.findViewById(R.id.acTv_TwoNum);
            acTvGiftThreeNum=containerView.findViewById(R.id.acTv_OneThree);

            containerView.findViewById(R.id.ll_GiftOne).setOnClickListener(new OnCheckNetClickListener() {
                @Override
                public void onMultiClick(View v) {
                    if (!TextUtils.isEmpty(acTvGiftOneNum.getText()) && !acTvGiftOneNum.getText().equals("0")) {
                        SdkApi.getInstance().sendGift(0);
                    }
                }
            });
            containerView.findViewById(R.id.ll_GiftTwo).setOnClickListener(new OnCheckNetClickListener() {
                @Override
                public void onMultiClick(View v) {
                    if (!TextUtils.isEmpty(acTvGiftTwoNum.getText()) && !acTvGiftTwoNum.getText().equals("0")) {
                        SdkApi.getInstance().sendGift(0);
                    }
                }
            });
            containerView.findViewById(R.id.ll_GiftThree).setOnClickListener(new OnCheckNetClickListener() {
                @Override
                public void onMultiClick(View v) {
                    if (!TextUtils.isEmpty(acTvGiftThreeNum.getText()) && !acTvGiftThreeNum.getText().equals("0")) {
                        SdkApi.getInstance().sendGift(0);
                    }
                }
            });
        }
    }

    public void showAsDropDown(PopupWindow pw, View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            pw.setHeight(height);
            pw.showAsDropDown(anchor, xoff, yoff,Gravity.BOTTOM);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff,Gravity.BOTTOM);
        }
    }

    //获取宝箱内容
    @SuppressLint({"InflateParams", "SetTextI18n"})
    private void getBoxView(SingsBean result){
        AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.AlertDialogStyle);
        builder.setCancelable(false);
        builder.create();

        View view=LayoutInflater.from(this).inflate(R.layout.layout_bar_getbox,null);
        SimpleDraweeView sdvGget=view.findViewById(R.id.sdv_Get);
        AppCompatTextView info=view.findViewById(R.id.acTv_Info);
        builder.setView(view);

        AlertDialog dialog=builder.show();

        sdvGget.setOnClickListener(v-> {
            mBoxView.setVisibility(View.GONE);
            mBoxView.setBackgroundResource(R.drawable.icon_bar_box_default);
            dialog.dismiss();
        });

        info.setText(result.getDiamond()+"钻石\n"+
                result.getGold()+"金币\n"+
                result.getHp()+"体力\n"+
                result.getExp()+"经验");
    }



    @SuppressLint("InflateParams")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DNGameEventMessage event) {
        switch (event.getEvent()) {
            case SESSION_READY:
                Log.i(TAG, "onEvent: SESSION_READY");
                break;
            case SESSION_BROKEN:
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
                break;
            case VIDEO_CHAT_FAIL:  //流断
                Log.i(TAG, "onEvent: VIDEO_CHAT_FAIL");
                AlterDialogBuilder dialogFail=new AlterDialogBuilder(this,new DialogTextView(MurphyBarActivity.this,"对方已下线!")).hideClose();
                dialogFail.getRootSure().setOnClickListener(v -> {
                    isStartChat=false;
                    removeView();
                    dialogFail.dismiss();
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
    public void onBackPressed() {
        if (mFlLoading.getVisibility() == View.VISIBLE) {
            Log.i(TAG, "onInitView: VISIBLE");
            removeView();
            isStartChat=false;
        } else {
            if (isStartChat) {
                Log.i(TAG, "onInitView: GONE--> true");
                removeView();
                isStartChat=false;
                return;
            }
            Log.i(TAG, "onInitView: GONE--> false");
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (isStartChat) {
            Log.i(TAG, "onDestroy: ->>>");
            removeView();
        }
        super.onDestroy();
    }

    private void removeView(){

        mFlLoading.setVisibility(View.GONE);
        mContainer.setVisibility(View.GONE);

        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }

        if (timerRunning) {
            mCount.cancel();
            timerRunning=false;
            mCount=null;
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
