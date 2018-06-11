package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 *
 */
public class ToyRoomsBean extends BaseBeans {

    private ResultBeanX result;

    public ResultBeanX getResult() {
        return result;
    }

    public void setResult(ResultBeanX result) {
        this.result = result;
    }

    public static class ResultBeanX {
        private List<RoomsBean> rooms;

        public List<RoomsBean> getRooms() {
            return rooms;
        }

        public void setRooms(List<RoomsBean> rooms) {
            this.rooms = rooms;
        }

        public static class RoomsBean {

            private int code;
            private ResultBean result;

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public ResultBean getResult() {
                return result;
            }

            public void setResult(ResultBean result) {
                this.result = result;
            }

            public static class ResultBean {

                private int room_id;
                private AwardBean award;
                private int status;
                private int index;
                private String name;
                private String image_url;
                private int price;

                public int getRoom_id() {
                    return room_id;
                }

                public void setRoom_id(int room_id) {
                    this.room_id = room_id;
                }

                public AwardBean getAward() {
                    return award;
                }

                public void setAward(AwardBean award) {
                    this.award = award;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getIndex() {
                    return index;
                }

                public void setIndex(int index) {
                    this.index = index;
                }

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

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public static class AwardBean {
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

            @Override
            public String toString() {
                return JSON.toJSONString(this);
            }
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
