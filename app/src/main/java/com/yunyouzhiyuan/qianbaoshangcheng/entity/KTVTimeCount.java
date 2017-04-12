package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/5.
 */

public class KTVTimeCount {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : {"attr_id":"6","attr_values":["1","2","3","4","5","6"]}
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

    public  class DataBean {
        /**
         * attr_id : 6
         * attr_values : ["1","2","3","4","5","6"]
         */

        private String attr_id;
        private List<String> attr_values;

        public String getAttr_id() {
            return attr_id;
        }

        public void setAttr_id(String attr_id) {
            this.attr_id = attr_id;
        }

        public List<String> getAttr_values() {
            return attr_values;
        }

        public void setAttr_values(List<String> attr_values) {
            this.attr_values = attr_values;
        }
    }
}
