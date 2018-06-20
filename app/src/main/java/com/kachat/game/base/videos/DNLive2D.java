package com.kachat.game.base.videos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;
import com.RtcVideoProcess.FaceRigItf;
import com.RtcVideoProcess.VideoProcessItf;
import com.dnion.RenderProxy;
import com.dnion.SharedRTCEnv;
import com.dnion.VAChatAPI;
import com.kachat.game.Constant;

public class DNLive2D {

//    private RenderProxy fullRenderProxy=null;
//    private VideoProcessItf videoProcessorToCamera=null;
//    private FaceRigItf faceRigItf=null;
//
//    public interface OnFaceDetectListener{
//        void onEvent(boolean isHave);
//    }
//    private OnFaceDetectListener mFaceDetectListener=null;
//    public void setOnFaceDetectListener(OnFaceDetectListener listener){ this.mFaceDetectListener=listener; }

//
//    private DNLive2D(){}
//    @SuppressLint("StaticFieldLeak")
//    public static DNLive2D getInstance(){  return DNLive2DHolder.instance;  }
//    private static class DNLive2DHolder{
//        @SuppressLint("StaticFieldLeak")
//        private static final DNLive2D instance=new DNLive2D();
//    }
//
//    public void init(String sdkUrl, String uid,String token){
//        VAChatAPI.getInstance().connect(sdkUrl, uid, token);
//    }
//
//    public RenderProxy createRenderProxy(Context context){
//        fullRenderProxy = SharedRTCEnv.getInstance().createRenderProxy(context.getApplicationContext());
//        fullRenderProxy.setAspectMode(RenderProxy.AspectMode.aspectFit);
//        fullRenderProxy.setEnableMirror(true);
//        return fullRenderProxy;
//    }
//
//    public SurfaceView createView(RenderProxy renderProxy){
//        if (renderProxy.getDisplay() == null) {
//            throw new NullPointerException("createView is null");
//        }
//        return renderProxy.getDisplay();
//    }
//
//    public void startView(SurfaceView surfaceView){
//        if (surfaceView == null) {
//            throw new NullPointerException("mSurfaceView is no init");
//        }
//
//        VAChatAPI.getInstance().startPreview(surfaceView,false);
//    }
//
//
//    public void initFaceRigItf(String filePath,String fileName,String bgPath,String bgName){
//
//        videoProcessorToCamera = VAChatAPI.getInstance().getVideoProcessorToCamera(); // 摄像头
//        if (videoProcessorToCamera == null) {
//            throw new NullPointerException("videoProcessorToCamera is null");
//        }
//
//        boolean isEnabled = videoProcessorToCamera.native_faceRigEnabled();
//        videoProcessorToCamera.native_start();
//        videoProcessorToCamera.native_setEnableFaceRigSource(true);
//
//        if (!isEnabled) {
//            faceRigItf = videoProcessorToCamera.native_faceRigItf();
////            String path = getApplicationInfo().sourceDir;
//            faceRigItf.native_setLive2DModel(filePath, fileName);
//            faceRigItf.native_showFaceTrack(false);
//            faceRigItf.native_setModelOuputSize(360, 640);
//            faceRigItf.native_setDetectFPS(5);
////            float[] floats=faceRigItf.native_modelOffset();
//            faceRigItf.native_setOnFaceDetectListener( have -> {
//                Log.i("", have ? "yes" : "no" );
//                // TODO: 2018/5/30 检测人脸 5s后为检测到人脸 弹提示，需转主线程
//                if (mFaceDetectListener != null) {
//                    mFaceDetectListener.onEvent(have);
//                }
//            });
//            faceRigItf.native_setModelZoomFraction(1.0f); // 缩放
////            faceRigItf.native_modelZoomFraction();
//            faceRigItf.native_setModelBackgroundImage(bgPath, bgName);
//        }
//    }
//
//    public void setLive2DModel(String filePath, String fileName){
//        if (faceRigItf == null) {
//            throw new NullPointerException("faceRigItf is null");
//        }
//
//        faceRigItf.native_setLive2DModel(filePath, fileName);
//    }
//
//    public void setFaceRigItf(float zoom, float left, float right){
//        if (faceRigItf == null) {
//            throw new NullPointerException("faceRigItf is null");
//        }
//        faceRigItf.native_setModelZoomFraction(zoom); // 缩放
//        faceRigItf.native_setModelOffset(left,right);
//    }
//
//    public void setModelBackgroundImage(String filePath, String fileName, String format){
//        if (faceRigItf == null) {
//            throw new NullPointerException("faceRigItf is null");
//        }
//        faceRigItf.native_setModelBackgroundImage(filePath, fileName + format);
//    }
//
//    public void destroy(){
//        reset();
//        VAChatAPI.getInstance().stopPreview();
//        VAChatAPI.getInstance().disconnect();
//    }
//
//    private void reset() {
//        mFaceDetectListener=null;
//        faceRigItf=null;
//        fullRenderProxy=null;
//        videoProcessorToCamera=null;
//    }
}
