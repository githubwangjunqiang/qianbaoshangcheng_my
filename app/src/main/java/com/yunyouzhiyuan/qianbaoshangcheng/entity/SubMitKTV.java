package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/31.
 */

public class SubMitKTV {
    private String goods_id;
    private List<Info> info;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

    public static class Info{
        private String key;
        private String price;
        private String store_count;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStore_count() {
            return store_count;
        }

        public void setStore_count(String store_count) {
            this.store_count = store_count;
        }
    }
}
