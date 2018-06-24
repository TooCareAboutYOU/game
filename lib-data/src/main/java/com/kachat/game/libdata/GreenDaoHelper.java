package com.kachat.game.libdata;

import android.content.Context;
import android.text.TextUtils;

import com.kachat.game.libdata.gen.DaoMaster;
import com.kachat.game.libdata.gen.DaoSession;
import com.kachat.game.libdata.gen.DbLive2DBeanDao;
import com.kachat.game.libdata.gen.DbLoginBeanDao;
import com.kachat.game.libdata.gen.DbUserBeanDao;

import org.greenrobot.greendao.database.Database;


public class GreenDaoHelper {

    private static final String TAG = "GreenDaoHelper";

    private static Context sContext;
    private static String dbName;
    private static String dbPassword;

    private static DaoMaster.DevOpenHelper sHelper;
//    private static SQLiteDatabase db;
    private static DaoMaster sDaoMaster;
    private static Database sDatabase;
    private static DaoSession sDaoSession;


    private volatile static GreenDaoHelper singleton;
    private GreenDaoHelper(){
        if (sHelper == null) {
            sHelper=new DaoMaster.DevOpenHelper(sContext,dbName,null);
        }
    }
    public static synchronized GreenDaoHelper getInstance() {
        if (singleton == null) {
             synchronized (GreenDaoHelper.class) {
                  if (singleton == null) {
                      singleton = new GreenDaoHelper();
                  }
             }
         }
        return singleton;
    }

    public static void init(Context context,String name,String pwd){
        sContext=context;
        dbName=name;
        dbPassword=pwd;
        if (!TextUtils.isEmpty(dbName)) {
            sHelper=new DaoMaster.DevOpenHelper(context,dbName,null);
        }
    }


    /****************************************** 用户 *******************************************************/

    public DbUserBeanDao writeUser(){
//        sDatabase=sHelper.getEncryptedWritableDb(dbPassword);
        sDatabase=sHelper.getWritableDb();
        sDaoMaster=new DaoMaster(sDatabase);
        sDaoSession=sDaoMaster.newSession();
        if (sDaoSession.getDbUserBeanDao() != null) {
            return sDaoSession.getDbUserBeanDao();
        }
        return null;
    }

    public DbUserBeanDao readUser(){
//        sDatabase=sHelper.getEncryptedReadableDb(dbPassword);
        sDatabase=sHelper.getReadableDb();
        sDaoMaster=new DaoMaster(sDatabase);
        sDaoSession=sDaoMaster.newSession();
        if (sDaoSession.getDbUserBeanDao() != null) {
            return sDaoSession.getDbUserBeanDao();
        }
        return null;
    }

    private static void close(){
        if (sDatabase != null) {
            sDatabase.close();
        }
    }

    /****************************************** 登录 *******************************************************/

    public DbLoginBeanDao writeLogin(){
        sDatabase=sHelper.getWritableDb();
        sDaoMaster=new DaoMaster(sDatabase);
        sDaoSession=sDaoMaster.newSession();
        if (sDaoSession.getDbUserBeanDao() != null) {
            return sDaoSession.getDbLoginBeanDao();
        }
        return null;
    }

    public DbLoginBeanDao readLogin(){
        sDatabase=sHelper.getReadableDb();
        sDaoMaster=new DaoMaster(sDatabase);
        sDaoSession=sDaoMaster.newSession();
        if (sDaoSession.getDbUserBeanDao() != null) {
            return sDaoSession.getDbLoginBeanDao();
        }
        return null;
    }


    /****************************************** 用户模型 *******************************************************/

    public DbLive2DBeanDao writeLive2DModel(){
        sDatabase=sHelper.getWritableDb();
        sDaoMaster=new DaoMaster(sDatabase);
        sDaoSession=sDaoMaster.newSession();
        if (sDaoSession.getDbLive2DBeanDao() != null) {
            return sDaoSession.getDbLive2DBeanDao();
        }
        return null;
    }

    public DbLive2DBeanDao readLive2DModel(){
        sDatabase=sHelper.getReadableDb();
        sDaoMaster=new DaoMaster(sDatabase);
        sDaoSession=sDaoMaster.newSession();
        if (sDaoSession.getDbLive2DBeanDao() != null) {
            return sDaoSession.getDbLive2DBeanDao();
        }
        return null;
    }

}
