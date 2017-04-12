package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/23.
 */

public class JingYingLeixing {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"id":"1","mobile_name":"美食"}]
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
         * id : 1
         * mobile_name : 美食
         */

        private String id;
        private String mobile_name;
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile_name() {
            return mobile_name;
        }

        public void setMobile_name(String mobile_name) {
            this.mobile_name = mobile_name;
        }
    }
}
