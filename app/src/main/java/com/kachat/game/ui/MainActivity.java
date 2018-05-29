package com.kachat.game.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.util.Log;
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
        findViewById(R.id.sample_text).setOnClickListener(v -> startActivity(new Intent(this, PersonInfoActivity.class)));
        getBack().setOnClickListener(v -> startActivity(new Intent(this, GameActivity.class)));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        KaChatApplication.getInstance().stop();
    }
}
