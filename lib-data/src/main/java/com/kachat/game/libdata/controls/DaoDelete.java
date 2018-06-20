package com.kachat.game.libdata.controls;

import android.util.Log;

import com.kachat.game.libdata.GreenDaoHelper;

/**
 * Created by admin on 2018/3/13.
 */

public class DaoDelete {

    private static final String TAG = "DaoDelete";

//    public static boolean deleteUserByUid(int uid){
//        synchronized (DaoDelete.class) {
//            int beforeSize = DaoQuery.queryUserlistSize();
//            if (beforeSize <= 0) {
//                //不存在用户
//                return false;
//            }
//            Log.i(TAG, "DaoQuery.queryUserlist() before:" + DaoQuery.queryUserlist());
//            Long id = DaoQuery.queryUserbeanByUid(uid).getId();
//            if (id != null) {
//                Log.i(TAG, "deleteUser: 删除前");
//                GreenDaoHelper.getInstance().writeUser().deleteByKey(id);
//                Log.i(TAG, "deleteUser: 删除后");
//            } else {
//                Log.i(TAG, "deleteUser: 数据不存在");
//                return false;
//            }
//            int afterSize = DaoQuery.queryUserlistSize();
//            Log.i(TAG, "DaoQuery.queryUserlist() after:" + DaoQuery.queryUserlist());
//            if (beforeSize - afterSize == 1) {
//                Log.i(TAG, "deleteUser: 返回true ");
//                return true;
//            }
//            return false;
//        }
//    }

    public static boolean deleteUserAll(){
        synchronized (DaoDelete.class) {
            int sizebefore = DaoQuery.queryUserListSize();
            if (sizebefore > 0) {
                Log.i(TAG, "deleteUserAll: 清空");
                GreenDaoHelper.getInstance().writeUser().deleteAll();
            } else {
                Log.i(TAG, "deleteUserAll: 已经为空");
                return true;
            }
            int sizeAfter = DaoQuery.queryUserListSize();
            if (sizeAfter == 0) {
                Log.i(TAG, "deleteUserAll: 清空成功");
                return true;
            }
            return false;
        }
    }

}
