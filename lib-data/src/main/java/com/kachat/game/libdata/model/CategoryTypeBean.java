package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class CategoryTypeBean implements Serializable {

    private List<CategoriesBean> categories;

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public static class CategoriesBean implements Serializable{

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
