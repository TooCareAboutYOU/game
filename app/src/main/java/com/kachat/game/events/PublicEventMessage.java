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

}
