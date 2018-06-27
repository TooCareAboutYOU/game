package com.kachat.game.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import com.RtcVideoProcess.FaceRigItf;
import com.RtcVideoProcess.VideoProcessItf;
import com.dnion.RenderProxy;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.ui.graduate.fragments.LivePersonModeListFragment;
import butterknife.BindView;

public class ChatActivity extends BaseActivity{

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        context.startActivity(intent);
    }

    private static final String TAG = "ChatActivity";
    
    @BindView(R.id.cl_fullscreenView)
    FrameLayout mClFullscreenView;
    @BindView(R.id.fl_Live2DList)
    FrameLayout mFlLive2DList;
    @BindView(R.id.seekbar)
    SeekBar mSeekBar;


    private FragmentTransaction transaction=null;
    private Fragment mFragmentPeople= LivePersonModeListFragment.getInstance();


    private RenderProxy fullRenderProxy;
    private SurfaceView localSFView;
    private VideoProcessItf videoProcessorToCamera=null;
    private FaceRigItf faceRigItf=null;
    private boolean SwitchBg=false;
    private boolean isHide=false;
    boolean isShow=false;
    @Override
    protected int onSetResourceLayout() { return R.layout.activity_chat; }

    @Override
    protected boolean onSetStatusBar() {
        return false;
    }

    @Override
    protected void onInitView() {
//        DbUserBean dbUserBean=DaoQuery.queryUserData();
//        VAChatAPI.getInstance().connect(Constant.CHAT_SDK_URL, Objects.requireNonNull(dbUserBean).getUid()+"", dbUserBean.getToken());
//        fullRenderProxy = SharedRTCEnv.getInstance().createRenderProxy(getApplicationContext());
//        fullRenderProxy.setAspectMode(RenderProxy.AspectMode.aspectFit);
//        fullRenderProxy.setEnableMirror(true);
//        localSFView = fullRenderProxy.getDisplay();
//        mClFullscreenView.addView(localSFView);
//        VAChatAPI.getInstance().startPreview(localSFView,false);
//
//        videoProcessorToCamera = VAChatAPI.getInstance().getVideoProcessorToCamera(); // 摄像头
//        if (videoProcessorToCamera == null) {
//            return;
//        }
//
//        boolean isEnabled = videoProcessorToCamera.native_faceRigEnabled();
//        videoProcessorToCamera.native_start();
//        videoProcessorToCamera.native_setEnableFaceRigSource(true);
//
//        if (!isEnabled) {
//            faceRigItf = videoProcessorToCamera.native_faceRigItf();
//            String path = getApplicationInfo().sourceDir;
//            faceRigItf.native_setLive2DModel("live2d/miyo", "miyo");
//            faceRigItf.native_showFaceTrack(false);
//            faceRigItf.native_setModelOuputSize(360, 640);
//            faceRigItf.native_setDetectFPS(5);
////            float[] floats=faceRigItf.native_modelOffset();
////            faceRigItf.native_setModelOffset();
//            faceRigItf.native_setOnFaceDetectListener( have -> {
//                Log.i("", have ? "yes" : "no" );
//                // TODO: 2018/5/30 检测人脸 5s后为检测到人脸 弹提示，需转主线程
//            });
//            faceRigItf.native_setModelZoomFraction(1.0f); // 缩放
////            faceRigItf.native_modelZoomFraction()
//            faceRigItf.native_setModelBackgroundImage("", "bg_1.png");
//        }
//
//        transaction=getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.fl_Live2DList,mFragmentPeople);
//        transaction.commit();
//
//        AppCompatButton btn=findViewById(R.id.acBtn_switch_bg);
//        btn.setOnClickListener(v -> {
//            SwitchBg =!SwitchBg;
//            if (SwitchBg) {
//                btn.setText("Bg2");
//                faceRigItf.native_setModelBackgroundImage("", "bg2.png");
//            }else {
//                btn.setText("Bg1");
//                faceRigItf.native_setModelBackgroundImage("", "bg_1.png");
//            }
//        });
//
//        findViewById(R.id.acBtn_switch_hudj).setOnClickListener(v -> {
//            HashMap cfg = new HashMap<String, Boolean>() {{
//                put("hudiejie1", isHide);
//                put("hudiejie2", isHide);
//                put("hudiejie3", isHide);
//            }};
//            faceRigItf.native_setModelVisibilityConfig(cfg);
//            isHide =!isHide;
//        });
//
//        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                faceRigItf.native_setModelZoomFraction((float) progress); // 缩放
//            }
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {  }
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {  }
//        });
//
//        findViewById(R.id.acBtn_switch_show).setOnClickListener(v -> {
//            isShow =!isShow;
//            mFlLive2DList.setVisibility(isShow ? View.GONE: View.VISIBLE);
//        });
    }

    @Override
    protected void onInitData(@Nullable Bundle savedInstanceState) {
    }


//        if (faceRigItf != null) {
//            if (!TextUtils.isEmpty(fileName)) {
//                faceRigItf.native_setLive2DModel("live2d/"+fileName, fileName);
//                switch (fileName) {
//                    case "battlesister":{
//                        faceRigItf.native_setModelZoomFraction(2f); // 缩放
//                        faceRigItf.native_setModelOffset(0,-80);
//                        break;
//                    }
//                    case "child":{
//                        faceRigItf.native_setModelZoomFraction(2f); // 缩放
//                        faceRigItf.native_setModelOffset(0,-20);
//                        break;
//                    }
//                    case "elf":{
//                        faceRigItf.native_setModelZoomFraction(1.7f); // 缩放
//                        faceRigItf.native_setModelOffset(0,-50);
//                        break;
//                    }
//                    case "game":{
//                        faceRigItf.native_setModelZoomFraction(1.4f); // 缩放
//                        faceRigItf.native_setModelOffset(-15,0);
//                        break;
//                    }
//                    case "zhishi":{
//                        faceRigItf.native_setModelZoomFraction(1.8f); // 缩放
//                        faceRigItf.native_setModelOffset(0,-60);
//                        break;
//                    }
//                    case "cobori10":{
//                        faceRigItf.native_setModelZoomFraction(1.2f); // 缩放
//                        faceRigItf.native_setModelOffset(0,0);
//                        break;
//                    }
////                    case "miku_f":{
////                        faceRigItf.native_setModelZoomFraction(1.0f); // 缩放
////                        faceRigItf.native_setModelOffset();
////                        break;
////                    }
////                    case "miyo":{
////                        faceRigItf.native_setModelZoomFraction(1.0f); // 缩放
////                        faceRigItf.native_setModelOffset();
////                        break;
////                    }
//                }
//            }
//        }else {
//            throw new NullPointerException("the faceRigItf is null");
//        }

    @Override
    protected void onDestroy() {
//        faceRigItf=null;
//        fullRenderProxy=null;
//        localSFView=null;
//        videoProcessorToCamera=null;
//        mClFullscreenView.removeView(localSFView);
//        transaction.remove(mFragmentPeople);
//        VAChatAPI.getInstance().stopPreview();
//        VAChatAPI.getInstance().disconnect();
        super.onDestroy();

    }


}
