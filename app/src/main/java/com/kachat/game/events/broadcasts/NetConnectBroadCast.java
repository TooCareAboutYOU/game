package com.kachat.game.events.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.Objects;

public class NetConnectBroadCast extends BroadcastReceiver {

    private static final String TAG = "initNetWork";

    private int flag=0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "NetConnectBroadCast onReceive: ");
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= Objects.requireNonNull(manager).getActiveNetworkInfo();
        if (networkInfo == null) {
            ToastUtils.showLong("当前网络不可用");
            flag=1;
        }else {
            if (!networkInfo.isAvailable() || networkInfo.isFailover()) {
                ToastUtils.showShort("当前网络不可用");
            }

            if (flag ==1) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    Log.i(TAG, "移动网络已连接: ");
                }
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    Log.i(TAG, "WIFI网络已连接: ");
                }
            }
        }
    }

}
