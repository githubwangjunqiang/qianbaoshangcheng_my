package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/26.
 */
public class StorInfo {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"sid":"84","stroName":"mmmm","slogo":"/Uploads/Picture/2016-11-25/20161125173408164.jpg","xx_addr":"ttt","connecter":"mmmm","cphone":"9999"}]
     */

    private int retcode;
    private String msg;
    /**
     * sid : 84
     * stroName : mmmm
     * slogo : /Uploads/Picture/2016-11-25/20161125173408164.jpg
     * xx_addr : ttt
     * connecter : mmmm
     * cphone : 9999
     */

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
        private String sid;
        private String stroname;
        private String slogo;
        private String xx_addr;
        private String connecter;
        private String cphone;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getStroName() {
            return stroname;
        }

        public void setStroName(String stroName) {
            this.stroname = stroName;
        }

        public String getSlogo() {
            return slogo;
        }

        public void setSlogo(String slogo) {
            this.slogo = slogo;
        }

        public String getXx_addr() {
            return xx_addr;
        }

        public void setXx_addr(String xx_addr) {
            this.xx_addr = xx_addr;
        }

        public String getConnecter() {
            return connecter;
        }

        public void setConnecter(String connecter) {
            this.connecter = connecter;
        }

        public String getCphone() {
            return cphone;
        }

        public void setCphone(String cphone) {
            this.cphone = cphone;
        }
    }
}
