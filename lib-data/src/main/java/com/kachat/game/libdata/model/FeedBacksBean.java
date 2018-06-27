package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 *
 */
public class FeedBacksBean implements Serializable {

        private int feedback_id;
        private String create_time;
        private String content;
        private int category;
        public int getFeedback_id() {
            return feedback_id;
        }

        public void setFeedback_id(int feedback_id) {
            this.feedback_id = feedback_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
