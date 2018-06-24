package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 *
 */
public class GamesBean extends BaseBeans{

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
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

            private String name;
            private int price;
            private int category;
            private int index;
            private int image; //图片
            private int imgStart; //开始游戏
            private int imgHint;  //说明
            private int imgTimeLimit;  //显示

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
