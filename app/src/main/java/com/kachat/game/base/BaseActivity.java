package com.kachat.game.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.Config;
import com.kachat.game.R;
import com.kachat.game.utils.StatusBarUtil;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int onSetResourceLayout();
    protected abstract void onInitView();
    protected abstract void onInitData(@Nullable Bundle savedInstanceState);

    public Toolbar getToolbar(){return findViewById(R.id.toolbar_base); }
    public SimpleDraweeView getBack(){ return findViewById(R.id.sdv_ToolBar_Base_Back);}
    public AppCompatTextView getToolbarTitle(){ return findViewById(R.id.atv_ToolBar_Base_Title);}
    private Unbinder unbinder;


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mAllowFullScreen) {
            this.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (onSetResourceLayout() == 0) {
            setContentView(R.layout.layout_empty);
        }else {
            setContentView(onSetResourceLayout());
        }
        unbinder=ButterKnife.bind(this);

        if (savedInstanceState != null) {
            onInitData(savedInstanceState);
        }else if(getIntent() != null && getIntent().getExtras() != null) {
            onInitData(savedInstanceState);
        }

        if (getToolbar() != null) {
            setSupportActionBar(getToolbar());
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
            }
        }
        if (getToolbarTitle() != null) {
            if (getTitle() != null || !TextUtils.isEmpty(getTitle())) {
                setToolBarTitle(getTitle().toString());
            }
        }
        onInitView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
//        KaChatApplication.getRefWatcher(this).watch(this);
        System.gc(); //activity销毁，提示系统回收
    }

    protected void setToolBarTitle(@SuppressLint("SupportAnnotationUsage") @StringRes String title){
        if (!TextUtils.isEmpty(title)) {
            if (this.getToolbarTitle() != null) {
                this.getToolbarTitle().setText(title);
            }else {
                getToolbar().setTitle(title);
                setSupportActionBar(getToolbar());
            }
        }
    }

    @SuppressLint("ResourceType")
    protected void setToolBarTitle(@IntegerRes int title){
        if (title != 0) {
            if (this.getToolbarTitle() != null) {
                this.getToolbarTitle().setText(title);
            }else {
                getToolbar().setTitle(title);
                setSupportActionBar(getToolbar());
            }
        }
    }


    /** 是否允许全屏 **/
    private boolean mAllowFullScreen = false;
    public void setAllowFullScreen(@SuppressLint("SupportAnnotationUsage") @BoolRes boolean allowFullScreen){ this.mAllowFullScreen=allowFullScreen; }

    /** 是否沉浸状态栏 **/
    private boolean isSetStatusBar = true;
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    public void setSteepStatusBar(@SuppressLint("SupportAnnotationUsage") @BoolRes boolean isSetStatusBar) {
        if (isSetStatusBar) {
            steepStatusBar();
        }
    }


    /** 是否禁止旋转屏幕 **/
    private boolean isAllowScreenRotate = false;
    public void setScreenRotate(@SuppressLint("SupportAnnotationUsage") @BoolRes boolean isAllowScreenRotate) {
        if (!isAllowScreenRotate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    public void Logger(@SuppressLint("SupportAnnotationUsage") @StringRes String msg){
        if (Config.isDebug) {
            Logger.i(msg);
        }
    }

    public void Toast(@SuppressLint("SupportAnnotationUsage") @StringRes String msg){  ToastUtils.showShort(msg);  }
    public void Toast(int msg){  ToastUtils.showShort(String.valueOf(msg));  }

    //放重复点击
    public boolean fastClick(){
        long lastClick=0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick =System.currentTimeMillis();
        return true;
    }


    /** 携带数据的页面跳转  */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /** 含有Bundle通过Class打开编辑界面  */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


}
