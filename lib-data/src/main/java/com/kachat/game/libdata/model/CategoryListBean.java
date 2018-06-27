package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class CategoryListBean implements Serializable{

    private List<GoodsBean> goods;

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        private int good_id;
        private CategoryBean category;
        private String create_time;
        private String name;
        private int index;
        private int status;
        private String image_url;
        private int price;
        private int currency;
        private int amount;
        private int limit;
        private String desc;

        public int getGood_id() {
            return good_id;
        }

        public void setGood_id(int good_id) {
            this.good_id = good_id;
        }

        public CategoryBean getCategory() {
            return category;
        }

        public void setCategory(CategoryBean category) {
            this.category = category;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getCurrency() {
            return currency;
        }

        public void setCurrency(int currency) {
            this.currency = currency;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static class CategoryBean {
            /**
             * name : 金币
             * good_category_id : 1
             * index : 1
             * desc : 金币
             */

            private String name;
            private int good_category_id;
            private int index;
            private String desc;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getGood_category_id() {
                return good_category_id;
            }

            public void setGood_category_id(int good_category_id) {
                this.good_category_id = good_category_id;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
            @Override
            public String toString() {
                return JSON.toJSONString(this);
            }
        }
        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
