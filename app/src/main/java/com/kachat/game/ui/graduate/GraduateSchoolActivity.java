package com.kachat.game.ui.graduate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.Config;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.base.BaseFragment;
import com.kachat.game.SdkApi;
import com.kachat.game.events.DNGameEventMessage;
import com.kachat.game.events.PublicEventMessage;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.libdata.model.ErrorBean;
import com.kachat.game.libdata.model.LivesBean;
import com.kachat.game.libdata.mvp.OnPresenterListeners;
import com.kachat.game.libdata.mvp.presenters.LivesPresenter;
import com.kachat.game.ui.MainActivity;
import com.kachat.game.ui.graduate.fragments.LiveBackGroundModeListFragment;
import com.kachat.game.ui.graduate.fragments.LivePersonModeListFragment;
import com.kachat.game.ui.graduate.fragments.LiveAudioModeListFragment;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;

public class GraduateSchoolActivity extends BaseActivity  {

    private static final String TAG = "GraduateSchoolActivity";
    public static final int LAYOUT_FIGURES_MASK=0;
    public static final int LAYOUT_ACCESSORY=1;
    public static final int LAYOUT_VOICE=2;
    public static final int LAYOUT_BACKGROUND=3;

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

    private LivesPresenter mLivesPresenter = null;
    private String personPath = "live2d/";
    private String bgPath = "livebg";

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_graduate_school;
    }

    @Override
    protected boolean onSetStatusBar() {
        return true;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (getImmersionBar() != null) {
            getImmersionBar().titleBar(mToolbar).transparentStatusBar().init();
        }
    }

    @Override
    protected void onInitView() {
        getToolBarBack().setOnClickListener(v -> finish());
        getToolbarMenu().setImageResource(R.drawable.icon_graduate_school);
        getToolbarMenu().setOnClickListener(v -> {
            boolean isSave=SdkApi.getInstance().save();
            if (isSave) {
                Config.setIsFiguresMask(isSave);
                new AlterDialogBuilder(this,new DialogTextView(this,"保存成功!")).hideRootSure();
            }else {
                new AlterDialogBuilder(this,new DialogTextView(this,"保存失败!")).hideRootSure();
            }
        });
        int size= DaoQuery.queryListModelListSize();
        if (size == 0) {
            mLivesPresenter = new LivesPresenter(new MaskCallBack());
            mLivesPresenter.attachPresenter();
        }else {
            String fileName= Objects.requireNonNull(DaoQuery.queryModelListData()).get(0).getLiveFileName();
            String bgName= Objects.requireNonNull(DaoQuery.queryModelListData()).get(0).getBgFileName();
            initVideo(fileName, bgName);
        }
        initLive();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    private void initVideo(String model, String bgImg) {
        SdkApi.getInstance().create(this);
        SdkApi.getInstance().loadLocalView(this, mFlContainer);
        SdkApi.getInstance().enableVideoView();
        SdkApi.getInstance().loadFaceRigItf(personPath + model, model, bgPath, bgImg,3);
    }

    private class MaskCallBack implements OnPresenterListeners.OnViewListener<LivesBean> {
        @Override
        public void onSuccess(LivesBean result) {
            Log.i(LivePersonModeListFragment.TAG, "onSuccess 1: "+result.toString());
            if (result.getLives() != null) {
                if (result.getLives().size() > 0 && !TextUtils.isEmpty(result.getLives().get(0).getLive().getName())) {
                    int size=result.getLives().size();
                    for (int i = 0; i < size; i++) {
                        if (result.getLives().get(i).getLive_number() != 0){
                            Log.i(LivePersonModeListFragment.TAG, "onSuccess 2: "+result.getLives().get(i).getLive().getName());
                            initVideo(result.getLives().get(i).getLive().getName(), "bg_1.png");
                            break;
                        }
                    }
                }
            }else {
                Toast("服务器异常！");
            }
        }
        @Override
        public void onFailed(int errorCode, ErrorBean error) {
            if (error != null &&  !TextUtils.isEmpty(error.getToast())) {
                Toast(error.getToast());
            }
        }
        @Override
        public void onError(Throwable e) {
            if (e != null) {
                Logger(e.getMessage());
                Toast(e.getMessage());
            }
        }
    }


    private void initLive() {
        loadLive2DPersons();
        mRgTabs.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.llc_Role:
                    loadLive2DPersons();
                    break;
                case R.id.llc_Prop:
                    break;
                case R.id.llc_Voice:
                    loadVoice();
                    break;
                case R.id.llc_BackGround:
                    loadLive2DBackGround();
                    break;
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PublicEventMessage.OnGraduateEvent event) {
        if (event != null) {
            switch (event.getType()) {
                case LAYOUT_FIGURES_MASK: {  //人物遮罩
                    SdkApi.getInstance().setLive2DModel(personPath + event.getMsg(), event.getMsg(),3);
                    break;
                }
                case LAYOUT_ACCESSORY: {  //饰品
                    break;
                }
                case LAYOUT_VOICE: {   //变声
                    int pitch=5;
                    switch (event.getMsg()) {
                        case "level_1":pitch=1;break;
                        case "level_2":pitch=2;break;
                        case "level_3":pitch=3;break;
                        case "level_4":pitch=4;break;
                        case "level_5":pitch=5;break;
                    }
                    SdkApi.getInstance().setAudioPitch(pitch);
                    break;
                }
                case LAYOUT_BACKGROUND: { //背景
                    SdkApi.getInstance().setModelBackgroundImage(bgPath, event.getMsg());
                    break;
                }
            }
        }
    }

    int broken=0;
    @SuppressLint("InflateParams")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DNGameEventMessage event) {
        switch (event.getEvent()) {
            case SESSION_BROKEN: {
                Log.i(TAG, "onEvent: SESSION_BROKEN");
                broken++;
                if (broken==7) {
                    AlterDialogBuilder dialogOccupy=new AlterDialogBuilder(this, new DialogTextView(this, "连接异常，请重新登录！"),"退出").hideClose();
                    dialogOccupy.getRootSure().setOnClickListener(v -> {
                        broken=0;
                        dialogOccupy.dismiss();
                        PublicEventMessage.ExitAccount(this);
                        finish();
                    });
                }
                break;
            }
            case SESSION_OCCUPY: {
                Log.i(TAG, "onEvent: SESSION_OCCUPY");
                AlterDialogBuilder dialogOccupy=new AlterDialogBuilder(this, new DialogTextView(this, "账号异地登录，请重新登录！"),"退出").hideClose();
                dialogOccupy.getRootSure().setOnClickListener(v -> {
                    dialogOccupy.dismiss();
                    PublicEventMessage.ExitAccount(this);
                    finish();
                });
                break;
            }
        }
    }

    private void loadLive2DPersons() { getSupportFragmentManager().beginTransaction().replace(R.id.fl_PropsList, createFragment(0)).commit(); }
    private void loadVoice() { getSupportFragmentManager().beginTransaction().replace(R.id.fl_PropsList, createFragment(2)).commit(); }
    private void loadLive2DBackGround() { getSupportFragmentManager().beginTransaction().replace(R.id.fl_PropsList, createFragment(3)).commit(); }

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, BaseFragment> sFragmentHashMap = new HashMap<>();
    public BaseFragment createFragment(int position) {
        BaseFragment baseFragment = sFragmentHashMap.get(position);
        if (baseFragment == null) {
            switch (position) {
                case 0: baseFragment = LivePersonModeListFragment.getInstance();break;
                case 1: new AlterDialogBuilder(GraduateSchoolActivity.this,new DialogTextView(GraduateSchoolActivity.this,"功能暂未开放，敬请期待!")).hideRootSure();break;
                case 2: baseFragment = LiveAudioModeListFragment.getInstance();break;
                case 3: baseFragment = LiveBackGroundModeListFragment.getInstance();break;
            }
        }
        return baseFragment;
    }

