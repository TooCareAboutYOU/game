package com.kachat.game.application;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.NetworkUtils;
import com.kachat.game.events.services.NetConnectService;

/**
 *
 */
public class KaChatApplication extends MultiDexApplication {

    private static KaChatApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        ApplicationHelper.init(this);
        startService(new Intent(getApplicationContext(),NetConnectService.class));
    }

    public static synchronized KaChatApplication getInstance(){ return instance; }

}
