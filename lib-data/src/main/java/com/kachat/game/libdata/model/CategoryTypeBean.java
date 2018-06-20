package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class CategoryTypeBean extends BaseBeans {


    /**
     * result : {"categories":[{"name":"金币","good_category_id":1,"index":1,"desc":"金币"},{"name":"消耗道具","good_category_id":2,"index":2,"desc":"消耗道具"},{"name":"人物遮罩碎片","good_category_id":3,"index":3,"desc":"人物遮罩碎片"},{"name":"饰品表情","good_category_id":5,"index":4,"desc":"饰品表情"}]}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        private List<CategoriesBean> categories;

        public List<CategoriesBean> getCategories() {
            return categories;
        }

        public void setCategories(List<CategoriesBean> categories) {
            this.categories = categories;
        }

        public static class CategoriesBean implements Serializable{
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
