package com.kachat.game.events;

import android.content.Context;

import com.kachat.game.Config;
import com.kachat.game.SdkApi;
import com.kachat.game.libdata.controls.DaoDelete;
import com.kachat.game.libdata.model.CategoryListBean;
import com.kachat.game.ui.user.MeActivity;
import com.kachat.game.ui.user.login.LoginActivity;
import com.kachat.game.utils.manager.ActivityManager;
import com.kachat.game.utils.widgets.AlterDialogBuilder;
import com.kachat.game.utils.widgets.DialogTextView;

import java.util.List;

/**
 *
 */
public class PublicEventMessage {

    public static void ExitAccount(Context context){

            if (DaoDelete.deleteUserAll()) {
//                    CleanUtils.cleanInternalDbByName(ApplicationHelper.DB_NAME);
                ActivityManager.getInstance().removeActivity("MainActivity");
                DaoDelete.deleteUserAll();
                if (Config.getIsFiguresMask()) {
                    DaoDelete.deleteLiveModelAll();
                }
                SdkApi.getInstance().sdkExit();
                LoginActivity.newInstance(context);
            }
    }

    public static class TransmitMobile{
        private String mobile;

        public TransmitMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMobile() {
            return mobile;
        }
    }

    public static class ShopBuy{
        private CategoryListBean.ResultBean.GoodsBean mGoodsBean;

        public ShopBuy(CategoryListBean.ResultBean.GoodsBean goodsBean) {
            mGoodsBean = goodsBean;
        }

        public CategoryListBean.ResultBean.GoodsBean getGoodsBean() {
            return mGoodsBean;
        }

        public void setGoodsBean(CategoryListBean.ResultBean.GoodsBean goodsBean) {
            mGoodsBean = goodsBean;
        }
    }

    public static class OnGraduateEvent{
        private int type; // 0：人物遮罩 ,1：饰品, 2：变身, 3：背景
        private String msg;

        public OnGraduateEvent(int type, String msg) {
            this.type = type;
            this.msg = msg;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
