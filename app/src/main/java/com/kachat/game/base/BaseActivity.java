package com.kachat.game.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyf.barlibrary.ImmersionBar;
import com.kachat.game.Config;
import com.kachat.game.R;
import com.kachat.game.utils.manager.ActivityManager;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int onSetResourceLayout();
    protected abstract boolean onSetStatusBar();
    protected abstract void onInitView();
    protected void onInitData(@Nullable Bundle savedInstanceState) { }
    private ImmersionBar mImmersionBar;
    protected ImmersionBar getImmersionBar(){
        if (!onSetStatusBar()) {
            throw new NullPointerException("The ImmersionBar is no set ");
        }
        return mImmersionBar;
    }
    protected void initImmersionBar(){
        if (onSetStatusBar()) {
            mImmersionBar=ImmersionBar.with(this);
            mImmersionBar.init();
        }
    }

//    private View mToolBarView =LayoutInflater.from(this).inflate(R.layout.base_toolbar,null);
    public Toolbar getToolbar(){ return findViewById(R.id.toolbar_base); }
    public SimpleDraweeView getToolBarBack(){ return findViewById(R.id.sdv_ToolBar_Base_Back); }
    public AppCompatTextView getToolBarTitle(){ return findViewById(R.id.atv_ToolBar_Base_Title); }
    public SimpleDraweeView getToolbarMenu(){ return findViewById(R.id.sdv_ToolBar_BaseMenu); }


//    private View mLoadView=LayoutInflater.from(this).inflate(R.layout.base_loading,null);
//    public ContentLoadingProgressBar getLoadView(){ return findViewById(R.id.clPb_Loading); }
    private ConstraintLayout getLoadView() {return findViewById(R.id.cl_LoadView);}
    public void showLoadView(){ getLoadView().setVisibility(View.VISIBLE); }
    public void hideLoadView(){ getLoadView().setVisibility(View.GONE); }


    private Unbinder unbinder;

    @SuppressLint({"RestrictedApi", "InflateParams"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        }else {
            onInitData(null);
        }

        ActivityManager.getInstance().addActivity(this);


        if (getToolbar() != null) {
            setSupportActionBar(getToolbar());
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
            }
        }
        if (getToolBarTitle() != null) {
            if (getTitle() != null || !TextUtils.isEmpty(getTitle())) {
                setToolBarTitle(getTitle().toString());
            }
        }


        onInitView();
        initImmersionBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }

        if (onSetStatusBar() && mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        ActivityManager.getInstance().removeActivity(this);

        System.gc(); //activity销毁，提示系统回收
    }



    protected void setToolBarTitle(@SuppressLint("SupportAnnotationUsage") @StringRes String title){
        if (!TextUtils.isEmpty(title)) {
            if (this.getToolBarTitle() != null) {
                this.getToolBarTitle().setText(title);
            }else {
                getToolbar().setTitle(title);
                setSupportActionBar(getToolbar());
            }
        }
    }

    @SuppressLint("ResourceType")
    protected void setToolBarTitle(@IntegerRes int title){
        if (title != 0) {
            if (this.getToolBarTitle() != null) {
                this.getToolBarTitle().setText(title);
            }else {
                getToolbar().setTitle(title);
                setSupportActionBar(getToolbar());
            }
        }
    }

    public void Logger(@SuppressLint("SupportAnnotationUsage") @StringRes String msg){
        if (Config.isDebug) {
            Logger.i(msg);
        }
    }

    public void Toast(String msg){  ToastUtils.showShort(msg);  }
    public void Toast(int msg){  ToastUtils.showShort(msg);  }

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
