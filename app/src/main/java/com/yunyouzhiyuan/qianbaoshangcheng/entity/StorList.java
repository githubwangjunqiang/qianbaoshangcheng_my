package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/26.
 */
public class StorList {

    /**
     * retcode : 2000
     * msg : 获取成功!
     * data : [{"original_img":"/Public/upload/goods/2017/03-09/58c0f0be06638.jpg","goods_id":"20","goods_name":"仅售98元，价值168元单人洗浴 雲鼎足道，节假日通用，免费WiFi！","prom_type":"2","shop_price":"168.00","month_sales":0}]
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
         * original_img : /Public/upload/goods/2017/03-09/58c0f0be06638.jpg
         * goods_id : 20
         * goods_name : 仅售98元，价值168元单人洗浴 雲鼎足道，节假日通用，免费WiFi！
         * prom_type : 2
         * shop_price : 168.00
         * month_sales : 0
         *    "is_on_sale":1 出售中  0 下架
         */

        private String original_img;
        private String goods_id;
        private String goods_name;
        private String prom_type;
        private String shop_price;
        private int month_sales;
        private String is_on_sale;

        public String getIs_on_sale() {
            return is_on_sale;
        }

        public void setIs_on_sale(String is_on_sale) {
            this.is_on_sale = is_on_sale;
        }

        public String getOriginal_img() {
            return original_img;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getProm_type() {
            return prom_type;
        }

        public void setProm_type(String prom_type) {
            this.prom_type = prom_type;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public int getMonth_sales() {
            return month_sales;
        }

        public void setMonth_sales(int month_sales) {
            this.month_sales = month_sales;
        }
    }
}
