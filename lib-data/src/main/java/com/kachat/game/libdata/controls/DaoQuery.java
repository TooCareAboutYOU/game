package com.kachat.game.libdata.controls;

import android.text.TextUtils;
import android.util.Log;

import com.kachat.game.libdata.GreenDaoHelper;
import com.kachat.game.libdata.dbmodel.DbLive2DBean;
import com.kachat.game.libdata.dbmodel.DbLoginBean;
import com.kachat.game.libdata.dbmodel.DbUserBean;
import com.kachat.game.libdata.gen.DbLive2DBeanDao;
import com.kachat.game.libdata.gen.DbUserBeanDao;

import java.util.List;
import java.util.Objects;

/**
 * Created by admin on 2018/3/12.
 */

public class DaoQuery {

    private static final String TAG = "DaoQuery";

    public static DbUserBean queryUserData(String mobile) {
        synchronized (DaoQuery.class) {
            List<DbUserBean> userBeansList = GreenDaoHelper.getInstance().writeUser().queryBuilder().list();
            if (userBeansList != null && userBeansList.size() > 0) {
                DbUserBean userBeans = GreenDaoHelper.getInstance().writeUser().queryBuilder().where(DbUserBeanDao.Properties.Mobile.eq(mobile)).build().unique();
                if (userBeans != null) {
                    Log.i("UpdateUserData", "1 精确查询--->>>>>>" + userBeans.toString());
                    return userBeans;
                }
                return userBeansList.get(0);
            }
            return null;
        }
    }

    public static DbUserBean queryUserData() {
        synchronized (DaoQuery.class) {
            List<DbUserBean> userBeans = GreenDaoHelper.getInstance().writeUser().queryBuilder().list();
            if (userBeans != null && userBeans.size() > 0) {
                return userBeans.get(0);
            }
            return null;
        }
    }

    public static int queryUserListSize() {
        synchronized (DaoQuery.class) {
            return GreenDaoHelper.getInstance().writeUser().queryBuilder().list().size();
        }
    }


    /************************************Live2d 模型******************************************/

    public static List<DbLive2DBean> queryModelListData(){
        synchronized (DaoQuery.class) {
            List<DbLive2DBean> dbLive2DBeanList = GreenDaoHelper.getInstance().writeLive2DModel().queryBuilder().list();
            if (dbLive2DBeanList != null && dbLive2DBeanList.size() > 0) {
               return dbLive2DBeanList;
            }
            return null;
        }
    }

    public static long queryGetModelId(String fileName){
        synchronized (DaoQuery.class) {
            if (TextUtils.isEmpty(fileName)) {
                throw new NullPointerException("the fileName is null");
            }
            DbLive2DBean bean = GreenDaoHelper.getInstance().writeLive2DModel().queryBuilder()
                    .where(DbLive2DBeanDao.Properties.LiveFileName.eq(fileName)).build().unique();
            if (bean != null) {
                return bean.getId();
            }
            return -1;
        }
    }

    public static int queryListModelListSize() {
        synchronized (DaoQuery.class) {
            return GreenDaoHelper.getInstance().writeLive2DModel().queryBuilder().list().size();
        }
    }
}
