package com.kachat.game.ui.graduate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import com.RtcVideoProcess.FaceRigItf;
import com.RtcVideoProcess.VideoProcessItf;
import com.dnion.RenderProxy;
import com.dnion.SharedRTCEnv;
import com.dnion.VAChatAPI;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.Constant;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.dbmodel.DbUserBean;
import com.kachat.game.ui.graduate.fragments.LiveBackGroundModeListFragment;
import com.kachat.game.ui.graduate.fragments.LivePersonModeListFragment;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import java.util.Objects;
import butterknife.BindView;
import butterknife.OnClick;

public class GraduateSchoolActivity extends BaseActivity implements LivePersonModeListFragment.OnSwitchListener,
        LiveBackGroundModeListFragment.OnSwitchListener{

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, GraduateSchoolActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar_base)
    Toolbar mToolbar;

    @BindView(R.id.fl_Container)
    FrameLayout mFlContainer;

    @BindView(R.id.cl_Bottom_Item)
    LinearLayoutCompat mClBottomItem;
    @BindView(R.id.sdv_Left)
    SimpleDraweeView mSdvLeft;
    @BindView(R.id.fl_PropsList)   //容器
    FrameLayout mFlPropsList;
    @BindView(R.id.sdv_Right)
    SimpleDraweeView mSdvRight;

    @BindView(R.id.llc_Role)
    LinearLayoutCompat mLlcRole;
    @BindView(R.id.llc_Prop)
    LinearLayoutCompat mLlcProp;
    @BindView(R.id.llc_Voice)
    LinearLayoutCompat mLlcVoice;
    @BindView(R.id.llc_BackGround)
    LinearLayoutCompat mLlcBackGround;
    @BindView(R.id.llc_Bottom)
    LinearLayoutCompat mLlcBottom;

    private FragmentTransaction transaction=null;
    private Fragment mFragmentPeople= LivePersonModeListFragment.getInstance();
    private Fragment mFragmentBG= LiveBackGroundModeListFragment.getInstance();
    private boolean isLoadLivePerson=false;
    private boolean isLoadLiveBG=false;


    private RenderProxy fullRenderProxy=null;
    private SurfaceView localSFView=null;
    private VideoProcessItf videoProcessorToCamera=null;
    private FaceRigItf faceRigItf=null;


    @Override
    protected int onSetResourceLayout() {   return R.layout.activity_graduate_school;    }

    @Override
    protected boolean onSetStatusBar() {   return true;   }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (getImmersionBar() != null) {
            getImmersionBar().titleBar(mToolbar).transparentStatusBar().init();
        }
    }

    @Override
    protected void onInitView() {
        getToolBarBack().setOnClickListener(v->this.finish());
        initVideo();
        initLiveProps();
    }

    private void initVideo() {
        DbUserBean dbUserBean= DaoQuery.queryUserData();
        VAChatAPI.getInstance().connect(Constant.CHAT_SDK_URL, Objects.requireNonNull(dbUserBean).getUid()+"", dbUserBean.getToken());
        fullRenderProxy = SharedRTCEnv.getInstance().createRenderProxy(getApplicationContext());
        fullRenderProxy.setAspectMode(RenderProxy.AspectMode.aspectFit);
        fullRenderProxy.setEnableMirror(true);
        localSFView = fullRenderProxy.getDisplay();
        mFlContainer.addView(localSFView);

        VAChatAPI.getInstance().startPreview(localSFView,false);

        videoProcessorToCamera = VAChatAPI.getInstance().getVideoProcessorToCamera(); // 摄像头
        if (videoProcessorToCamera == null) {
            return;
        }

        boolean isEnabled = videoProcessorToCamera.native_faceRigEnabled();
        videoProcessorToCamera.native_start();
        videoProcessorToCamera.native_setEnableFaceRigSource(true);

        if (!isEnabled) {
            faceRigItf = videoProcessorToCamera.native_faceRigItf();
//            String path = getApplicationInfo().sourceDir;
            faceRigItf.native_setLive2DModel("live2d/miyo", "miyo");
            faceRigItf.native_showFaceTrack(false);
            faceRigItf.native_setModelOuputSize(360, 640);
            faceRigItf.native_setDetectFPS(5);
//            float[] floats=faceRigItf.native_modelOffset();
//            faceRigItf.native_setModelOffset();
            faceRigItf.native_setOnFaceDetectListener( have -> {
                Log.i("", have ? "yes" : "no" );
                // TODO: 2018/5/30 检测人脸 5s后为检测到人脸 弹提示，需转主线程
            });
            faceRigItf.native_setModelZoomFraction(1.0f); // 缩放
//            faceRigItf.native_modelZoomFraction()
            faceRigItf.native_setModelBackgroundImage("", "bg2.png");
        }

    }

    @OnClick({R.id.sdv_ToolBar_Base_Back, R.id.sdv_Left, R.id.sdv_Right, R.id.llc_Role, R.id.llc_Prop, R.id.llc_Voice, R.id.llc_BackGround})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sdv_ToolBar_Base_Back:
                finish();
                break;
            case R.id.sdv_Left:
                break;
            case R.id.sdv_Right:
                break;
            case R.id.llc_Role:
                loadLive2DPersons();
                break;
            case R.id.llc_Prop:
                @SuppressLint("InflateParams")
                View containerView=LayoutInflater.from(this).inflate(R.layout.dialog_game_introductions,null);
                new AlterDialogBuilder(this,"游戏说明",containerView);
                break;
            case R.id.llc_Voice:
                break;
            case R.id.llc_BackGround:
                loadLive2DBackGround();
                break;
        }
    }

    private void initLiveProps() {
        LivePersonModeListFragment.getInstance().setOnSwitchListener(this);
        LiveBackGroundModeListFragment.getInstance().setOnSwitchListener(this);
    }

    /**
     * Live2D 人物
     */
    private void loadLive2DPersons(){
        mClBottomItem.setVisibility(mClBottomItem.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        if (mClBottomItem.getVisibility() == View.VISIBLE) {
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fl_PropsList,mFragmentPeople);
            transaction.commit();
            isLoadLivePerson=true;
        } else {
            transaction.remove(mFragmentPeople);
            isLoadLivePerson=false;
        }
    }

    @Override
    public void onLivePersonEvent(String fileName) {
        if (faceRigItf != null) {
            if (!TextUtils.isEmpty(fileName)) {
                faceRigItf.native_setLive2DModel("live2d/"+fileName, fileName);
                switch (fileName) {
                    case "battlesister":{
                        faceRigItf.native_setModelZoomFraction(2f); // 缩放
                        faceRigItf.native_setModelOffset(0,-80);
                        break;
                    }
                    case "child":{
                        faceRigItf.native_setModelZoomFraction(2f); // 缩放
                        faceRigItf.native_setModelOffset(0,-20);
                        break;
                    }
                    case "elf":{
                        faceRigItf.native_setModelZoomFraction(1.7f); // 缩放
                        faceRigItf.native_setModelOffset(0,-50);
                        break;
                    }
                    case "game":{
                        faceRigItf.native_setModelZoomFraction(1.4f); // 缩放
                        faceRigItf.native_setModelOffset(-15,0);
                        break;
                    }
                    case "zhishi":{
                        faceRigItf.native_setModelZoomFraction(1.8f); // 缩放
                        faceRigItf.native_setModelOffset(0,-60);
                        break;
                    }
                    case "cobori10":{
                        faceRigItf.native_setModelZoomFraction(1.2f); // 缩放
                        faceRigItf.native_setModelOffset(0,0);
                        break;
                    }
                    case "miku_f":{
                        faceRigItf.native_setModelZoomFraction(1.0f); // 缩放
                        faceRigItf.native_setModelOffset(0,0);
                        break;
                    }
                    case "miyo":{
                        faceRigItf.native_setModelZoomFraction(1.0f); // 缩放
                        faceRigItf.native_setModelOffset(0,0);
                        break;
                    }
                }
            }
        }else {
            throw new NullPointerException("the faceRigItf is null");
        }
    }


    /**
     * Live2D 背景
     */

    private void loadLive2DBackGround(){
        mClBottomItem.setVisibility(mClBottomItem.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        if (mClBottomItem.getVisibility() == View.VISIBLE) {
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fl_PropsList,mFragmentBG);
            transaction.commit();
            isLoadLiveBG=true;
        } else {
            transaction.remove(mFragmentBG);
            isLoadLiveBG=false;
        }
    }

    @Override
    public void onLiveBackGroundEvent(String fileName) {
        if (faceRigItf != null) {
            if (!TextUtils.isEmpty(fileName)) {
                faceRigItf.native_setModelBackgroundImage("", fileName + ".png");
            }
        }
    }




    @Override
    protected void onDestroy() {
        if (isLoadLivePerson) {  transaction.remove(mFragmentPeople);  }

        if (isLoadLiveBG) {  transaction.remove(mFragmentBG);  }

        mFlContainer.removeView(localSFView);
        faceRigItf=null;
        fullRenderProxy=null;
        localSFView=null;
        videoProcessorToCamera=null;
        VAChatAPI.getInstance().stopPreview();
        VAChatAPI.getInstance().disconnect();
        super.onDestroy();
    }



}
