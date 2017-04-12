package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/22.
 */

public class HomeData {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"sid":"25","stroname":"清风旭老板","slogo":"/Uploads/Picture/2017-03-22/2017032214015293.jpg","xx_addr":"东方国际大厦","connecter":"旭老板","cphone":"15077777777"}]
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
         * sid : 25
         * stroname : 清风旭老板
         * slogo : /Uploads/Picture/2017-03-22/2017032214015293.jpg
         * xx_addr : 东方国际大厦
         * connecter : 旭老板
         * cphone : 15077777777
         */

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

        public String getStroname() {
            return stroname;
        }

        public void setStroname(String stroname) {
            this.stroname = stroname;
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
