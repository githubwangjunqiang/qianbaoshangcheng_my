package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.io.Serializable;

/**
 * Created by ${王俊强} on 2017/3/29.
 */

public class Test implements Serializable {
    private String id;
    private String item;

    public Test(String id, String item) {
        this.id = id;
        this.item = item;
    }
}
