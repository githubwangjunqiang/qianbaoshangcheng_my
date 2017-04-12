package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/25.
 */
public class LeiXing {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"id":"98","title":"餐饮"},{"id":"99","title":"电器"},{"id":"100","title":"服装"},{"id":"101","title":"鞋子"}]
     */

    private int retcode;
    private String msg;
    /**
     * id : 98
     * title : 餐饮
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
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