//    private void setFigureMaskConfig(String fileName){
//        if (!TextUtils.isEmpty(fileName)) {
//            float[][] params=null;
//            switch (fileName) {
//                case "aLaiKeSi": mDbLive2DBean.setFiguresMask(params = new float[][]{{1.0f, 0f, 0f}, {1.5f, 0f, -0.1333f}}); break;
//                case "haru": mDbLive2DBean.setFiguresMask(params = new float[][]{{2.3f,0f,-0.625f},{2.5f,0f,-0.7f}});break;
//                case "kaPa": mDbLive2DBean.setFiguresMask(params = new float[][]{{1.5f,-0.0416f,0f},{1.5f,0f,-0.041f}}); break;
//                case "lanTiYa": mDbLive2DBean.setFiguresMask(params = new float[][]{{1.9f,0f,-0.1f},{2.5f,0f,-0.1933f}}); break;
//                case "murahana": mDbLive2DBean.setFiguresMask(params = new float[][]{{1.7f,0f,-0.1f},{2.0f,0f,-0.05f}}); break;
//                case "neiLin": mDbLive2DBean.setFiguresMask(params = new float[][]{{1.8f,0f,-0.0937f},{2.5f,0f,-0.2533f}}); break;
//                case "natori": mDbLive2DBean.setFiguresMask(params = new float[][]{{2.5f,0f,-0.5937f},{3.2f,0f,-0.85f}}); break;
//                case "tiYaNa": mDbLive2DBean.setFiguresMask(params = new float[][]{{1.9f,0f,-0.125f},{2.5f,0f,-0.1933f}}); break;
//                case "weiKeTa": mDbLive2DBean.setFiguresMask(params = new float[][]{{2.0f,0f,-0.0468f},{3.2f,0f,-0.2433f}}); break;
//                case "xingChen": mDbLive2DBean.setFiguresMask(params = new float[][]{{1.0f,0f,-0.1f},{1.7f,0f,-0.1333f}}); break;
//                case "yuLu": mDbLive2DBean.setFiguresMask(params = new float[][]{{1.0f,0f,-0.1f},{1.7f,0f,-0.05f}}); break;
//                case "yangYan": mDbLive2DBean.setFiguresMask(params = new float[][]{{1.0f,0f,-0.1f},{1.7f,0f,-0.1333f}}); break;
//            }
//            assert params != null;
//            SdkApi.getInstance().setLive2DModel(personPath + fileName, fileName);
//            SdkApi.getInstance().setFaceRigItf(params[0][1],params[0][2],params[0][3]);
//        }
//    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {

        SdkApi.getInstance().destroy(true);

        if (mFlContainer.getChildCount() > 0) {
            mFlContainer.removeAllViews();
        }
        if (sFragmentHashMap != null) {
            sFragmentHashMap.clear();
        }

        Log.i(TAG, "onDestroy: ");
        if (mLivesPresenter != null) {
            mLivesPresenter.detachPresenter();
            mLivesPresenter=null;
        }

        super.onDestroy();
    }
}
