package com.kachat.game.ui;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.kachat.game.R;
import com.kachat.game.application.KaChatApplication;
import com.kachat.game.base.BaseActivity;
import com.kachat.game.ui.game.GameActivity;
import com.kachat.game.ui.user.PersonInfoActivity;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected int onSetResourceLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView() {

    }

    @Override
    protected void onInitData(@Nullable Bundle savedInstanceState) {
        findViewById(R.id.sample_text).setOnClickListener(v -> startActivity(new Intent(this, GameActivity.class)));
        getBack().setOnClickListener(v -> startActivity(new Intent(this, GameActivity.class)));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        KaChatApplication.getInstance().stop();
    }

    //记录用户首次点击返回键的时间
    private long firstTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
