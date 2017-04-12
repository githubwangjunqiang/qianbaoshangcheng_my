package com.yunyouzhiyuan.qianbaoshangcheng.okhttp;

import okhttp3.OkHttpClient;

/**
 * Created by ${王俊强} on 2017/3/23.
 */

public class OkhttpClent {
    private static OkHttpClient clent;

    private OkhttpClent() {

    }

    public static OkHttpClient getClent() {
        if (clent == null) {
            clent = new OkHttpClient();
        }
        return clent;
    }
}
