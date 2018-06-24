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
import com.kachat.game.application.KaChatApplication;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;

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
                            Toast.makeText(context, "连接", Toast.LENGTH_SHORT).show();
                        }else {
//                            new AlterDialogBuilder(KaChatApplication.getInstance(),new DialogTextView(KaChatApplication.getInstance(),"网络已断开"));
                            Toast.makeText(context, "数据连接已断开", Toast.LENGTH_SHORT).show();
                        }
                    break;
            }
        }
    }
}
