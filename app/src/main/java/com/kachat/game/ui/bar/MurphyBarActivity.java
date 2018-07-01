package com.kachat.game.ui.bar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
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
import com.kachat.game.libdata.model.MessageBean;
import com.kachat.game.libdata.model.PropsBean;
import com.kachat.game.libdata.model.SingsBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.PropsPresenter;
import com.kachat.game.libdata.mvp.presenters.StatPagesPresenter;
import com.kachat.game.ui.bar.fragments.CharmRankListFragment;
import com.kachat.game.utils.OnCheckNetClickListener;
import com.kachat.game.utils.manager.ThreadManager;
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
//    private PopupWindow popupWindow=null;
    private AppCompatTextView acTvGiftOneNum,acTvGiftTwoNum,acTvGiftThreeNum;
    private SimpleDraweeView sdvGiftOneNum,sdvGiftTwoNum,sdvGiftThreeNum;

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
//                mBoxView.setBackgroundResource(R.drawable.icon_bar_box_opened);
            }
        };
    }
    private BottomSheetDialog bottomSheetDialog=null;
    private PropsPresenter mGetMyPropPresenter = null;
    private StatPagesPresenter mStatPagesPresenter = null;
    
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
                isStartChat = false;
            } else {
                if (isStartChat) {
                    Log.i(TAG, "onInitView: GONE--> true");
                    removeView();
                    isStartChat = false;
                    return;
                }
                Log.i(TAG, "onInitView: GONE--> false");
                finish();
            }
        });

        mStatPagesPresenter = new StatPagesPresenter(new StatPageCallBack());
        mStatPagesPresenter.attachPresenter(StatPagesPresenter.StatType.CHAT);

        //开启聊天
        findViewById(R.id.sdv1).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                Log.i(TAG, "onMultiClick: ");
                if (Config.getIsFiguresMask()) {
                    mFlLoading.setVisibility(View.VISIBLE);
                    SdkApi.getInstance().create(MurphyBarActivity.this);
                    SdkApi.getInstance().loadLocalView(MurphyBarActivity.this, mLocalView);
                    if (SdkApi.getInstance().getLocalView() != null) {
                        SdkApi.getInstance().getLocalView().setZOrderOnTop(true);
                    }
                    SdkApi.getInstance().loadRemoteView(MurphyBarActivity.this, mRemoteView);
                    SdkApi.getInstance().enableVideoView();
                    DbLive2DBean dbLive2DBean = Objects.requireNonNull(DaoQuery.queryModelListData()).get(0);
                    SdkApi.getInstance().loadFaceRigItf(dbLive2DBean.getLiveFilePath(), dbLive2DBean.getLiveFileName(), dbLive2DBean.getBgFilePath(), dbLive2DBean.getBgFileName(), Constant.MATCH_TYPE_CHAT);
                    SdkApi.getInstance().startGameMatch(Constant.MATCH_TYPE_CHAT);
                    isStartChat = true;
                    timerView();
                } else {
                    new AlterDialogBuilder(MurphyBarActivity.this, new DialogTextView(MurphyBarActivity.this, "暂无人物形象，请前往 '研究院' 创建人物！")).hideRootSure();
                }
            }
        });

        //魅力列表
        findViewById(R.id.sdv2).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                CharmRankListFragment.getInstance().show(getSupportFragmentManager(), CharmRankListFragment.TAG);
            }
        });

        //开宝箱
        mAcTvTimer = findViewById(R.id.acTv_Timer);
        mBoxView.setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (mAcTvTimer.getVisibility() == View.GONE) {
                    getBoxView(new SingsBean(1, 1, 1, 1));
                }
            }
        });


        //举报
        findViewById(R.id.sdv_Report).setOnClickListener(new OnCheckNetClickListener() {
            @Override
            public void onMultiClick(View v) {
                new AlterDialogBuilder(MurphyBarActivity.this, new DialogTextView(MurphyBarActivity.this, "举报成功!")).hideRootSure();
            }
        });

        mGetMyPropPresenter = new PropsPresenter(new PropCallBack());
        bottomSheetDialog = new BottomSheetDialog(this);
        //赠送礼物
        findViewById(R.id.sdv_ChoiceGift).setOnClickListener(v -> {
//            createOwnGiftView();
            createOwnGiftView();
        });
    }

    @SuppressLint({"ClickableViewAccessibility", "InflateParams"})
    private void createOwnGiftView(){
            containerView= LayoutInflater.from(this).inflate(R.layout.layout_bar_owngift,null);
            bottomSheetDialog.setContentView(containerView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            bottomSheetDialog.setCancelable(true);
            bottomSheetDialog.show();

            acTvGiftOneNum=containerView.findViewById(R.id.acTv_OneNum);
            acTvGiftTwoNum=containerView.findViewById(R.id.acTv_TwoNum);
            acTvGiftThreeNum=containerView.findViewById(R.id.acTv_OneThree);

            sdvGiftOneNum=containerView.findViewById(R.id.ll_GiftOne);
            sdvGiftTwoNum=containerView.findViewById(R.id.ll_GiftTwo);
            sdvGiftThreeNum=containerView.findViewById(R.id.ll_GiftThree);

            mGetMyPropPresenter.attachPresenter();

            sdvGiftOneNum.setOnClickListener(new OnCheckNetClickListener() {
                @Override
                public void onMultiClick(View v) {  //送屎
                    if (!TextUtils.isEmpty(acTvGiftOneNum.getText()) && !acTvGiftOneNum.getText().equals("0")) {
                        SdkApi.getInstance().sendGift(300);
                        Toast("向对方扔了一个粑粑x1");
                        mGetMyPropPresenter.attachPresenter();
                    }
                }
            });
            sdvGiftTwoNum.setOnClickListener(new OnCheckNetClickListener() {
                @Override
                public void onMultiClick(View v) { //送LOVE
                    if (!TextUtils.isEmpty(acTvGiftTwoNum.getText()) && !acTvGiftTwoNum.getText().equals("0")) {
                        SdkApi.getInstance().sendGift(301);
                        Toast("向对方赠送了一个爱心x1");
                        mGetMyPropPresenter.attachPresenter();
                    }
                }
            });
            sdvGiftThreeNum.setOnClickListener(new OnCheckNetClickListener() {
                @Override
                public void onMultiClick(View v) { //送草泥马
                    if (!TextUtils.isEmpty(acTvGiftThreeNum.getText()) && !acTvGiftThreeNum.getText().equals("0")) {
                        SdkApi.getInstance().sendGift(302);
                        Toast("向对方赠送了一个草尼玛x1");
                        mGetMyPropPresenter.attachPresenter();
                    }
                }
            });

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

    private class PropCallBack implements OnPresenterListeners.OnViewListener<PropsBean> {

        @SuppressLint("SetTextI18n")
        @Override
        public void onSuccess(PropsBean result) {
            Log.i(TAG, "onSuccess: " + result.toString());
            if (result.getProps() != null && result.getProps().size() > 0) {
                for (PropsBean.ChildPropsBean childPropsBean : result.getProps()) {
                    if (childPropsBean.getProp() != null) {
                        switch (childPropsBean.getProp().getIndex()) {
                            //粑粑
                            case 300: {
                                acTvGiftOneNum.setText(childPropsBean.getNumber()+"");
                                if (!TextUtils.isEmpty(childPropsBean.getProp().getImage_url())) {
                                    sdvGiftOneNum.setImageURI(Uri.parse(childPropsBean.getProp().getImage_url()));
                                }
                            }break;
                            //LOVE
                            case 301: {
                                acTvGiftTwoNum.setText(childPropsBean.getNumber()+"");
                                if (!TextUtils.isEmpty(childPropsBean.getProp().getImage_url())) {
                                    sdvGiftTwoNum.setImageURI(Uri.parse(childPropsBean.getProp().getImage_url()));
                                }
                            }break;
                            //草泥马
                            case 302: {
                                acTvGiftThreeNum.setText(childPropsBean.getNumber()+"");
                                if (!TextUtils.isEmpty(childPropsBean.getProp().getImage_url())) {
                                    sdvGiftThreeNum.setImageURI(Uri.parse(childPropsBean.getProp().getImage_url()));
                                }
                                ;
                            }break;
                        }
                    }
                }
            }
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (!TextUtils.isEmpty(error.getToast())) {
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


    @SuppressLint("InflateParams")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DNGameEventMessage event) {
        switch (event.getEvent()) {
            case SESSION_READY:
                Log.i(TAG, "onEvent: SESSION_READY");
                break;
            case SESSION_KEEP_ALIVE:
                Log.i(TAG, "onEvent: SESSION_KEEP_ALIVE");
                break;
            case JOIN_SUCCESS:
                Log.i(TAG, "onEvent: JOIN_SUCCESS");
                mFlLoading.setVisibility(View.GONE);
                mContainer.setVisibility(View.VISIBLE);
                mCount.start();
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
                break;
            case VIDEO_CHAT_FINISH:
                Log.i(TAG, "onEvent: VIDEO_CHAT_FINISH");
                break;
            case VIDEO_CHAT_TERMINATE:
                Log.i(TAG, "onEvent: VIDEO_CHAT_TERMINATE");
                    removeView();
                    isStartChat=false;
                    AlterDialogBuilder dialogFail=new AlterDialogBuilder(this,new DialogTextView(MurphyBarActivity.this,"对方已下线!")).hideClose();
                    dialogFail.getRootSure().setOnClickListener(v -> {
                        dialogFail.dismiss();
                    });
                break;
            case VIDEO_CHAT_FAIL:  //流断
                Log.i(TAG, "onEvent: VIDEO_CHAT_FAIL");
                break;
            case GOT_GIFT:
                Log.i(TAG, "onEvent: GOT_GIFT"+event.getLong());
                if (event.getLong() == 300) {
                    Toast("对方向你扔了一个粑粑x1");
                }
                if (event.getLong() == 301) {
                    Toast("对方向你赠送了一个爱心x1");
                }
                if (event.getLong() == 302) {
                    Toast("对方向你赠送了一个草尼玛x1");
                }else
                break;
            case ERROR_MESSAGE:
                Log.i(TAG, "onEvent: ERROR_MESSAGE");
                break;
        }
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

        if (mGetMyPropPresenter != null) {
            mGetMyPropPresenter.detachPresenter();
            mGetMyPropPresenter=null;
        }

        if (mStatPagesPresenter != null) {
            mStatPagesPresenter.detachPresenter();
            mStatPagesPresenter=null;
        }
        
        super.onDestroy();
    }

    private void removeView(){
        Log.i(TAG, "removeView: ");
        mFlLoading.setVisibility(View.GONE);
        mContainer.setVisibility(View.GONE);

        if (containerView != null) {
            containerView=null;
        }

        if (bottomSheetDialog != null) {
            bottomSheetDialog=null;
        }

        if (timerRunning) {
            mCount.cancel();
            timerRunning=false;
            mCount=null;
        }
        SdkApi.getInstance().destroy(true);
        if (mRemoteView.getChildCount() > 0) {
            Log.i(TAG, "removeView: ");
            mRemoteView.removeAllViews();
        }
        if (mLocalView.getChildCount() > 0){
            mLocalView.removeAllViews();
        }

    }

    private class StatPageCallBack implements OnPresenterListeners.OnViewListener<MessageBean>{

        @Override
        public void onSuccess(MessageBean result) {
            Log.i(TAG, "onSuccess: ");
        }

        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            Log.i(TAG, "onFailed: ");
        }

        @Override
        public void onError(Throwable e) {
            Log.i(TAG, "onError: ");
        }
    }

}
