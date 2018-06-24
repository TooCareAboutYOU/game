package com.kachat.game.events;


import com.alibaba.fastjson.JSON;
import com.dnion.VAGameDelegate;

/**
 *
 */
public class DNGameEventMessage {

    public enum DNGameEvent {
        SESSION_READY,
        SESSION_BROKEN,
        SESSION_OCCUPY,
        SESSION_KEEP_ALIVE,
        JOIN_SUCCESS,
        JOIN_FAILED,
        MATCH_SUCCESS,
        GAME_MESSAGE,
        GAME_STAT,
        VIDEO_CHAT_START,
        VIDEO_CHAT_FINISH,
        VIDEO_CHAT_TERMINATE,
        VIDEO_CHAT_FAIL,
        GOT_GIFT,
        ERROR_MESSAGE
    }

    private String mString;
    private VAGameDelegate.VAGameStat vaGameStat;
    private long mLong;
    private int mInt;
    private DNGameEvent mEvent;

    public DNGameEventMessage(DNGameEvent event) {
        mEvent = event;
    }

    public DNGameEventMessage(String string, DNGameEvent event) {
        mString = string;
        mEvent = event;
    }

    public DNGameEventMessage(long aLong, DNGameEvent event) {
        mLong = aLong;
        mEvent = event;
    }

    public DNGameEventMessage(int anInt, DNGameEvent event) {
        mInt = anInt;
        mEvent = event;
    }

    public DNGameEventMessage(String string, VAGameDelegate.VAGameStat vaGameStat, DNGameEvent event) {
        mString = string;
        this.vaGameStat = vaGameStat;
        mEvent = event;
    }


    public String getString() {
        return mString;
    }

    public void setString(String string) {
        mString = string;
    }

    public VAGameDelegate.VAGameStat getVaGameStat() {
        return vaGameStat;
    }

    public void setVaGameStat(VAGameDelegate.VAGameStat vaGameStat) {
        this.vaGameStat = vaGameStat;
    }

    public long getLong() {
        return mLong;
    }

    public void setLong(long aLong) {
        mLong = aLong;
    }

    public int getInt() {
        return mInt;
    }

    public void setInt(int anInt) {
        mInt = anInt;
    }

    public DNGameEvent getEvent() {
        return mEvent;
    }

    public void setEvent(DNGameEvent event) {
        mEvent = event;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public static class OnGameMessageBean{

        /**
         * type : leave
         * data : {}
         * game : hexTris
         */

        private String type;
        private DataBean data;
        private String game;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getGame() {
            return game;
        }

        public void setGame(String game) {
            this.game = game;
        }

        public static class DataBean {
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }
}
