package com.kachat.game.libdata.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 user_live_id: 用户遮罩ID
 live_number: 拥有该遮罩数量，0-表示未拥有
 live_chip_number: 拥有该遮罩碎片个数
 live: 单个遮罩详情
     index: 遮罩编号
     name: 遮罩名称
     image_url: 图片地址
     unlock_chip: 解锁遮罩需要的碎片数量
     costumes: 该遮罩所有装饰数组
     costume_number: 拥有该装饰数量，0-表示未拥有
         costume_chip_number: 拥有该装饰碎片数量
         costume: 装饰详情
         name: 装饰名称
         index: 装饰编号
         unlock_chip: 解锁该装饰需要的碎片数量
         image_url: 装饰图片地址
 */
public class LivesBean implements Serializable {

        private List<ChildLivesBean> lives;

        public List<ChildLivesBean> getLives() {
            return lives;
        }

        public void setLives(List<ChildLivesBean> lives) {
            this.lives = lives;
        }

        public static class ChildLivesBean implements Serializable{
            /**
             * user_live_id : 381
             * live : {"name":"tiYaNa","index":704,"image_url":"http://api.e3webrtc.com:8004/media/lives/0704_tiyana_ih9wuib.png","unlock_chip":120,"costumes":[{"costume":{"name":"缇亚娜的项链","index":1104,"image_url":"http://api.e3webrtc.com:8004/media/costumes/1104_tiyana_xiangliang.png","unlock_chip":120},"costume_chip_number":0,"costume_number":2},{"costume":{"name":"缇亚娜的枪","index":1105,"image_url":"http://api.e3webrtc.com:8004/media/costumes/1105_tiyana_qiang.png","unlock_chip":120},"costume_chip_number":0,"costume_number":2}]}
             * live_chip_number : 9
             * live_number : 1
             */

            private int user_live_id;
            private LiveBean live;
            private int live_chip_number;
            private int live_number;
            private boolean isFlag;

            public int getUser_live_id() {
                return user_live_id;
            }

            public void setUser_live_id(int user_live_id) {
                this.user_live_id = user_live_id;
            }

            public LiveBean getLive() {
                return live;
            }

            public void setLive(LiveBean live) {
                this.live = live;
            }

            public int getLive_chip_number() {
                return live_chip_number;
            }

            public void setLive_chip_number(int live_chip_number) {
                this.live_chip_number = live_chip_number;
            }

            public int getLive_number() {
                return live_number;
            }

            public void setLive_number(int live_number) {
                this.live_number = live_number;
            }

            public boolean isFlag() {
                return isFlag;
            }

            public void setFlag(boolean flag) {
                isFlag = flag;
            }

            public static class LiveBean implements Serializable{
                /**
                 * name : tiYaNa
                 * index : 704
                 * image_url : http://api.e3webrtc.com:8004/media/lives/0704_tiyana_ih9wuib.png
                 * unlock_chip : 120
                 * costumes : [{"costume":{"name":"缇亚娜的项链","index":1104,"image_url":"http://api.e3webrtc.com:8004/media/costumes/1104_tiyana_xiangliang.png","unlock_chip":120},"costume_chip_number":0,"costume_number":2},{"costume":{"name":"缇亚娜的枪","index":1105,"image_url":"http://api.e3webrtc.com:8004/media/costumes/1105_tiyana_qiang.png","unlock_chip":120},"costume_chip_number":0,"costume_number":2}]
                 */

                private String name;
                private int index;
                private String image_url;
                private int unlock_chip;
                private List<CostumesBean> costumes;

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

                public String getImage_url() {
                    return image_url;
                }

                public void setImage_url(String image_url) {
                    this.image_url = image_url;
                }

                public int getUnlock_chip() {
                    return unlock_chip;
                }

                public void setUnlock_chip(int unlock_chip) {
                    this.unlock_chip = unlock_chip;
                }

                public List<CostumesBean> getCostumes() {
                    return costumes;
                }

                public void setCostumes(List<CostumesBean> costumes) {
                    this.costumes = costumes;
                }

                public static class CostumesBean implements Serializable{
                    /**
                     * costume : {"name":"缇亚娜的项链","index":1104,"image_url":"http://api.e3webrtc.com:8004/media/costumes/1104_tiyana_xiangliang.png","unlock_chip":120}
                     * costume_chip_number : 0
                     * costume_number : 2
                     */

                    private CostumeBean costume;
                    private int costume_chip_number;
                    private int costume_number;

                    public CostumeBean getCostume() {
                        return costume;
                    }

                    public void setCostume(CostumeBean costume) {
                        this.costume = costume;
                    }

                    public int getCostume_chip_number() {
                        return costume_chip_number;
                    }

                    public void setCostume_chip_number(int costume_chip_number) {
                        this.costume_chip_number = costume_chip_number;
                    }

                    public int getCostume_number() {
                        return costume_number;
                    }

                    public void setCostume_number(int costume_number) {
                        this.costume_number = costume_number;
                    }

                    public static class CostumeBean implements Serializable{
                        /**
                         * name : 缇亚娜的项链
                         * index : 1104
                         * image_url : http://api.e3webrtc.com:8004/media/costumes/1104_tiyana_xiangliang.png
                         * unlock_chip : 120
                         */

                        private String name;
                        private int index;
                        private String image_url;
                        private int unlock_chip;

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

                        public String getImage_url() {
                            return image_url;
                        }

                        public void setImage_url(String image_url) {
                            this.image_url = image_url;
                        }

                        public int getUnlock_chip() {
                            return unlock_chip;
                        }

                        public void setUnlock_chip(int unlock_chip) {
                            this.unlock_chip = unlock_chip;
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

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }

}
