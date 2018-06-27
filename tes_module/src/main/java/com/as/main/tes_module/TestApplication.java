package com.as.main.tes_module;

import android.app.Application;

import com.kachat.game.libdata.HttpLocalDataHelper;

/**
 *
 */
public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpLocalDataHelper.init(this, "http://api.e3webrtc.com:8004","Test.db","123");
    }
}
