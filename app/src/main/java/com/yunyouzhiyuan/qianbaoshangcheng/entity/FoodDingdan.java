package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/6.
 */

public class FoodDingdan  {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"order_id":"302","order_sn":"201704020151241038","master_order_sn":"201704020151244640","user_id":"1","pay_time":"2017-04-02","order_amount":"226.00","goodsinfo":[{"goods_name":"怡宝矿泉水","goods_id":"3","goods_num":"1","goods_price":"0.00","original_img":"/Public/upload/goods/2017/02-17/58a66ea363733.jpg"},{"goods_name":"乐可芬椰果饮料","goods_id":"25","goods_num":"4","goods_price":"15.00","original_img":"/Public/upload/goods/2017/03-09/58c0fb195e895.jpg"},{"goods_name":"雪花淡爽","goods_id":"26","goods_num":"5","goods_price":"8.00","original_img":"/Public/upload/goods/2017/03-09/58c0fd7015715.jpg"},{"goods_name":"加多宝","goods_id":"27","goods_num":"4","goods_price":"8.00","original_img":"/Public/upload/goods/2017/03-09/58c0fdd89b0fd.jpg"},{"goods_name":"椰汁","goods_id":"28","goods_num":"5","goods_price":"8.00","original_img":"/Public/upload/goods/2017/03-09/58c0fe1890f08.jpg"},{"goods_name":"北冰洋","goods_id":"29","goods_num":"5","goods_price":"6.00","original_img":"/Public/upload/goods/2017/03-09/58c0fe491dc37.jpg"},{"goods_name":"杏仁露","goods_id":"30","goods_num":"4","goods_price":"6.00","original_img":"/Public/upload/goods/2017/03-09/58c0fe88637d9.jpg"}],"mobile":"15075818555","nickname":"旭老板"}]
     */

    private int retcode;
    private String msg;
    private List<DataBean> data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public  class DataBean {
        /**
         * order_id : 302
         * order_sn : 201704020151241038
         * master_order_sn : 201704020151244640
         * user_id : 1
         * pay_time : 2017-04-02
         * order_amount : 226.00
         * goodsinfo : [{"goods_name":"怡宝矿泉水","goods_id":"3","goods_num":"1","goods_price":"0.00","original_img":"/Public/upload/goods/2017/02-17/58a66ea363733.jpg"},{"goods_name":"乐可芬椰果饮料","goods_id":"25","goods_num":"4","goods_price":"15.00","original_img":"/Public/upload/goods/2017/03-09/58c0fb195e895.jpg"},{"goods_name":"雪花淡爽","goods_id":"26","goods_num":"5","goods_price":"8.00","original_img":"/Public/upload/goods/2017/03-09/58c0fd7015715.jpg"},{"goods_name":"加多宝","goods_id":"27","goods_num":"4","goods_price":"8.00","original_img":"/Public/upload/goods/2017/03-09/58c0fdd89b0fd.jpg"},{"goods_name":"椰汁","goods_id":"28","goods_num":"5","goods_price":"8.00","original_img":"/Public/upload/goods/2017/03-09/58c0fe1890f08.jpg"},{"goods_name":"北冰洋","goods_id":"29","goods_num":"5","goods_price":"6.00","original_img":"/Public/upload/goods/2017/03-09/58c0fe491dc37.jpg"},{"goods_name":"杏仁露","goods_id":"30","goods_num":"4","goods_price":"6.00","original_img":"/Public/upload/goods/2017/03-09/58c0fe88637d9.jpg"}]
         * mobile : 15075818555
         * nickname : 旭老板
         */

        private String order_id;
        private String order_sn;
        private String master_order_sn;
        private String user_id;
        private String pay_time;
        private String order_amount;
        private String mobile;
        private String nickname;
        private List<GoodsinfoBean> goodsinfo;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getMaster_order_sn() {
            return master_order_sn;
        }

        public void setMaster_order_sn(String master_order_sn) {
            this.master_order_sn = master_order_sn;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public List<GoodsinfoBean> getGoodsinfo() {
            return goodsinfo;
        }

        public void setGoodsinfo(List<GoodsinfoBean> goodsinfo) {
            this.goodsinfo = goodsinfo;
        }

        public  class GoodsinfoBean {
            /**
             * goods_name : 怡宝矿泉水
             * goods_id : 3
             * goods_num : 1
             * goods_price : 0.00
             * original_img : /Public/upload/goods/2017/02-17/58a66ea363733.jpg
             */

            private String goods_name;
            private String goods_id;
            private String goods_num;
            private String goods_price;
            private String original_img;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(String goods_num) {
                this.goods_num = goods_num;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getOriginal_img() {
                return original_img;
            }

            public void setOriginal_img(String original_img) {
                this.original_img = original_img;
            }
        }
    }
}
