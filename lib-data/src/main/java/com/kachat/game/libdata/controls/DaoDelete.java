package com.kachat.game.libdata.controls;

import android.text.TextUtils;
import android.util.Log;

import com.kachat.game.libdata.GreenDaoHelper;
import com.kachat.game.libdata.dbmodel.DbLive2DBean;

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
            int sizeUser = DaoQuery.queryUserListSize();
            if (sizeUser > 0) {
                Log.i(TAG, "delete User: 不为清空");
                GreenDaoHelper.getInstance().writeUser().deleteAll();
            }
            Log.i(TAG, "User 数据表已经为空");
            return true;
        }
    }

    public static boolean deleteLiveModelAll(){
        synchronized (DaoDelete.class) {
            int sizeLiveModel = DaoQuery.queryListModelListSize();

            if (sizeLiveModel > 0) {
                Log.i(TAG, "delete LiveModel: 不为清空");
                GreenDaoHelper.getInstance().writeLive2DModel().deleteAll();
            }

            Log.i(TAG, "LiveModel 数据库已经为空");
            return true;
        }
    }

    public static boolean deleteModelByKey(long key){
        synchronized (DaoDelete.class) {
            GreenDaoHelper.getInstance().writeLive2DModel().deleteByKey(key);
            return true;
        }
    }

}
