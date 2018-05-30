package com.kachat.game;

import android.support.annotation.Nullable;
import com.dnion.VAChatAPI;
import com.kachat.game.utils.SharedPreferencesHelper;


public class Config {

    // TODO: 2018/5/29
    // debug版调试开关
    public static final boolean isDebug=true;

    //SDK
    private static String ConnectStatus="ConnectStatus";
    public static void setSdkConnectStatus(boolean isConnected){  SharedPreferencesHelper.getInstance().setBooleanValue(ConnectStatus,isConnected); }
    public static boolean getSdkConnectStatus=SharedPreferencesHelper.getInstance().getBooleanValueByKey(ConnectStatus);

    public static void SdkConnect(@Nullable String uid,@Nullable String token){
        if (!getSdkConnectStatus) {
            // TODO: 2018/5/30 SDK连接
            VAChatAPI.getInstance().connect(Constant.CHAT_SDK_URL,uid,token);
        }
    }

    public static void SdkDisConnect(){
        if (getSdkConnectStatus) {
            // TODO: 2018/5/30 SDK断开
            VAChatAPI.getInstance().disconnect();
        }
    }


}
