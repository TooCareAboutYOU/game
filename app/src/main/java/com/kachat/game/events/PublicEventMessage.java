package com.kachat.game.events;

import com.kachat.game.libdata.model.CategoryListBean;

import java.util.List;

/**
 *
 */
public class PublicEventMessage {

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
