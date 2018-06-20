package com.kachat.game.libdata.controls;

import com.kachat.game.libdata.GreenDaoHelper;
import com.kachat.game.libdata.dbmodel.DbUserBean;


public class DaoUpdate {

    private static final String TAG = "UpdateUserData";


    public static boolean updateUser(String token, String username, String gender, int uid, int age,
                                     int system, int level, int hp, int exp_to_level_up, int exp,
                                     String number, int diamond, int charm,int gold){
        synchronized (DaoUpdate.class) {
            DbUserBean userBean = DaoQuery.queryUserData();
            if (userBean != null) {
                userBean.setToken(token);
                userBean.setUsername(username);
                userBean.setGender(gender);
                userBean.setUid(uid);
                userBean.setAge(age);
                userBean.setSystem(system);
                userBean.setLevel(level);
                userBean.setHp(hp);
                userBean.setExp_to_level_up(exp_to_level_up);
                userBean.setExp(exp);
                userBean.setNumber(number);
                userBean.setDiamond(diamond);
                userBean.setCharm(charm);
                userBean.setGold(gold);
                GreenDaoHelper.getInstance().writeUser().update(userBean);
                return true;
            }
            return false;
        }
    }

    public static boolean updateUser(String username, String gender, int uid, int age,
                                     int level, int hp, int exp_to_level_up, int exp,
                                     String number, int diamond, int charm,int gold){
        synchronized (DaoUpdate.class) {
            DbUserBean userBean = DaoQuery.queryUserData();
            if (userBean != null) {
                userBean.setUsername(username);
                userBean.setGender(gender);
                userBean.setUid(uid);
                userBean.setAge(age);
                userBean.setLevel(level);
                userBean.setHp(hp);
                userBean.setExp_to_level_up(exp_to_level_up);
                userBean.setExp(exp);
                userBean.setNumber(number);
                userBean.setDiamond(diamond);
                userBean.setCharm(charm);
                userBean.setGold(gold);
                GreenDaoHelper.getInstance().writeUser().update(userBean);
                return true;
            }
            return false;
        }
    }

}
