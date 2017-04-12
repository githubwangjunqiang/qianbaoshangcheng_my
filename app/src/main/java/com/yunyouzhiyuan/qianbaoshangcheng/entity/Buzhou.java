package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/25.
 */
public class Buzhou {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"id":"14","path":"/Uploads/Picture/2016-11-24/5836bb940b43e.png"},{"id":"15","path":"/Uploads/Picture/2016-11-24/5836bbb84ef54.png"},{"id":"16","path":"/Uploads/Picture/2016-11-24/5836bc0cabb70.png"},{"id":"17","path":"/Uploads/Picture/2016-11-24/5836bc3e80177.png"},{"id":"18","path":"/Uploads/Picture/2016-11-24/5836bc59ebf0e.png"},{"id":"19","path":"/Uploads/Picture/2016-11-24/5836bc776b348.png"}]
     */

    private int retcode;
    private String msg;
    /**
     * id : 14
     * path : /Uploads/Picture/2016-11-24/5836bb940b43e.png
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
        private String id;
        private String path;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
