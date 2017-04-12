package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/12.
 */

public class MessageInfo {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"order_id":"362","order_sn":"201704111800536944","master_order_sn":"201704111800534228","user_id":"1","pay_time":"2017-04-11","goods_name":"周一至周日王KTV团小房欢唱 04.11 14开始","goods_id":"13","prom_type":"2","goods_price":"80.00","mobile":"15075818555","nickname":"旭老板"},{"order_id":"355","order_sn":"201704111158559280","master_order_sn":"201704111158552954","user_id":"1","pay_time":"2017-04-11","goods_name":"周一至周日王KTV团小房欢唱 04.11 14开始","goods_id":"13","prom_type":"2","goods_price":"80.00","mobile":"15075818555","nickname":"旭老板"},{"order_id":"354","order_sn":"201704111156474293","master_order_sn":"201704111156477708","user_id":"1","pay_time":"2017-04-11","goods_name":"周一至周日王KTV团小房欢唱 04.11 14开始","goods_id":"13","prom_type":"2","goods_price":"80.00","mobile":"15075818555","nickname":"旭老板"},{"order_id":"351","order_sn":"201704111039266390","master_order_sn":"201704111039265146","user_id":"1","pay_time":"2017-04-11","goods_name":"周一至周日王KTV团小房欢唱 04.11 14开始","goods_id":"13","prom_type":"2","goods_price":"80.00","mobile":"15075818555","nickname":"旭老板"}]
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
         * order_id : 362
         * order_sn : 201704111800536944
         * master_order_sn : 201704111800534228
         * user_id : 1
         * pay_time : 2017-04-11
         * goods_name : 周一至周日王KTV团小房欢唱 04.11 14开始
         * goods_id : 13
         * prom_type : 2
         * goods_price : 80.00
         * mobile : 15075818555
         * nickname : 旭老板
         */

        private String order_id;
        private String order_sn;
        private String master_order_sn;
        private String user_id;
        private String pay_time;
        private String goods_name;
        private String goods_id;
        private String prom_type;
        private String order_amount;

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        private String mobile;
        private String nickname;

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

        public String getProm_type() {
            return prom_type;
        }

        public void setProm_type(String prom_type) {
            this.prom_type = prom_type;
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
    }
}
