package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class GamesBean implements Serializable {

        private List<GameBean> games;

        public List<GameBean> getGames() {
            return games;
        }

        public void setGames(List<GameBean> games) {
            this.games = games;
        }

        public static class GameBean {
            /**
             * name : 六芒星
             * price : 2
             * category : 0
             * index : 902
             */

            private String name; //游戏名称
            private int price;
            private int category;
            private int index;  //游戏序列号
            private int image; //图片
            private int imgStart; //开始游戏
            private int imgHint;  //说明
            private int imgTimeLimit;  //显示
            private String url;  //h5游戏地址
            private int status; //是否 0 开启 ,1 未开启
            private int sdk_index;


            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getCategory() {
                return category;
            }

            public void setCategory(int category) {
                this.category = category;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public int getImage() {
                return image;
            }

            public void setImage(int image) {
                this.image = image;
            }

            public int getImgStart() {
                return imgStart;
            }

            public void setImgStart(int imgStart) {
                this.imgStart = imgStart;
            }

            public int getImgHint() {
                return imgHint;
            }

            public void setImgHint(int imgHint) {
                this.imgHint = imgHint;
            }

            public int getImgTimeLimit() {
                return imgTimeLimit;
            }

            public void setImgTimeLimit(int imgTimeLimit) {
                this.imgTimeLimit = imgTimeLimit;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getSdk_index() {
                return sdk_index;
            }

            public void setSdk_index(int sdk_index) {
                this.sdk_index = sdk_index;
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
