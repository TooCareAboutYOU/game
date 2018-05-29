package com.kachat.game.libdata;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kachat.game.libdata.gen.GreenDaoHelper;
import com.kachat.game.libdata.http.HttpManager;

/**
 *
 */
public class HttpLocalDataHelper {

    public static void init(@NonNull Context context, String url,String dbName,String dbPassword){
        HttpManager.init(context,url);
        initDataBase(context, dbName,dbPassword);
    }

    private static void initDataBase(Context context,String dbName,String dbPassword){
        GreenDaoHelper.init(context,dbName,dbPassword);
    }


}
