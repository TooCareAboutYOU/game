package com.kachat.game.ui.graduate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.ui.graduate.fragments.LiveBackGroundModeListFragment;
import com.kachat.game.ui.graduate.fragments.LivePersonModeListFragment;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import butterknife.BindView;
import butterknife.OnClick;

public class GraduateSchoolActivity extends BaseActivity implements LivePersonModeListFragment.OnSwitchListener,
        LiveBackGroundModeListFragment.OnSwitchListener {

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

//    private SurfaceView mSurfaceView;

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
        initVideo();
        initLiveProps();
    }

    private void initVideo() {

    }


    private void initLiveProps() {
        LivePersonModeListFragment.getInstance().setOnSwitchListener(this);
        LiveBackGroundModeListFragment.getInstance().setOnSwitchListener(this);
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


//    @Override
//    public void onEvent(boolean isHave) {
//
//    }

    /**  Live2D 人物*/
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

    }


    /**  Live2D 背景*/
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
    }

    @Override
    protected void onDestroy() {
        if (isLoadLivePerson) {  transaction.remove(mFragmentPeople);  }

        if (isLoadLiveBG) {  transaction.remove(mFragmentBG);  }

//        mFlContainer.removeView(mSurfaceView);
        super.onDestroy();
    }
}
