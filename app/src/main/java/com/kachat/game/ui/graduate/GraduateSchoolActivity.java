package com.kachat.game.ui.graduate;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import com.RtcVideoProcess.FaceRigItf;
import com.RtcVideoProcess.VideoProcessItf;
import com.dnion.RenderProxy;
import com.dnion.VAGameAPI;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.ui.graduate.fragments.LiveBackGroundModeListFragment;
import com.kachat.game.ui.graduate.fragments.LivePersonModeListFragment;
import com.kachat.game.ui.graduate.fragments.LiveVoiceModeListFragment;
import butterknife.BindView;

public class GraduateSchoolActivity extends BaseActivity implements LivePersonModeListFragment.OnSwitchListener,
        LiveBackGroundModeListFragment.OnSwitchListener,
        LiveVoiceModeListFragment.OnSwitchListener{

    private static final String TAG = "GraduateSchoolActivity";

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, GraduateSchoolActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar_base)
    Toolbar mToolbar;

    @BindView(R.id.fl_Container)
    FrameLayout mFlContainer;     // 视频容器

    @BindView(R.id.cl_Bottom_Item)
    ConstraintLayout mClBottomItem;
    @BindView(R.id.sdv_Left)
    SimpleDraweeView mSdvLeft;
    @BindView(R.id.fl_PropsList)   //素材容器
    FrameLayout mFlPropsList;
    @BindView(R.id.sdv_Right)
    SimpleDraweeView mSdvRight;

    @BindView(R.id.llc_Role)
    AppCompatRadioButton mLlcRole;
    @BindView(R.id.llc_Prop)
    AppCompatRadioButton mLlcProp;
    @BindView(R.id.llc_Voice)
    AppCompatRadioButton mLlcVoice;
    @BindView(R.id.llc_BackGround)
    AppCompatRadioButton mLlcBackGround;
    @BindView(R.id.rg_Tabs)
    RadioGroup mRgTabs;

    private Fragment mFragmentPeople= LivePersonModeListFragment.getInstance();
    private Fragment mFragmentVoice= LiveVoiceModeListFragment.getInstance();
    private Fragment mFragmentBG= LiveBackGroundModeListFragment.getInstance();
    private VideoProcessItf videoProcessorToCamera=null;
    private FaceRigItf faceRigItf = null;
    private RenderProxy localProxy=null;

    @Override
    protected int onSetResourceLayout() { return R.layout.activity_graduate_school; }

    @Override
    protected boolean onSetStatusBar() { return true; }

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
        getToolbarMenu().setImageResource(R.drawable.icon_graduate_school);
        getToolbarMenu().setOnClickListener(v -> {});

//        initVideo();
        initLive();
    }

    private void initVideo(String model,String bgImg) {
//        SdkApi.getInstance().create();
//        SdkApi.getInstance().loadLocalView(this, mFlContainer);
//        SdkApi.getInstance().loadFaceRigItf("live2d/miyo", "miyo", "livebg", "bg_1.png");

        VAGameAPI.getInstance().startPreview();
        localProxy = VAGameAPI.getInstance().createRenderProxy(getApplicationContext());
        localProxy.setAspectMode(RenderProxy.AspectMode.aspectFill);
        mFlContainer.addView(localProxy.getDisplay());

         videoProcessorToCamera = VAGameAPI.getInstance().getVideoProcessorToCamera(); // 摄像头
        if (this.videoProcessorToCamera == null) {
            throw new NullPointerException("videoProcessorToCamera is null");
        }

        boolean isEnabled = videoProcessorToCamera.native_faceRigEnabled();
        videoProcessorToCamera.native_start();
        videoProcessorToCamera.native_setEnableFaceRigSource(true);

        if (!isEnabled) {
            faceRigItf = videoProcessorToCamera.native_faceRigItf();
            faceRigItf.native_setLive2DModel("live2d/"+model, model);
            faceRigItf.native_showFaceTrack(false);
            faceRigItf.native_setModelOuputSize(320, 640);
            faceRigItf.native_setDetectFPS(1);
            this.faceRigItf.native_setOnFaceDetectListener(have -> {
                Log.i("", have ? "yes" : "no");
                // TODO: 2018/5/30 检测人脸 5s后为检测到人脸 弹提示，需转主线程
            });
            this.faceRigItf.native_setModelZoomFraction(1.0f); // 缩放
            this.faceRigItf.native_setModelBackgroundImage("livebg", bgImg);
        }

    }


    private void initLive() {
        mRgTabs.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.llc_Role: loadLive2DPersons(); break;
                case R.id.llc_Prop: break;
                case R.id.llc_Voice: loadVoice(); break;
                case R.id.llc_BackGround: loadLive2DBackGround(); break;
            }
        });

        loadLive2DPersons();
        LivePersonModeListFragment.getInstance().setOnSwitchListener(this);
        LiveVoiceModeListFragment.getInstance().setOnSwitchListener(this);
        LiveBackGroundModeListFragment.getInstance().setOnSwitchListener(this);
    }

    /**
     * Live2D人物
     */
    private void loadLive2DPersons(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_PropsList,mFragmentPeople).commit();
    }

    @Override
    public void onLivePersonEvent(String fileName) {
        Log.i(TAG, "onLivePersonEvent: "+fileName);
    }

    /**
     * 道具
     */
    private void loadProps(){
//        @SuppressLint("InflateParams")
//        View containerView=LayoutInflater.from(this).inflate(R.layout.dialog_introductions,null);
//        new AlterDialogBuilder(this,"游戏说明",containerView);
    }

    private void loadVoice(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_PropsList,mFragmentVoice).commit();
    }

    @Override
    public void onLiveVoiceEvent(String fileName) {
        Log.i(TAG, "onLiveVoiceEvent: "+fileName);
    }


    /**
     * Live2D 背景
     */
    private void loadLive2DBackGround(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_PropsList,mFragmentBG).commit();
    }

    @Override
    public void onLiveBackGroundEvent(String fileName) {
        Log.i(TAG, "onLiveBackGroundEvent: "+fileName);
    }









    @Override
    protected void onDestroy() {

//        SdkApi.getInstance().destroy(false);

        this.videoProcessorToCamera = VAGameAPI.getInstance().getVideoProcessorToCamera(); // 摄像头
        if (this.videoProcessorToCamera != null) {
            this.videoProcessorToCamera.native_stop();
            this.videoProcessorToCamera=null;
        }

        VAGameAPI.getInstance().stopPreview();

        if (this.localProxy != null) {
            this.localProxy = null;
        }

        if (mFlContainer.getChildCount() > 0) {
            mFlContainer.removeAllViews();
        }

        super.onDestroy();
    }
}
