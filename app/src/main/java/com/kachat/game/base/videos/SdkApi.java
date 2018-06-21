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

    private Context mContext;
    private WebSettings mWebSettings = null;
    private BridgeWebView mBridgeWebView = null;
    private RenderProxy localProxy = null, remoteProxy = null;
    private SurfaceView localView = null, remoteView = null;
    private VideoProcessItf videoProcessorToCamera = null;
    private FaceRigItf faceRigItf = null;

    public interface OnFaceDetectListener {
        void onEvent(boolean isHave);
    }

    private OnFaceDetectListener mFaceDetectListener = null;

    public void setOnFaceDetectListener(OnFaceDetectListener listener) {
        this.mFaceDetectListener = listener;
    }

    private SdkApi() {
    }

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
        if (mBridgeWebView == null) {
            throw new NullPointerException("mBridgeWebView is null");
        }
        return mBridgeWebView;
    }

    public void sdkLogin(int uid, String token) {
        VAGameAPI.getInstance().login(uid + "", token);
    }

    public void sdkExit() {
        VAGameAPI.getInstance().logout();
    }

    public void loadGame(String htmlUrl) {
        this.mBridgeWebView.loadUrl(htmlUrl);
    }

    /**
     * 0：GAME_TOWER,      //盖房子 9002
     * 1：GAME_POPSTAR,    //消灭星星  9003
     * 2：GAME_HEXSTAR,    //六边形  9004
     * 3：GAME_CHAT,       //聊天
     */
    public void startGameMatch(int gameType) {
        VAGameAPI.getInstance().startGameMatch(gameType);
    }

    public void create() {
        VAGameAPI.getInstance().startPreview();
    }

    public void loadLocalView(Context context, ViewGroup localContainer) {
        this.localProxy = VAGameAPI.getInstance().createRenderProxy(context.getApplicationContext());
        this.localProxy.setAspectMode(RenderProxy.AspectMode.aspectFill);
        this.localView = this.localProxy.getDisplay();
        localContainer.addView(this.localView);
    }

    public void loadRemoteView(Context context, ViewGroup remoteContainer) {
        this.remoteProxy = VAGameAPI.getInstance().createRenderProxy(context.getApplicationContext());
        this.remoteProxy.setAspectMode(RenderProxy.AspectMode.aspectFill);
        this.remoteView = this.remoteProxy.getDisplay();
        remoteContainer.addView(this.remoteView);
    }

    public void enableVideoView() {
        VAGameAPI.getInstance().enableVideoChatWithLocalAndRemoteView(this.localProxy, this.remoteProxy);
    }


    public void loadFaceRigItf(String filePath, String fileName, String bgPath, String bgName) {
        this.videoProcessorToCamera = VAGameAPI.getInstance().getVideoProcessorToCamera(); // 摄像头
        if (this.videoProcessorToCamera == null) {
            throw new NullPointerException("videoProcessorToCamera is null");
        }

        boolean isEnabled = this.videoProcessorToCamera.native_faceRigEnabled();
        this.videoProcessorToCamera.native_start();
        this.videoProcessorToCamera.native_setEnableFaceRigSource(true);

        if (!isEnabled) {
            this.faceRigItf = this.videoProcessorToCamera.native_faceRigItf();
//            String path = getApplicationInfo().sourceDir;
            this.faceRigItf.native_setLive2DModel(filePath, fileName);
            this.faceRigItf.native_showFaceTrack(false);
            this.faceRigItf.native_setModelOuputSize(320, 640);
            this.faceRigItf.native_setDetectFPS(1);
//            float[] floats=faceRigItf.native_modelOffset();
            this.faceRigItf.native_setOnFaceDetectListener(have -> {
                Log.i("", have ? "yes" : "no");
                // TODO: 2018/5/30 检测人脸 5s后为检测到人脸 弹提示，需转主线程
                if (this.mFaceDetectListener != null) {
                    this.mFaceDetectListener.onEvent(have);
                }
            });
            this.faceRigItf.native_setModelZoomFraction(1.0f); // 缩放
            this.faceRigItf.native_setModelBackgroundImage(bgPath, bgName);
        }
    }


    public void sendMessage(String msg) {
        VAGameAPI.getInstance().sendGameMessage(msg);
    }

    public void sendGift(int giftId) {
        VAGameAPI.getInstance().sendGift(giftId);
    }

    public void setLive2DModel(String filePath, String fileName) {
        if (this.faceRigItf == null) {
            throw new NullPointerException("faceRigItf is null");
        }
        this.faceRigItf.native_setLive2DModel(filePath, fileName);
    }

    public void setFaceRigItf(float zoom, float left, float right) {
        if (this.faceRigItf == null) {
            throw new NullPointerException("faceRigItf is null");
        }
        this.faceRigItf.native_setModelZoomFraction(zoom); // 缩放
        this.faceRigItf.native_setModelOffset(left, right);
    }

    public void setModelBackgroundImage(String filePath, String fileName, String format) {
        if (this.faceRigItf == null) {
            throw new NullPointerException("faceRigItf is null");
        }
        this.faceRigItf.native_setModelBackgroundImage(filePath, fileName + format);
    }

    public void destroy(boolean isLoad) {
        if (this.mBridgeWebView != null) {
            if (this.mWebSettings != null) {
                this.mWebSettings = null;
            }
            this.mBridgeWebView.clearFormData(); // 仅清除自动完成填充的表单数据
            this.mBridgeWebView.clearCache(true); //  清除网页访问留下的缓存,针对整个应用程序
            this.mBridgeWebView.clearHistory();              //  清除当前webview访问的历史记录
            this.mBridgeWebView.destroy();
            this.mBridgeWebView = null;
        }

        this.videoProcessorToCamera = VAGameAPI.getInstance().getVideoProcessorToCamera(); // 摄像头
        if (this.videoProcessorToCamera != null) {
            this.videoProcessorToCamera.native_stop();
            this.videoProcessorToCamera = null;
        }

        if (isLoad) {
            VAGameAPI.getInstance().stopGameMatch();
            VAGameAPI.getInstance().leaveGameRoom();
        }

        VAGameAPI.getInstance().stopPreview();

        if (this.localProxy != null) {
            this.localProxy = null;
        }
        if (this.remoteProxy != null) {
            this.remoteProxy = null;
        }


    }


}
