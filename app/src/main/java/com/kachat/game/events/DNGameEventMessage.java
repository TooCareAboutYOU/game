package com.kachat.game.events;


import com.alibaba.fastjson.JSON;
import com.dnion.VAGameDelegate;

import java.util.List;

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



    //  SDK
    // {"Data": {"box": 1, "chips": [], "game": 900, "props": [{"number": 2, "prop": 3},{"number": 1, "prop": 301},
    // {"number": 1, "prop": 0},{"number": 1, "prop": 1}], "user": 236}, "Method": "GameReward", "MsgID": 10103}\

    public static class OnGameMessageBean{
        private String type;
        private DataBean data;
        private String game;

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }

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
            /**
             * score : 25
             * count : 1
             */
            private int score;
            private int count;

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }
        }
    }

    //SDK Box
    public static class OnBoxsMessageBean{

        private int user;  //用户ID
        private int game;  //   游戏序号
        private int box; // 宝箱种类 0-白色，1-蓝色，2-紫色，3-橙色，4-金色
        private List<ChipsBean> chips;  //碎片,为数组
        private List<PropsBean> props;  // 获得道具奖励，为数组

        public int getUser() {
            return user;
        }

        public void setUser(int user) {
            this.user = user;
        }

        public int getGame() {
            return game;
        }

        public void setGame(int game) {
            this.game = game;
        }

        public int getBox() {
            return box;
        }

        public void setBox(int box) {
            this.box = box;
        }

        public List<ChipsBean> getChips() {
            return chips;
        }

        public void setChips(List<ChipsBean> chips) {
            this.chips = chips;
        }

        public List<PropsBean> getProps() {
            return props;
        }

        public void setProps(List<PropsBean> props) {
            this.props = props;
        }

        public static class ChipsBean {
            private int chip;   // 碎片编号
            private int number; // 碎片数量

            public int getChip() {
                return chip;
            }

            public void setChip(int chip) {
                this.chip = chip;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }
        }

        public static class PropsBean {

            private int prop;   // 道具编号
            private int number; // 道具数量

            public int getProp() {
                return prop;
            }

            public void setProp(int prop) {
                this.prop = prop;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }
        }
    }

    public static class GameDataBean{

        /**
         * Data : {"box":1,"chips":[],"game":900,"props":[{"number":2,"prop":3},{"number":1,"prop":301},{"number":1,"prop":0},{"number":1,"prop":1}],"user":236}
         * Method : GameReward
         * MsgID : 10103
         */

        private DataBean Data;
        private String Method;
        private int MsgID;

        public DataBean getData() {
            return Data;
        }

        public void setData(DataBean Data) {
            this.Data = Data;
        }

        public String getMethod() {
            return Method;
        }

        public void setMethod(String Method) {
            this.Method = Method;
        }

        public int getMsgID() {
            return MsgID;
        }

        public void setMsgID(int MsgID) {
            this.MsgID = MsgID;
        }

        public static class DataBean {

            private int box;
            private int game;
            private int user;
            private List<ChipsBean> chips;
            private List<PropsBean> props;

            public int getBox() {
                return box;
            }

            public void setBox(int box) {
                this.box = box;
            }

            public int getGame() {
                return game;
            }

            public void setGame(int game) {
                this.game = game;
            }

            public int getUser() {
                return user;
            }

            public void setUser(int user) {
                this.user = user;
            }

            public List<ChipsBean> getChips() {
                return chips;
            }

            public void setChips(List<ChipsBean> chips) {
                this.chips = chips;
            }

            public List<PropsBean> getProps() {
                return props;
            }

            public void setProps(List<PropsBean> props) {
                this.props = props;
            }

            public static class PropsBean {
                /**
                 * number : 2
                 * prop : 3
                 */

                private int number;
                private int prop;

                public int getNumber() {
                    return number;
                }

                public void setNumber(int number) {
                    this.number = number;
                }

                public int getProp() {
                    return prop;
                }

                public void setProp(int prop) {
                    this.prop = prop;
                }

                @Override
                public String toString() {
                    return JSON.toJSONString(this);
                }
            }

            public static class ChipsBean{
                private int chip;
                private int number;

                public int getChip() {
                    return chip;
                }

                public void setChip(int chip) {
                    this.chip = chip;
                }

                public int getNumber() {
                    return number;
                }

                public void setNumber(int number) {
                    this.number = number;
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
    }

}
