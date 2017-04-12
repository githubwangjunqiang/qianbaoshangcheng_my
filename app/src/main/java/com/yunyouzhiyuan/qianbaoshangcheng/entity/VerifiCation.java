package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/10.
 */

public class VerifiCation {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"order_id":"346","master_order_sn":"201704101421444573","user_id":"1","mobile":"15075818555","order_amount":"180.00","goods_id":"35","goods_num":"1","goods_price":"200.00","group_price":"250.00","original_img":"/Uploads/Picture/2017-03-27/20170327111505358.jpg"}]
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

    public static class DataBean {
        /**
         * order_id : 346
         * master_order_sn : 201704101421444573
         * user_id : 1
         * mobile : 15075818555
         * order_amount : 180.00
         * goods_id : 35
         * goods_num : 1
         * goods_price : 200.00
         * group_price : 250.00
         * original_img : /Uploads/Picture/2017-03-27/20170327111505358.jpg
         */

        private String order_id;
        private String goods_name;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        private String master_order_sn;
        private String user_id;
        private String mobile;
        private String order_amount;
        private String goods_id;
        private String goods_num;
        private String goods_price;
        private String group_price;
        private String original_img;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
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

        public String getGroup_price() {
            return group_price;
        }

        public void setGroup_price(String group_price) {
            this.group_price = group_price;
        }

        public String getOriginal_img() {
            return original_img;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }
    }
}
