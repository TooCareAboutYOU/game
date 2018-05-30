package com.kachat.game.ui.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.RtcVideoProcess.FaceRigItf;
import com.RtcVideoProcess.VideoProcessItf;
import com.dnion.RenderProxy;
import com.dnion.SharedRTCEnv;
import com.dnion.VAChatAPI;
import com.kachat.game.Config;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.ui.game.fragments.Live2DModeListFragment;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends BaseActivity implements Live2DModeListFragment.OnSwitchListener{

    @BindView(R.id.cl_fullscreenView)
    FrameLayout mClFullscreenView;
    @BindView(R.id.fl_Live2DList)
    FrameLayout mFlLive2DList;
    @BindView(R.id.seekbar)
    SeekBar mSeekBar;


    private RenderProxy fullRenderProxy;
    private SurfaceView localSFView;
    private FaceRigItf faceRigItf=null;
    private boolean SwitchBg=false;
    private boolean isHide=false;
    @Override
    protected int onSetResourceLayout() { return R.layout.activity_chat; }

    @Override
    protected void onInitView() {
        Config.SdkConnect("", "");
        fullRenderProxy = SharedRTCEnv.getInstance().createRenderProxy(getApplicationContext());
        fullRenderProxy.setAspectMode(RenderProxy.AspectMode.aspectFill);
        fullRenderProxy.setEnableMirror(true);
        localSFView = fullRenderProxy.getDisplay();
        mClFullscreenView.addView(localSFView);
        VAChatAPI.getInstance().startPreview(localSFView);

        VideoProcessItf videoProcessorToCamera = VAChatAPI.getInstance().getVideoProcessorToCamera(); // 摄像头
        if (videoProcessorToCamera == null) {
            return;
        }

        boolean isEnabled = videoProcessorToCamera.native_faceRigEnabled();
        videoProcessorToCamera.native_start();
        videoProcessorToCamera.native_setEnableFaceRigSource(true);

        if (!isEnabled) {
            faceRigItf = videoProcessorToCamera.native_faceRigItf();
            String path = getApplicationInfo().sourceDir;
            faceRigItf.native_setLive2DModel("", "miyo");
            faceRigItf.native_showFaceTrack(false);
            faceRigItf.native_setModelOuputSize(360, 640);
            faceRigItf.native_setDetectFPS(5);

            faceRigItf.native_setOnFaceDetectListener( have -> {
                Log.i("", have ? "yes" : "no" );
                // TODO: 2018/5/30 检测人脸 5s后为检测到人脸 弹提示，需转主线程
            });
            faceRigItf.native_setModelZoomFraction(2.0f); // 缩放
//            faceRigItf.native_modelZoomFraction()
            faceRigItf.native_setModelBackgroundImage("", "bg1.png");
        }

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_Live2DList,Live2DModeListFragment.getInstance());
        transaction.commit();

        AppCompatButton btn=findViewById(R.id.acBtn_switch_bg);
        btn.setOnClickListener(v -> {
            SwitchBg =!SwitchBg;
            if (SwitchBg) {
                btn.setText("Bg2");
                faceRigItf.native_setModelBackgroundImage("", "bg2.png");
            }else {
                btn.setText("Bg1");
                faceRigItf.native_setModelBackgroundImage("", "bg1.png");
            }
        });


        findViewById(R.id.acBtn_switch_hudj).setOnClickListener(v -> {
            HashMap cfg = new HashMap<String, Boolean>() {{
                put("hudiejie1", isHide);
                put("hudiejie2", isHide);
                put("hudiejie3", isHide);
            }};
            faceRigItf.native_setModelVisibilityConfig(cfg);
            isHide =!isHide;
        });
//        mSeekBar.setSecondaryProgress(0);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                faceRigItf.native_setModelZoomFraction(progress); // 缩放
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onInitData(@Nullable Bundle savedInstanceState) {
        Live2DModeListFragment.getInstance().setOnSwitchListener(this);
    }

    @Override
    public void onEvent(String filePath, String fileName) {
        faceRigItf.native_setLive2DModel("", fileName);
    }
}
