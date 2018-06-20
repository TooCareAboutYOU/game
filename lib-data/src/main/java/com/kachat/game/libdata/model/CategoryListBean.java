package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 *
 */
public class CategoryListBean extends BaseBeans {

    /**
     * result : {"goods":[{"good_id":1,"category":{"name":"金币","good_category_id":1,"index":1,"desc":"金币"},"create_time":"2018-06-07 12:43","name":"100金币","index":4,"status":0,"image_url":"http://api.e3webrtc.com:8004/media/goods/0003_jinbi.png","price":10,"currency":0,"amount":100,"limit":0,"desc":"100金币"},{"good_id":2,"category":{"name":"金币","good_category_id":1,"index":1,"desc":"金币"},"create_time":"2018-06-07 14:37","name":"520金币","index":5,"status":0,"image_url":"http://api.e3webrtc.com:8004/media/goods/0003_jinbi_oVOABau.png","price":50,"currency":0,"amount":520,"limit":0,"desc":"520金币"},{"good_id":3,"category":{"name":"金币","good_category_id":1,"index":1,"desc":"金币"},"create_time":"2018-06-07 14:39","name":"1100金币","index":6,"status":0,"image_url":"http://api.e3webrtc.com:8004/media/goods/0003_jinbi_oKh1Zon.png","price":100,"currency":0,"amount":1100,"limit":0,"desc":"1100金币"},{"good_id":4,"category":{"name":"金币","good_category_id":1,"index":1,"desc":"金币"},"create_time":"2018-06-07 14:40","name":"5750金币","index":7,"status":0,"image_url":"http://api.e3webrtc.com:8004/media/goods/0003_jinbi_vcautcE.png","price":500,"currency":0,"amount":5750,"limit":0,"desc":"5750金币"},{"good_id":5,"category":{"name":"金币","good_category_id":1,"index":1,"desc":"金币"},"create_time":"2018-06-07 14:40","name":"7200金币","index":8,"status":0,"image_url":"http://api.e3webrtc.com:8004/media/goods/0003_jinbi_BAT7vU7.png","price":666,"currency":0,"amount":7200,"limit":0,"desc":"7200金币"},{"good_id":6,"category":{"name":"金币","good_category_id":1,"index":1,"desc":"金币"},"create_time":"2018-06-07 14:41","name":"12000金币","index":9,"status":0,"image_url":"http://api.e3webrtc.com:8004/media/goods/0003_jinbi_ai4noPu.png","price":1000,"currency":0,"amount":12000,"limit":0,"desc":"12000金币"}]}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<GoodsBean> goods;

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * good_id : 1
             * category : {"name":"金币","good_category_id":1,"index":1,"desc":"金币"}
             * create_time : 2018-06-07 12:43
             * name : 100金币
             * index : 4
             * status : 0
             * image_url : http://api.e3webrtc.com:8004/media/goods/0003_jinbi.png
             * price : 10
             * currency : 0
             * amount : 100
             * limit : 0
             * desc : 100金币
             */

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
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
