package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class ScenesBean extends BaseBeans {

    private ResultBean result;

    public ResultBean getResult() { return result; }

    public void setResult(ResultBean result) { this.result = result; }

    public static class ResultBean implements Serializable{
        private List<LivesBean> lives;

        public List<LivesBean> getLives() { return lives; }
        public void setLives(List<LivesBean> lives) { this.lives = lives; }

        public static class LivesBean implements Serializable{

            private LiveBean live;

            public LiveBean getLive() { return live; }
            public void setLive(LiveBean live) { this.live = live; }

            public static class LiveBean {

                private String name;
                private int index;

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
        public String toString() { return JSON.toJSONString(this); }

    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
