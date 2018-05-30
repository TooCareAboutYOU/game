package com.kachat.game.events.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.blankj.utilcode.util.NetworkUtils;
import com.kachat.game.Config;
import com.kachat.game.Constant;

import java.util.Objects;

public class NetConnectService extends Service {

    private NetWorkBroadCastReceiver mCastReceiver;

    public NetConnectService() { }

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
        mCastReceiver=new NetWorkBroadCastReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(NetWorkBroadCastReceiver.CONNECTIVITY_ACTION);
        registerReceiver(mCastReceiver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mCastReceiver);
    }


    public class NetWorkBroadCastReceiver extends BroadcastReceiver {

        public static final String CONNECTIVITY_ACTION=ConnectivityManager.CONNECTIVITY_ACTION;

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (Objects.requireNonNull(intent.getAction())) {
                case ConnectivityManager.CONNECTIVITY_ACTION:
                        boolean newWorkState= NetworkUtils.isConnected();
                        if (newWorkState) {
                            Toast.makeText(context, "网络连接", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "网络断开", Toast.LENGTH_SHORT).show();
                        }
                    break;
            }

        }
    }



}
