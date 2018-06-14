package com.kachat.game.application;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.kachat.game.events.services.NetConnectService;

/**
 *
 */
public class KaChatApplication extends MultiDexApplication {

    private static KaChatApplication instance;

    public static Intent intent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        ApplicationHelper.init(this);
//        start();
//        initLeakCanary();
    }

    public static synchronized KaChatApplication getInstance(){ return instance; }

    private void start(){
        startService(new Intent(this,NetConnectService.class));
    }

    public void stop(){
        stopService(intent);
    }


//    private RefWatcher mRefWatcher;
//    public static RefWatcher getRefWatcher(Context context){
//        KaChatApplication application= (KaChatApplication) context.getApplicationContext();
//        return application.mRefWatcher;
//    }
//
//    private void initLeakCanary(){
//        mRefWatcher=LeakCanary.install(instance);
//
//    }

}
