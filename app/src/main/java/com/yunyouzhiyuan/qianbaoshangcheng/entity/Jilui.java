package com.yunyouzhiyuan.qianbaoshangcheng.entity;

/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class Jilui {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : {"slogo":"/Uploads/Picture/2017-03-21/20170321164542945.jpg","time":"2017-03-22 10:35:28","store_id":"10"}
     */

    private int retcode;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean {
        /**
         * slogo : /Uploads/Picture/2017-03-21/20170321164542945.jpg
         * time : 2017-03-22 10:35:28
         * store_id : 10
         */

        private String slogo;
        private String time;
        private String store_id;
        private String sc_id;

        public String getSc_id() {
            return sc_id;
        }

        public void setSc_id(String sc_id) {
            this.sc_id = sc_id;
        }

        public String getSlogo() {
            return slogo;
        }

        public void setSlogo(String slogo) {
            this.slogo = slogo;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }
    }
}
