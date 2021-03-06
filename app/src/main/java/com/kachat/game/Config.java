package com.kachat.game;

import android.util.Log;

import com.kachat.game.utils.SharedPreferencesHelper;

import retrofit2.http.PUT;


public class Config {

    // TODO: 2018/5/29
    // debug版调试开关
    public static final boolean isDebug=true;

    private static String firstIn="firstIn";
    public static void setFirstIn(boolean value){ SharedPreferencesHelper.getInstance().setBooleanValue(firstIn,value); }
    public static boolean getFirstIn(){ return SharedPreferencesHelper.getInstance().getBooleanValueByKey(firstIn); }

    private static String mobile="MOBILE";
    public static void setMobile(String value){ SharedPreferencesHelper.getInstance().setStringValue(mobile,value); }
    public static String getMobile(){ return SharedPreferencesHelper.getInstance().getStringValueByKey(mobile); }

    private static String isFiguresMask="figuresMask";
    public static void setIsFiguresMask(boolean value){ SharedPreferencesHelper.getInstance().setBooleanValue(isFiguresMask,value); }
    public static boolean getIsFiguresMask(){ return SharedPreferencesHelper.getInstance().getBooleanValueByKey(isFiguresMask); }

}
