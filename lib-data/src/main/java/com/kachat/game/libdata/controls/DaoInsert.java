package com.kachat.game.libdata.controls;


import android.text.TextUtils;

import com.kachat.game.libdata.GreenDaoHelper;
import com.kachat.game.libdata.dbmodel.DbLive2DBean;
import com.kachat.game.libdata.dbmodel.DbLoginBean;
import com.kachat.game.libdata.dbmodel.DbUserBean;

/**
 * Created by admin on 2018/3/12.
 */

public class DaoInsert {

    private static final String TAG = "DaoInsert";

    /**
     * 用例demo
     * @param userBean
     */
    public static void insertUser(DbUserBean userBean){
        synchronized (DaoInsert.class) {
            if (userBean == null && GreenDaoHelper.getInstance().readUser() == null) {
                throw new NullPointerException("the userBean or GreenDaoHelper.readUser() is null!");
            }
            GreenDaoHelper.getInstance().readUser().insert(userBean);
        }
    }

    public static void insertLive2DModel(DbLive2DBean bean){
        synchronized (DaoInsert.class) {
            if (GreenDaoHelper.getInstance().readLive2DModel() == null || bean == null) {
                throw new NullPointerException("the liveModelBean or readLive2DModel() is null!");
            }
            String fileName=bean.getLiveFileName();
            if (TextUtils.isEmpty(fileName)) {
                throw new NullPointerException("fileName is null!");
            }

            GreenDaoHelper.getInstance().readLive2DModel().insert(bean);
        }
    }
}
