package com.yunyouzhiyuan.qianbaoshangcheng.entity;

/**
 * Created by ${王俊强} on 2017/3/27.
 */

public class AddFangXing {

    /**
     * retcode : 2000
     * msg : 添加成功！
     * data : {"spec_item_id":"32"}
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
         * spec_item_id : 32
         */

        private String spec_item_id;

        public String getSpec_item_id() {
            return spec_item_id;
        }

        public void setSpec_item_id(String spec_item_id) {
            this.spec_item_id = spec_item_id;
        }
    }
}
