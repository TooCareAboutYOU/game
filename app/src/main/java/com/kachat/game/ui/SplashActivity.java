package com.kachat.game.ui;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.util.EventLog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.PermissionUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kachat.game.R;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.libdata.controls.DaoQuery;
import com.kachat.game.ui.user.login.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

//        WindowManager.LayoutParams params=getWindow().getAttributes();
//        params.systemUiVisibility=View.SYSTEM_UI_FLAG_LOW_PROFILE
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION ;
//        getWindow().setAttributes(params);
public class SplashActivity extends BaseActivity implements PermissionUtils.OnPermissionListener {

    private static final String TAG = "SplashActivity";

    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA

    };
    private int PERMISSION_REQUEST_CODE = 1;

    private static final int UI_START_DELAY = 100;
    private static final int UI_ANIMATION_DELAY = 100;
    private static final int UI_TO_MAIN_ACTIVITY = 500;
    private final Handler mHideHandler = new Handler();

    @BindView(R.id.img_bg)
    SimpleDraweeView imgBg;

    private final Runnable mHidePart2Runnable = () -> mHideHandler.postDelayed(() -> {
        Log.e(TAG, "mHidePart2Runnable：");
        PermissionUtils.requestPermissions(SplashActivity.this, PERMISSION_REQUEST_CODE, permissions, SplashActivity.this);
    }, UI_TO_MAIN_ACTIVITY);

    private final Runnable mHideRunnable = () -> {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    };

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        BarUtils.isNavBarVisible(this);
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, UI_START_DELAY);
    }

    @Override
    protected int onSetResourceLayout() { return R.layout.activity_splash; }

    @Override
    protected boolean onSetStatusBar() {
        return true;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (getImmersionBar() != null) {
            getImmersionBar().transparentBar().init();
        }
    }


    @Override
    protected void onInitView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void RequestNetWork() {
//        imgBg.setVisibility(View.VISIBLE);
//        Uri uri = Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527142320408&di=0e0d05696c62396158e3b0c2f5a37fa3&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171108%2Fa8edcc511a1e4471a5dc2b0d73c48479.jpeg");
//        imgBg.setImageURI(uri);
        Intent intent=null;
        if (DaoQuery.queryUserListSize() == 1) {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        }else {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPermissionGranted() {  //用户同意时调用
        Log.e(TAG, "onPermissionGranted: ");
        RequestNetWork();
//        UpLoadBugLogService.startActionBaz(this,"1","2");
//        int i=0;
//        Log.i(TAG, "onPermissionGranted: "+(i/0));
    }

    @Override
    public void onPermissionDenied(String[] deniedPermissions) { // 当用户拒绝时调用
        PermissionUtils.requestPermissions(SplashActivity.this, PERMISSION_REQUEST_CODE, deniedPermissions, SplashActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionsResult: ");
        PermissionUtils.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


}
