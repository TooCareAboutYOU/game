package com.kachat.game.ui.game;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.dnion.VAGameAPI;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.events.VAGameEventMessage;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;

public class GameActivity extends BaseActivity {

    private static final String TAG = "GameActivity";

    @BindView(R.id.bridgeWebView)
    BridgeWebView mBridgeWebView;
    @BindView(R.id.btn_Status)
    AppCompatButton mBtnStatus;
    private WebSettings mWebSettings;

    @Override
    protected int onSetResourceLayout() {   return R.layout.activity_game;   }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onInitView() {
        mWebSettings=mBridgeWebView.getSettings();
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setPluginState(WebSettings.PluginState.ON);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setDisplayZoomControls(false);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setSupportMultipleWindows(true);
        mWebSettings.setNeedInitialFocus(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setAppCacheMaxSize(1024*1024*8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        mWebSettings.setAppCachePath(appCachePath);

        mBridgeWebView.setDefaultHandler(new DefaultHandler());
        mBridgeWebView.setWebChromeClient(new MyWebChromeClient());
        mBridgeWebView.setWebViewClient(new BridgeWebViewClient(mBridgeWebView));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            mBridgeWebView.evaluateJavascript(js,null);
//        }else {
            mBridgeWebView.loadUrl("http://demo.e3webrtc.com:9002");
//            mBridgeWebView.reload();
//        }
    }

    @Override
    protected void onInitData(@Nullable Bundle savedInstanceState) {
        //js发送给按住消息   submitFromWeb 是js调用的方法名    安卓\返回给js
        mBridgeWebView.registerHandler("ToApp", (data, function) -> {
            //显示接收的消息
            handleGameRequest(data);
            //返回给html的消息
            function.onCallBack( "返回给Toast的alert");
        });

        String gateUrl = "ws://47.96.24.93:8899";

        AppCompatEditText editText=findViewById(R.id.et_uid);

        findViewById(R.id.acBtn_load).setOnClickListener(v -> {
            String uid=editText.getText().toString().trim();
            if (!uid.isEmpty()) {
                VAGameAPI.getInstance().connect(gateUrl, uid, "");
            }
        });

        findViewById(R.id.acBtn_match).setOnClickListener(v -> {
            Toast.makeText(this, "点击了match", Toast.LENGTH_SHORT).show();
            VAGameAPI.getInstance().startGameMatch(1);
        });
    }


    public void handleGameRequest (String msg){
        VAGameAPI.getInstance().sendGameMessage(msg);
        Log.i(TAG, "handleGameRequest: ");
        Toast.makeText(this, "WebView请求:" + msg, Toast.LENGTH_SHORT).show();
    }

    public void handleGameResponse (String msg){
        Log.i(TAG, "handleGameResponse: ");
        Toast.makeText(this, "WebView应答:" + msg, Toast.LENGTH_SHORT).show();
    }

    private void signalWebView(String cmd) {
        Toast.makeText(this, "通知WebView:" + cmd, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "signalWebView: ");
        mBridgeWebView.callHandler("ToJS", cmd, data -> handleGameResponse(data));
    }
    
    @Subscribe(threadMode= ThreadMode.MAIN)
    public void onEvent(VAGameEventMessage event) {
        switch (event.getType()) {
            case VAGAME_SUCCESS:
                Logger("onEvent:SDK连接成功");
                mBtnStatus.setText("SDK连接成功");
                Toast.makeText(this, "SDK连接成功", Toast.LENGTH_SHORT).show();
                break;
            case VAGAME_FAILED:
                Log.i(TAG, "onEvent: SDK连接失败\t\t"+event.getMsg());
                mBtnStatus.setText("SDK连接失败\t\t"+event.getMsg());
                break;
            case VAGAME_DISCONNECTED:
                Log.i(TAG, "onEvent: SDK连接断开\t\t"+event.getMsg());
                mBtnStatus.setText("SDK连接断开\t\t"+event.getMsg());
                Toast.makeText(this, "SDK连接失败", Toast.LENGTH_SHORT).show();
                break;
            case VAGAME_JOIN_SUCCESS:
                Log.i(TAG, "onEvent: 加入成功");
                mBtnStatus.setText("加入成功");
                break;
            case VAGAME_JOIN_FAILED:
                Log.i(TAG, "onEvent: 加入失败\t\t"+event.getMsg());
                mBtnStatus.setText("加入失败\t\t"+event.getMsg());
                Toast.makeText(this, "加入失败", Toast.LENGTH_SHORT).show();
                break;
            case VAGAME_LEAVE:
                Log.i(TAG, "onEvent: 离开");
                mBtnStatus.setText("离开");
                Toast.makeText(this, "离开", Toast.LENGTH_SHORT).show();
                break;
            case VAGAME_MATCH_SUCCESS:
                Log.i(TAG, "onEvent: MATCH 成功");
                mBtnStatus.setText("match 成功");
                VAGameAPI.getInstance().joinGameRoom();
                Toast.makeText(this, "MATCH 成功", Toast.LENGTH_SHORT).show();
                break;
            case VAGAME_MESSAGE:
                Log.i(TAG, "onEvent: 消息\t\t"+event.getMsg());
                mBtnStatus.setText("消息\t\t"+event.getMsg());
                Toast.makeText(this, "消息\t\t" + event.getMsg(), Toast.LENGTH_SHORT).show();
                signalWebView(event.getMsg());
                break;
        }
    }


    private class BridgeWebViewClient extends com.github.lzyzsd.jsbridge.BridgeWebViewClient{

        BridgeWebViewClient(BridgeWebView webView) {
            super(webView);
            Log.i(TAG, "BridgeWebViewClient: ");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i(TAG, "shouldOverrideUrlLoading: "+url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.i(TAG, "onPageStarted: "+url+"\t\t");
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.i(TAG, "onPageFinished: "+url);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.i(TAG, "onReceivedError: "+errorCode+"\t\t"+description+"\t\t"+failingUrl);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    private class MyWebChromeClient extends WebChromeClient{

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.i(TAG, "onProgressChanged: "+newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.i(TAG, "onReceivedTitle: "+title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
            Log.i(TAG, "onReceivedIcon: ");
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
            super.onReceivedTouchIconUrl(view, url, precomposed);
            Log.i(TAG, "onReceivedTouchIconUrl: "+url+"\t\t"+precomposed);
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
            Log.i(TAG, "onCreateWindow: "+isDialog+"\t\t"+isUserGesture+"\t\t"+resultMsg);
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
            Log.w(TAG, "onJsAlert: "+url+"\t\t"+message+"\t\t"+result.toString());
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            Log.i(TAG, "onJsConfirm: "+url+"\t\t"+message+"\t\t"+result.toString());
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            Log.i(TAG, "onJsPrompt: "+url+"\t\t"+message+"\t\t"+defaultValue+"\t\t"+result.toString());
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
            Log.i(TAG, "onJsBeforeUnload: "+url+"\t\t"+message+"\t\t"+result.toString());
            return super.onJsBeforeUnload(view, url, message, result);
        }

        @Override
        public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota, WebStorage.QuotaUpdater quotaUpdater) {
            super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
            Log.i(TAG, "onExceededDatabaseQuota: "+databaseIdentifier+"\t\t"+quota+"\t\t"+estimatedDatabaseSize+"\t\t"+totalQuota+"\t\t"+quotaUpdater.toString());
        }

        @Override
        public void onReachedMaxAppCacheSize(long requiredStorage, long quota, WebStorage.QuotaUpdater quotaUpdater) {
            super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
            Log.i(TAG, "onReachedMaxAppCacheSize: "+requiredStorage+"\t\t"+quota+"\t\t"+quotaUpdater.toString());
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            super.onGeolocationPermissionsShowPrompt(origin, callback);
            Log.i(TAG, "onGeolocationPermissionsShowPrompt: "+origin+"\t\t"+callback.toString());
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
            Log.i(TAG, "onConsoleMessage: "+message+"\t\t"+lineNumber+"\t\t"+sourceID);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.i(TAG, "onConsoleMessage: "+consoleMessage.message());
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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        VAGameAPI.getInstance().leaveGameRoom();
    }

    @Override
    protected void onDestroy() {
        VAGameAPI.getInstance().disconnect();
        super.onDestroy();
//        System.exit(0);
    }

}
