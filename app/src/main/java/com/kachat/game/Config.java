package com.kachat.game;

import android.util.Log;

import com.kachat.game.utils.SharedPreferencesHelper;

import retrofit2.http.PUT;


public class Config {

    // TODO: 2018/5/29
    // debug版调试开关
    public static final boolean isDebug=true;


    private static String mobile="MOBILE";
    public static void setMobile(String value){ SharedPreferencesHelper.getInstance().setStringValue(mobile,value); }
    public static String getMobile(){ return SharedPreferencesHelper.getInstance().getStringValueByKey(mobile); }

    private static String isFirstStartGame="startGame";
    public static void setFirst(int value){ SharedPreferencesHelper.getInstance().setIntValue(isFirstStartGame,value); }
    public static int getFirst(){ return SharedPreferencesHelper.getInstance().getIntValueByKey(isFirstStartGame); }





}
