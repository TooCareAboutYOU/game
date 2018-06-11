package com.kachat.game.libdata.controls;

import android.util.Log;

import com.kachat.game.libdata.GreenDaoHelper;
import com.kachat.game.libdata.dbmodel.DbLoginBean;
import com.kachat.game.libdata.dbmodel.DbUserBean;
import com.kachat.game.libdata.gen.DbUserBeanDao;

import java.util.List;

/**
 * Created by admin on 2018/3/12.
 */

public class DaoQuery {

    private static final String TAG = "DaoQuery";

    //登录状态
    public static boolean queryLoginState() {
        synchronized (DaoQuery.class) {
            List<DbLoginBean> userBeans = GreenDaoHelper.getInstance().writeLogin().queryBuilder().list();
            if (userBeans != null && userBeans.size() == 1) {
                return userBeans.get(0).getIsLogin();
            }
            return false;
        }
    }

    public static int queryLoginListSize() {
        synchronized (DaoQuery.class) {
            return GreenDaoHelper.getInstance().writeLogin().queryBuilder().list().size();
        }
    }


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

    public static int queryUserlistSize() {
        synchronized (DaoQuery.class) {
            return GreenDaoHelper.getInstance().writeUser().queryBuilder().list().size();
        }
    }

    //    /**
//     * @param uid
//     * @return
//     */
//    public static List<DbUserBean> queryUserListByUid(int uid){
//        synchronized (DaoQuery.class) {
//            List<UserBean> userBeans = GreenDaoHelper.getInstance().writeUser().queryBuilder().where(UserBeanDao.Properties.Uid.lt(uid)).list();
//            Log.i(TAG, "条数: " + userBeans.size());
//            if (userBeans != null && userBeans.size() > 0) {
//                for (UserBean bean : userBeans) {
//                    Log.i(TAG, "数据: " + bean.toString());
//                }
//                return userBeans;
//            }
//            return null;
//        }
//    }
//
//
//    public static List<UserBean> queryUserlist(){
//        synchronized (DaoQuery.class) {
//            List<UserBean> userBeans = GreenDaoHelper.getInstance().writeUser().queryBuilder().list();
//            Log.i(TAG, "条数: " + userBeans.size());
//            if (userBeans != null && userBeans.size() > 0) {
//                for (UserBean bean : userBeans) {
//                    Log.i(TAG, "数据: " + bean.toString());
//                }
//                return userBeans;
//            }
//            return null;
//        }
//    }
}
