package com.kachat.game.base.videos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.RtcVideoProcess.FaceRigItf;
import com.RtcVideoProcess.VideoProcessItf;
import com.dnion.RenderProxy;
import com.dnion.VAGameAPI;
import com.github.lzyzsd.jsbridge.BridgeWebView;

public class SdkApi {

    private static final String TAG = "SdkApi";
    
    private Context mContext;
    private WebSettings mWebSettings = null;
    private BridgeWebView mBridgeWebView = null;
    private RenderProxy localProxy = null, remoteProxy = null;
    private SurfaceView localView = null, remoteView = null;
    private VideoProcessItf videoProcessorToCamera = null;
    private FaceRigItf faceRigItf = null;

    public interface OnFaceDetectListener { void onEvent(boolean isHave);}
    private OnFaceDetectListener mFaceDetectListener = null;
    public void setOnFaceDetectListener(OnFaceDetectListener listener) { mFaceDetectListener = listener; }

    private SdkApi() { }

    @SuppressLint("StaticFieldLeak")
    public static SdkApi getInstance() {
        return Html5GameHolder.instance;
    }

    private static class Html5GameHolder {
        @SuppressLint("StaticFieldLeak")
        private static final SdkApi instance = new SdkApi();
    }

    public void loadGame(Context context, BridgeWebView bridgeWebView) {
        mContext = context.getApplicationContext();
        mBridgeWebView = bridgeWebView;

        mWebSettings = mBridgeWebView.getSettings();
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
        mWebSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = mContext.getCacheDir().getAbsolutePath();
        mWebSettings.setAppCachePath(appCachePath);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            mBridgeWebView.evaluateJavascript(js,null);
//        }else {
//            mBridgeWebView.loadUrl("http://demo.e3webrtc.com:9004");
//            mBridgeWebView.reload();
//        }

        mBridgeWebView.setOnLongClickListener(v -> true);
    }

    public BridgeWebView getBridgeWebView() {
        if (mBridgeWebView == null) { throw new NullPointerException("mBridgeWebView is null"); }
        return mBridgeWebView;
    }

    public void sdkLogin(int uid, String token) {
        Log.i(TAG, "sdkLogin: "); 
        VAGameAPI.getInstance().login(uid + "", token); }

    public void sdkExit() {
        Log.i(TAG, "sdkExit: ");
        VAGameAPI.getInstance().logout(); }

    public void loadGame(String htmlUrl) { mBridgeWebView.loadUrl(htmlUrl); }

    /**
     * 0：GAME_TOWER,      //盖房子 9002
     * 1：GAME_POPSTAR,    //消灭星星  9003
     * 2：GAME_HEXSTAR,    //六边形  9004
     * 3：GAME_CHAT,       //聊天
     */
    public void startGameMatch(int gameType) {
        Log.i(TAG, "startGameMatch: ");
        VAGameAPI.getInstance().startGameMatch(gameType); }

    public void create() {
        Log.i(TAG, "create: ");
        VAGameAPI.getInstance().startPreview(); }

    public void loadLocalView(Context context, ViewGroup localContainer) {
        Log.i(TAG, "loadLocalView: ");
        localProxy = VAGameAPI.getInstance().createRenderProxy(context.getApplicationContext());
        localProxy.setAspectMode(RenderProxy.AspectMode.aspectFill);
        localView = localProxy.getDisplay();
        localContainer.addView(localView);
    }

    public void loadRemoteView(Context context, ViewGroup remoteContainer) {
        Log.i(TAG, "loadRemoteView: ");
        remoteProxy = VAGameAPI.getInstance().createRenderProxy(context.getApplicationContext());
        remoteProxy.setAspectMode(RenderProxy.AspectMode.aspectFill);
        remoteView = remoteProxy.getDisplay();
        remoteContainer.addView(remoteView);
    }

    public void enableVideoView() {
        Log.i(TAG, "enableVideoView: ");
        VAGameAPI.getInstance().enableVideoChatWithLocalAndRemoteView(localProxy, remoteProxy); }

    public void loadFaceRigItf(String filePath, String fileName, String bgPath, String bgName) {
        Log.i(TAG, "loadFaceRigItf: ");
        videoProcessorToCamera = VAGameAPI.getInstance().getVideoProcessorToCamera(); // 摄像头
        if (videoProcessorToCamera == null) {
            throw new NullPointerException("videoProcessorToCamera is null");
        }

        boolean isEnabled = videoProcessorToCamera.native_faceRigEnabled();
        videoProcessorToCamera.native_start();
        videoProcessorToCamera.native_setEnableFaceRigSource(true);

        if (!isEnabled) {
            faceRigItf = videoProcessorToCamera.native_faceRigItf();
//            String path = getApplicationInfo().sourceDir;
            faceRigItf.native_setLive2DModel(filePath, fileName);
            faceRigItf.native_showFaceTrack(false);
            faceRigItf.native_setModelOuputSize(320, 640);
            faceRigItf.native_setDetectFPS(1);
//            float[] floats=faceRigItf.native_modelOffset();
            faceRigItf.native_setOnFaceDetectListener(have -> {
                Log.i("", have ? "yes" : "no");
                // TODO: 2018/5/30 检测人脸 5s后为检测到人脸 弹提示，需转主线程
                if (mFaceDetectListener != null) {
                    mFaceDetectListener.onEvent(have);
                }
            });
            faceRigItf.native_setModelZoomFraction(1.0f); // 缩放
            faceRigItf.native_setModelBackgroundImage(bgPath, bgName);
        }
    }


    public void sendMessage(String msg) { VAGameAPI.getInstance().sendGameMessage(msg); }

    public void sendGift(int giftId) { VAGameAPI.getInstance().sendGift(giftId); }

    public void setLive2DModel(String filePath, String fileName) {
        if (faceRigItf == null) {
            throw new NullPointerException("faceRigItf is null");
        }
        faceRigItf.native_setLive2DModel(filePath, fileName);
    }

    public void setFaceRigItf(float zoom, float left, float right) {
        if (faceRigItf == null) {
            throw new NullPointerException("faceRigItf is null");
        }
        faceRigItf.native_setModelZoomFraction(zoom); // 缩放
        faceRigItf.native_setModelOffset(left, right);
    }

    public void setModelBackgroundImage(String filePath, String fileName) {
        if (faceRigItf == null) {
            throw new NullPointerException("faceRigItf is null");
        }
        faceRigItf.native_setModelBackgroundImage(filePath, fileName );
    }

    public void destroy(boolean isLoad) {
        Log.i(TAG, "destroy: ");
        if (mBridgeWebView != null) {
            if (mWebSettings != null) {
                mWebSettings = null;
            }
            mBridgeWebView.clearFormData(); // 仅清除自动完成填充的表单数据
            mBridgeWebView.clearCache(true); //  清除网页访问留下的缓存,针对整个应用程序
            mBridgeWebView.clearHistory();              //  清除当前webview访问的历史记录
            mBridgeWebView.destroy();
            mBridgeWebView = null;
        }

        videoProcessorToCamera = VAGameAPI.getInstance().getVideoProcessorToCamera(); // 摄像头
        if (videoProcessorToCamera != null) {
            videoProcessorToCamera.native_stop();
            videoProcessorToCamera = null;
        }

        VAGameAPI.getInstance().stopGameMatch();
        if (isLoad) {
            VAGameAPI.getInstance().leaveGameRoom();
        }

        VAGameAPI.getInstance().stopPreview();

        if (localProxy != null) {
            localProxy = null;
        }
        if (remoteProxy != null) {
            remoteProxy = null;
        }


    }


}
