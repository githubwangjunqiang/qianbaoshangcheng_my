package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/6.
 */

public class FoodCuisine {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"cat_id":"1","cat_name":"热销"},{"cat_id":"2","cat_name":"热菜系列"},{"cat_id":"3","cat_name":"凉菜系列"},{"cat_id":"4","cat_name":"汤系列"},{"cat_id":"5","cat_name":"主食"},{"cat_id":"6","cat_name":"酒水"}]
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

    public static class DataBean {
        /**
         * cat_id : 1
         * cat_name : 热销
         */

        private String cat_id;
        private String cat_name;

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }
    }
}
