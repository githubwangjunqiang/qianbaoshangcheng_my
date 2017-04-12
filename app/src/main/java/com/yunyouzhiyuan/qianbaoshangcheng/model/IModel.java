package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.OkhttpClent;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class IModel {
    public interface AsyncCallBack {
        void onSucceed(Object object);//请求成功

        void onError(Object object);//请求失败

        void onFailure(String string);//请求失败
    }

    protected OkHttpClient client = OkhttpClent.getClent();
    protected MainHandler handler = MainHandler.getInstance();

    protected void runOnUiError(final Object object, final AsyncCallBack callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onError(object);
            }
        });
    }

    protected void runOnUiSuccess(final Object object, final AsyncCallBack callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSucceed(object);
            }
        });
    }

}
