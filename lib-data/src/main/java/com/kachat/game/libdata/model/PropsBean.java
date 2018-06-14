package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.List;


/**
 -props: 用户道具数组
 - number: 拥有道具数量
 - prop: 道具详情
 - name: 道具名称
 - image_url: 道具图片地址
 - index: 道具编号
 - category: 道具类别，0-通用道具，1-游戏道具，2-聊天道具
 */
public class PropsBean extends BaseBeans {


    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        private List<ChildPropsBean> props;

        public List<ChildPropsBean> getProps() {
            return props;
        }

        public void setProps(List<ChildPropsBean> props) {
            this.props = props;
        }

        public static class ChildPropsBean implements Serializable{

            private PropBean prop;
            private int number;

            public PropBean getProp() {
                return prop;
            }

            public void setProp(PropBean prop) {
                this.prop = prop;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public static class PropBean implements Serializable{

                private String name;
                private String image_url;
                private int index;
                private int category;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getImage_url() {
                    return image_url;
                }

                public void setImage_url(String image_url) {
                    this.image_url = image_url;
                }

                public int getIndex() {
                    return index;
                }

                public void setIndex(int index) {
                    this.index = index;
                }

                public int getCategory() {
                    return category;
                }

                public void setCategory(int category) {
                    this.category = category;
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
