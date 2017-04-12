package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/1.
 */

public class HuoDongTuangou {

    /**
     * retcode : 2000
     * msg : 获取成功!
     * data : [{"goods_id":"35","goods_name":"豪华时尚全日房"}]
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
         * goods_id : 35
         * goods_name : 豪华时尚全日房
         */

        private String goods_id;
        private String goods_name;

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
    }
}
