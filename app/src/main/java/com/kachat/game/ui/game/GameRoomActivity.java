package com.kachat.game.ui.game;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.kachat.game.events.PublicEventMessage;
import com.kachat.game.ui.bar.MurphyBarActivity;
import com.kachat.game.ui.user.MeActivity;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.SdkApi;
import com.kachat.game.events.DNGameEventMessage;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.dbmodel.DbLive2DBean;
import com.kachat.game.libdata.dbmodel.DbUserBean;
import com.kachat.game.utils.widgets.DialogTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import butterknife.BindView;

public class GameRoomActivity extends BaseActivity {

    private static final String TAG = "GameRoomActivity";

    public static final String Html_Url = "html_url";
    public static final String GAME_TYPE="game_type";

    @BindView(R.id.fl_LoadingView)
    FrameLayout mLoadLayout;
    @BindView(R.id.toolbar_base)
    Toolbar mToolbarBase;
    @BindView(R.id.bridgeWebView)
    BridgeWebView mBridgeWebView;
    @BindView(R.id.fl_local)
    FrameLayout flLocalView;
    @BindView(R.id.fl_remote)
    FrameLayout flRemoteView;

    private int register=0;

    public static void newInstance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, GameRoomActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    private DbUserBean mDbUserBean = DaoQuery.queryUserData();
    private int type;

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_game_room;
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
        getToolBarBack().setOnClickListener(v -> finish());
        mLoadLayout.setVisibility(View.VISIBLE);
        initGameHtml();
    }

    private void initGameHtml() {
        SdkApi.getInstance().loadGame(mBridgeWebView);
        SdkApi.getInstance().getBridgeWebView().setDefaultHandler(new DefaultHandler());
        SdkApi.getInstance().getBridgeWebView().setWebChromeClient(new MyWebChromeClient());
        SdkApi.getInstance().getBridgeWebView().setWebViewClient(new BridgeWebViewClient(mBridgeWebView));

        //js发送给按住消息   submitFromWeb 是js调用的方法名    安卓\返回给js
        SdkApi.getInstance().getBridgeWebView().registerHandler("ToApp", (data, function) -> {
            Log.i(TAG, "接受Js消息: ");
//            显示接收的消息
            handleGameRequest(data);
//            返回给html的消息
            function.onCallBack("返回给//Toast的alert");
        });

        Bundle bundle = getIntent().getExtras();
        String url = Objects.requireNonNull(bundle).getString(Html_Url);
        type=bundle.getInt(GAME_TYPE);

        Log.i(TAG, "initGameHtml: "+url+"\t\t"+type);

        if (TextUtils.isEmpty(url)) {
            Toast("游戏地址为空!");
            this.finish();
        }


        SdkApi.getInstance().create(this);
        SdkApi.getInstance().loadGame(url);

    }

    private void loadVideo(){
        SdkApi.getInstance().loadLocalView(this, flLocalView);
        SdkApi.getInstance().loadRemoteView(this, flRemoteView);
        SdkApi.getInstance().enableVideoView();
        DbLive2DBean dbLive2DBean= Objects.requireNonNull(DaoQuery.queryModelListData()).get(0);
        SdkApi.getInstance().loadFaceRigItf(dbLive2DBean.getLiveFilePath(), dbLive2DBean.getLiveFileName(), dbLive2DBean.getBgFilePath(), dbLive2DBean.getBgFileName(),type);//
        SdkApi.getInstance().startGameMatch(type);
    }

    public void handleGameRequest(String msg) {
        SdkApi.getInstance().sendMessage(msg);
        Log.w(TAG, "handleGameRequest: "+msg);
//        //Toast.makeText(this, "WebView请求:" + msg, //Toast.LENGTH_SHORT).show();
    }

    public void handleGameResponse(String msg) {
        Log.w(TAG, "handleGameResponse: "+msg);
    }

    private void signalWebView(String cmd) {
        Log.w(TAG, "signalWebView: "+cmd);
        mBridgeWebView.callHandler("ToJS", cmd, data -> handleGameResponse(data));
        this.finish();
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
                register++;
                if (register == 7) {
                    AlterDialogBuilder dialogBroken=new AlterDialogBuilder(this, new DialogTextView(GameRoomActivity.this,"连接超时，请重新连接！"));
                    dialogBroken.getRootSure().setOnClickListener(v -> {
                        SdkApi.getInstance().sdkExit();
                        SdkApi.getInstance().create(this);
                    });
                }
                break;
            case SESSION_OCCUPY:  // 同一个账号登录
                Log.i(TAG, "onEvent: SESSION_OCCUPY");
                AlterDialogBuilder dialogBuilder=new AlterDialogBuilder(this, new DialogTextView(GameRoomActivity.this,"账号异地登录，请重新?"));
                dialogBuilder.getRootSure().setOnClickListener(v -> {
                    dialogBuilder.dismiss();
                    PublicEventMessage.ExitAccount(this);
                    finish();
                });
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
                if (!TextUtils.isEmpty(event.getString())) {
                    DNGameEventMessage.OnGameMessageBean gameBean= JSON.parseObject(event.getString(),DNGameEventMessage.OnGameMessageBean.class);
                    if (!TextUtils.isEmpty(gameBean.getType()) && gameBean.getType().equals("leave")) {
//                        SdkApi.getInstance().destroy(true);
//                        finish();
                        AlterDialogBuilder dialogBuilder1=new AlterDialogBuilder(GameRoomActivity.this,
                                new DialogTextView(GameRoomActivity.this,"对方已下线！！！")).hideClose();
                        dialogBuilder1.getRootSure().setOnClickListener(v -> {
                            dialogBuilder1.dismiss();
                            finish();
                        });
                    }

                    DNGameEventMessage.OnBoxsMessageBean boxBean=JSON.parseObject(event.getString(),DNGameEventMessage.OnBoxsMessageBean.class);
                    if (boxBean!= null) {

                        View boxRootView= LayoutInflater.from(this).inflate(R.layout.dialog_box,null);
                        ImageView sdvViewBG=boxRootView.findViewById(R.id.sdv_Box_bg);
                        ImageView sdvView=boxRootView.findViewById(R.id.sdv_Box);
                        Glide.with(this).asGif().load(R.drawable.gif_game_box_bg).into(sdvViewBG);
                        switch (boxBean.getBox()) {
                            case 0: Glide.with(this).asGif().load(R.drawable.gif_box_white).into(sdvView);break;//白色
                            case 1: Glide.with(this).asGif().load(R.drawable.gif_box_blue).into(sdvView);break;//蓝色
                            case 2: Glide.with(this).asGif().load(R.drawable.gif_box_purple).into(sdvView);break;//紫色
                            case 3: Glide.with(this).asGif().load(R.drawable.gif_box_orange).into(sdvView);break;//橙色
                            case 4: Glide.with(this).asGif().load(R.drawable.gif_box_yellow).into(sdvView);break;//金色
                        }
                        AlterDialogBuilder builderView=new AlterDialogBuilder(this,boxRootView).hideRootSure();
                        boxRootView.setOnClickListener(v -> {
                            builderView.dismiss();
                            DailogView(boxBean);
                        });
                    }
                }

                signalWebView(event.getString());
                break;
            case GAME_STAT:
                Log.i(TAG, "onEvent: GAME_STAT");
                break;
            case VIDEO_CHAT_START:
                Log.i(TAG, "onEvent: VIDEO_CHAT_START");
                mLoadLayout.setVisibility(View.GONE);
                break;
            case VIDEO_CHAT_FINISH:
                Log.i(TAG, "onEvent: VIDEO_CHAT_FINISH");
                break;
            case VIDEO_CHAT_TERMINATE:
                Log.i(TAG, "onEvent: VIDEO_CHAT_TERMINATE");
//                AlterDialogBuilder dialogBuilder1=new AlterDialogBuilder(GameRoomActivity.this,
//                        new DialogTextView(GameRoomActivity.this,"对方已下线！！！")).hideClose();
//                dialogBuilder1.getRootSure().setOnClickListener(v -> {
//                    dialogBuilder1.dismiss();
//                    finish();
//                });
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

    private class BridgeWebViewClient extends com.github.lzyzsd.jsbridge.BridgeWebViewClient {

        BridgeWebViewClient(BridgeWebView webView) {
            super(webView);
            Log.i(TAG, "BridgeWebViewClient: ");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i(TAG, "shouldOverrideUrlLoading: " + url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.i(TAG, "onPageStarted: " + url + "\t\t");
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.i(TAG, "onPageFinished: " + url);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.i(TAG, "onReceivedError: " + errorCode + "\t\t" + description + "\t\t" + failingUrl);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.i(TAG, "onProgressChanged: " + newProgress);
            if (newProgress == 100) {
                loadVideo();
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.i(TAG, "onReceivedTitle: " + title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
            Log.i(TAG, "onReceivedIcon: ");
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
            super.onReceivedTouchIconUrl(view, url, precomposed);
            Log.i(TAG, "onReceivedTouchIconUrl: " + url + "\t\t" + precomposed);
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            Log.i(TAG, "onShowCustomView: ");
        }

        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            super.onShowCustomView(view, requestedOrientation, callback);
            Log.i(TAG, "onShowCustomView: ");
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
            Log.i(TAG, "onHideCustomView: ");
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            Log.i(TAG, "onCreateWindow: " + isDialog + "\t\t" + isUserGesture + "\t\t" + resultMsg);
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

        @Override
        public void onRequestFocus(WebView view) {
            super.onRequestFocus(view);
            Log.i(TAG, "onRequestFocus: ");
        }

        @Override
        public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
            Log.i(TAG, "onCloseWindow: ");
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.w(TAG, "onJsAlert: " + url + "\t\t" + message + "\t\t" + result.toString());
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            Log.i(TAG, "onJsConfirm: " + url + "\t\t" + message + "\t\t" + result.toString());
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            Log.i(TAG, "onJsPrompt: " + url + "\t\t" + message + "\t\t" + defaultValue + "\t\t" + result.toString());
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
            Log.i(TAG, "onJsBeforeUnload: " + url + "\t\t" + message + "\t\t" + result.toString());
            return super.onJsBeforeUnload(view, url, message, result);
        }

        @Override
        public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota, WebStorage.QuotaUpdater quotaUpdater) {
            super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
            Log.i(TAG, "onExceededDatabaseQuota: " + databaseIdentifier + "\t\t" + quota + "\t\t" + estimatedDatabaseSize + "\t\t" + totalQuota + "\t\t" + quotaUpdater.toString());
        }

        @Override
        public void onReachedMaxAppCacheSize(long requiredStorage, long quota, WebStorage.QuotaUpdater quotaUpdater) {
            super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
            Log.i(TAG, "onReachedMaxAppCacheSize: " + requiredStorage + "\t\t" + quota + "\t\t" + quotaUpdater.toString());
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            super.onGeolocationPermissionsShowPrompt(origin, callback);
            Log.i(TAG, "onGeolocationPermissionsShowPrompt: " + origin + "\t\t" + callback.toString());
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
            Log.i(TAG, "onGeolocationPermissionsHidePrompt: ");
        }

        @Override
        public void onPermissionRequest(PermissionRequest request) {
            super.onPermissionRequest(request);
            Log.i(TAG, "onPermissionRequest: ");
        }

        @Override
        public void onPermissionRequestCanceled(PermissionRequest request) {
            super.onPermissionRequestCanceled(request);
            Log.i(TAG, "onPermissionRequestCanceled: ");
        }

        @Override
        public boolean onJsTimeout() {
            Log.i(TAG, "onJsTimeout: ");
            return super.onJsTimeout();
        }

        @Override
        public void onConsoleMessage(String message, int lineNumber, String sourceID) {
            super.onConsoleMessage(message, lineNumber, sourceID);
            Log.i(TAG, "onConsoleMessage: " + message + "\t\t" + lineNumber + "\t\t" + sourceID);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.i(TAG, "onConsoleMessage: " + consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }

        @Nullable
        @Override
        public Bitmap getDefaultVideoPoster() {
            Log.i(TAG, "getDefaultVideoPoster: ");
            return super.getDefaultVideoPoster();
        }

        @Nullable
        @Override
        public View getVideoLoadingProgressView() {
            Log.i(TAG, "getVideoLoadingProgressView: ");
            return super.getVideoLoadingProgressView();
        }

        @Override
        public void getVisitedHistory(ValueCallback<String[]> callback) {
            super.getVisitedHistory(callback);
            Log.i(TAG, "getVisitedHistory: ");
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            Log.i(TAG, "onShowFileChooser: ");
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
    }

    @SuppressLint("InflateParams")
    private void DailogView(DNGameEventMessage.OnBoxsMessageBean boxBean){
        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(this,R.style.AlertDialogStyle);
        View view=LayoutInflater.from(this).inflate(R.layout.dialog_box_info,null);
        ImageView imgBG=view.findViewById(R.id.imgBG);
        SimpleDraweeView propOneView=view.findViewById(R.id.sdv_one);
        AppCompatTextView propOneTvView=view.findViewById(R.id.acTv_One);
        SimpleDraweeView propTwoView=view.findViewById(R.id.sdv_two);
        AppCompatTextView propTwoTvView=view.findViewById(R.id.acTv_Two);
        SimpleDraweeView chipsThreeView=view.findViewById(R.id.sdv_three);
        AppCompatTextView chipsThreeTvView=view.findViewById(R.id.acTv_Three);
        SimpleDraweeView getView=view.findViewById(R.id.sdv_Get);
        Glide.with(this).asGif().load(R.drawable.gif_game_box_bg).into(imgBG);
        if (boxBean != null && boxBean.getChips() != null && boxBean.getChips().size() != 0
                && boxBean.getProps() != null && boxBean.getProps().size() != 0) {
            propOneView.setImageResource(getDrawableView(boxBean.getProps().get(0).getProp()));
            propOneTvView.setText("X"+boxBean.getProps().get(0).getNumber());
            propTwoView.setImageResource(getDrawableView(boxBean.getProps().get(1).getProp()));
            propTwoTvView.setText("X"+boxBean.getProps().get(1).getNumber());
            chipsThreeView.setImageResource(getDrawableView(boxBean.getChips().get(0).getChip()));
            chipsThreeTvView.setText("X"+boxBean.getChips().get(0).getNumber());

        }
        builder.setView(view);
        builder.setCancelable(false);
        builder.create();
        android.support.v7.app.AlertDialog dialog=builder.show();
        getView.setOnClickListener(v -> dialog.dismiss());
    }


    private @DrawableRes int getDrawableView(int index){
        @DrawableRes int drawable=R.mipmap.ic_launcher;
        switch (index) {
            case 0: drawable= R.drawable.img_000_strong;break;
            case 1: drawable= R.drawable.img_001_xiaofei;break;
            case 2: drawable= R.drawable.img_002_shaizi;break;
            case 3: drawable= R.drawable.img_003_gold;break;
            case 100: drawable= R.drawable.img_100_yanjing;break;
            case 101: drawable= R.drawable.img_101_wawaquan;break;
            case 300: drawable= R.drawable.img_300_baba; break;
            case 301: drawable= R.drawable.img_301_love;break;
            case 302: drawable= R.drawable.img_302_caonima;break;
            case 400: drawable= R.drawable.img_400_weikeda;break;
            case 401: drawable= R.drawable.img_401_tiyana;break;
            case 402: drawable= R.drawable.img_402_alaikesi;break;
            case 403: drawable= R.drawable.img_403_niezheng;break;
            case 404: drawable= R.drawable.img_404_kapa;break;
            case 405: drawable= R.drawable.img_405_landiya;break;
            case 406: drawable= R.drawable.img_406_mingqu;break;
            case 407:drawable= R.drawable.img_407_chun;break;
            case 408:drawable= R.drawable.img_408_cunhua;break;
//            case 409:break;
        }
        return drawable;
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        SdkApi.getInstance().getBridgeWebView().resumeTimers();
        SdkApi.getInstance().getBridgeWebView().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
        SdkApi.getInstance().getBridgeWebView().pauseTimers();
        SdkApi.getInstance().getBridgeWebView().onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");

        SdkApi.getInstance().destroy(true);

        if (flLocalView.getChildCount() > 0) {
            flLocalView.removeAllViews();
        }
        if (flRemoteView.getChildCount() > 0) {
            flRemoteView.removeAllViews();
        }

        super.onDestroy();
    }

}
