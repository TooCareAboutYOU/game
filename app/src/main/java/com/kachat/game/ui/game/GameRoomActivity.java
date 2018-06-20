package com.kachat.game.ui.game;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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
import android.widget.Toast;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.base.videos.SdkApi;
import com.kachat.game.events.DNGameEventMessage;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.dbmodel.DbUserBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import butterknife.BindView;

public class GameRoomActivity extends BaseActivity {

    private static final String TAG = "GameRoomActivity";

    public static final String Html_Url = "html_url";
    public static final String GAME_TYPE="game_type";

    @BindView(R.id.toolbar_base)
    Toolbar mToolbarBase;

    @BindView(R.id.bridgeWebView)
    BridgeWebView mBridgeWebView;
    @BindView(R.id.fl_local)
    FrameLayout flLocalView;
    @BindView(R.id.fl_remote)
    FrameLayout flRemoteView;

    public static void newInstance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, GameRoomActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    private DbUserBean mDbUserBean = DaoQuery.queryUserData();

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
        initGameHtml();
    }

    private void initGameHtml() {
        SdkApi.getInstance().initGame(this, mBridgeWebView);
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
        int type=bundle.getInt(GAME_TYPE);

        if (TextUtils.isEmpty(url)) {
            Log.i(TAG, "游戏地址为空: ");
            this.finish();
        }
        SdkApi.getInstance().loadGame(url);
//
        SdkApi.getInstance().startPreview();
        SdkApi.getInstance().loadVideoView(this, flLocalView, flRemoteView);

        SdkApi.getInstance().initFaceRigItf("live2d/miyo", "miyo", "", "bg1.png");

        SdkApi.getInstance().startGameMatch(type);
    }

    public void handleGameRequest(String msg) {
        SdkApi.getInstance().sendMessage(msg);
        Log.w(TAG, "handleGameRequest: "+msg);
//        //Toast.makeText(this, "WebView请求:" + msg, //Toast.LENGTH_SHORT).show();
    }

    public void handleGameResponse(String msg) {
        Log.w(TAG, "handleGameResponse: "+msg);
//        //Toast.makeText(this, "WebView应答:" + msg, //Toast.LENGTH_SHORT).show();
    }

    private void signalWebView(String cmd) {
        //Toast.makeText(this, "通知WebView:" + cmd, //Toast.LENGTH_SHORT).show();
        Log.w(TAG, "signalWebView: "+cmd);
        mBridgeWebView.callHandler("ToJS", cmd, data -> handleGameResponse(data));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DNGameEventMessage event) {
        switch (event.getEvent()) {
            case SESSION_READY:
                Logger("onEvent:SESSION_READY");
                //Toast.makeText(this, "SESSION_READY", //Toast.LENGTH_SHORT).show();
                break;
            case SESSION_BROKEN:
                Log.i(TAG, "onEvent: SESSION_BROKEN");
                break;
            case SESSION_OCCUPY:
                Log.i(TAG, "onEvent: SESSION_OCCUPY");
                //Toast.makeText(this, "SESSION_OCCUPY", //Toast.LENGTH_SHORT).show();
                break;
            case SESSION_KEEP_ALIVE:
                Log.i(TAG, "onEvent: SESSION_KEEP_ALIVE");
                //Toast.makeText(this, "SESSION_KEEP_ALIVE", //Toast.LENGTH_SHORT).show();
                break;
            case JOIN_SUCCESS:
                Log.i(TAG, "onEvent: JOIN_SUCCESS");
                //Toast.makeText(this, "JOIN_SUCCESS", //Toast.LENGTH_SHORT).show();
                break;
            case JOIN_FAILED:
                Log.i(TAG, "onEvent: JOIN_FAILED");
                //Toast.makeText(this, "JOIN_FAILED", //Toast.LENGTH_SHORT).show();
                break;
            case MATCH_SUCCESS:
                Log.i(TAG, "onEvent: MATCH_SUCCESS");
                //Toast.makeText(this, "MATCH_SUCCESS", //Toast.LENGTH_SHORT).show();
                break;
            case GAME_MESSAGE:
                Log.i(TAG, "onEvent: GAME_MESSAGE");
                //Toast.makeText(this, "GAME_MESSAGE", //Toast.LENGTH_SHORT).show();
                signalWebView(event.getString());
                break;
            case GAME_STAT:
                Log.i(TAG, "onEvent: GAME_STAT");
                //Toast.makeText(this, "GAME_STAT", //Toast.LENGTH_SHORT).show();
                break;
            case VIDEO_CHAT_START:  //远端断线  提前退出
                Log.i(TAG, "onEvent: VIDEO_CHAT_START");
                //Toast.makeText(this, "VIDEO_CHAT_START", //Toast.LENGTH_SHORT).show();
                break;
            case VIDEO_CHAT_FINISH:
                Log.i(TAG, "onEvent: VIDEO_CHAT_FINISH");
                //Toast.makeText(this, "VIDEO_CHAT_FINISH", //Toast.LENGTH_SHORT).show();
                break;
            case VIDEO_CHAT_TERMINATE:
                Log.i(TAG, "onEvent: VIDEO_CHAT_TERMINATE");
                //Toast.makeText(this, "VIDEO_CHAT_TERMINATE", //Toast.LENGTH_SHORT).show();
                break;
            case VIDEO_CHAT_FAIL:
                Log.i(TAG, "onEvent: VIDEO_CHAT_FAIL");
                //Toast.makeText(this, "VIDEO_CHAT_FAIL", //Toast.LENGTH_SHORT).show();
                break;
            case GOT_GIFT:
                Log.i(TAG, "onEvent: GOT_GIFT");
                //Toast.makeText(this, "GOT_GIFT", //Toast.LENGTH_SHORT).show();
                break;
            case ERROR_MESSAGE:
                Log.i(TAG, "onEvent: ERROR_MESSAGE");
                //Toast.makeText(this, "ERROR_MESSAGE", //Toast.LENGTH_SHORT).show();
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
        SdkApi.getInstance().destroy();
        if (flLocalView.getChildCount() > 0) {
            flLocalView.removeAllViews();
        }
        if (flRemoteView.getChildCount() > 0) {
            flRemoteView.removeAllViews();
        }
        super.onDestroy();
    }

}
