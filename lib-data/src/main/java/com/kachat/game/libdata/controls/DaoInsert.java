package com.kachat.game.libdata.controls;


import com.kachat.game.libdata.GreenDaoHelper;
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
            if (userBean != null && GreenDaoHelper.getInstance().readUser() != null) {
                GreenDaoHelper.getInstance().readUser().insert(userBean);
            } else {
                throw new NullPointerException("the userBean or GreenDaoHelper.readUser() is null!");
            }
        }
    }

//    public static void insterLogin(boolean state){
//        synchronized (DaoInsert.class){
//            DbLoginBean bean=new DbLoginBean(null,state);
//            GreenDaoHelper.getInstance().readLogin().insert(bean);
//        }
//    }
}
