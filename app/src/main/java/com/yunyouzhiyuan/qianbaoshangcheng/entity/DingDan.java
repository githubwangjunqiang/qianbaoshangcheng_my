package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class DingDan {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"order_id":"300","order_sn":"201703310932375050","master_order_sn":"201703310932372342","user_id":"31","goods_name":"周一至周日王KTV团小房欢唱 03-31 14开始","goods_id":"13","prom_type":"2","goods_price":"80.00","add_time":"1490923957","end_time":"2017-07-31","start_time":"2017-02-25","mobile":"15066666666","original_img":"/Public/upload/goods/2017/02-24/58afcf5d337d8.jpg"}]
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


    public class DataBean {
        /**
         * order_id : 300
         * order_sn : 201703310932375050
         * master_order_sn : 201703310932372342
         * user_id : 31
         * goods_name : 周一至周日王KTV团小房欢唱 03-31 14开始
         * goods_id : 13
         * prom_type : 2
         * goods_price : 80.00
         * add_time : 1490923957
         * end_time : 2017-07-31
         * start_time : 2017-02-25
         * mobile : 15066666666
         * original_img : /Public/upload/goods/2017/02-24/58afcf5d337d8.jpg
         *
         * goods_rank
         *
         */

        private String order_id;
        private String order_sn;
        private String master_order_sn;
        private String user_id;
        private String goods_name;
        private String goods_id;
        private String prom_type;
        private String goods_price;
        private String add_time;
        private String end_time;
        private String start_time;
        private String mobile;
        private String original_img;
        private String goods_rank;

        public String getGoods_rank() {
            return goods_rank;
        }

        public void setGoods_rank(String goods_rank) {
            this.goods_rank = goods_rank;
        }

        public String getOrder_id() {
            return order_id == null ? "" : order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn == null ? "" : order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getMaster_order_sn() {
            return master_order_sn == null ? "" : master_order_sn;
        }

        public void setMaster_order_sn(String master_order_sn) {
            this.master_order_sn = master_order_sn;
        }

        public String getUser_id() {
            return user_id == null ? "" : user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getGoods_name() {
            return goods_name == null ? "" : goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_id() {
            return goods_id == null ? "" : goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getProm_type() {
            return prom_type == null ? "" : prom_type;
        }

        public void setProm_type(String prom_type) {
            this.prom_type = prom_type;
        }

        public String getGoods_price() {
            return goods_price == null ? "" : goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getAdd_time() {
            return add_time == null ? "" : add_time;

        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getEnd_time() {
            return end_time == null ? "" : end_time;

        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getStart_time() {
            return start_time == null ? "" : start_time;

        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOriginal_img() {
            return original_img == null ? "" : original_img;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }
    }
}
