package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/27.
 */

public class KTVList {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"id":"8","name":"房型","cat_id1":"21","cat_id2":"26","cat_id3":"49","specItemList":[{"item_id":"31","item":"旭老板清风徐来"}]},{"id":"9","name":"预订时间","cat_id1":"21","cat_id2":"26","cat_id3":"49","specItemList":[]},{"id":"10","name":"星期","cat_id1":"21","cat_id2":"26","cat_id3":"49","specItemList":[]}]
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
         * id : 8
         * name : 房型
         * cat_id1 : 21
         * cat_id2 : 26
         * cat_id3 : 49
         * specItemList : [{"item_id":"31","item":"旭老板清风徐来"}]
         */

        private String id;
        private String name;
        private String cat_id1;
        private String cat_id2;
        private String cat_id3;
        private List<SpecItemListBean> specItemList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCat_id1() {
            return cat_id1;
        }

        public void setCat_id1(String cat_id1) {
            this.cat_id1 = cat_id1;
        }

        public String getCat_id2() {
            return cat_id2;
        }

        public void setCat_id2(String cat_id2) {
            this.cat_id2 = cat_id2;
        }

        public String getCat_id3() {
            return cat_id3;
        }

        public void setCat_id3(String cat_id3) {
            this.cat_id3 = cat_id3;
        }

        public List<SpecItemListBean> getSpecItemList() {
            return specItemList;
        }

        public void setSpecItemList(List<SpecItemListBean> specItemList) {
            this.specItemList = specItemList;
        }

        public static class SpecItemListBean {
            /**
             * item_id : 31
             * item : 旭老板清风徐来
             */

            private String item_id;
            private String item;
            private boolean isChecked;

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public SpecItemListBean(String item_id, String item) {
                this.item_id = item_id;
                this.item = item;
            }

            public String getItem_id() {
                return item_id;
            }

            public void setItem_id(String item_id) {
                this.item_id = item_id;
            }

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }
        }
    }
}
