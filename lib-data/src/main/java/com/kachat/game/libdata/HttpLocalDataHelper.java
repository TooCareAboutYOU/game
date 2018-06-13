package com.kachat.game.libdata;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kachat.game.libdata.http.HttpManager;

import okhttp3.Route;

/**
 *
 */
public class HttpLocalDataHelper {

    public static void init(@NonNull Context context, @NonNull String url, String dbName, String dbPassword){
        HttpManager.init(context,url);
        initDataBase(context, dbName,dbPassword);
    }

    private static void initDataBase(@NonNull Context context, String dbName, String dbPassword){
        GreenDaoHelper.init(context,dbName,dbPassword);
    }


}
