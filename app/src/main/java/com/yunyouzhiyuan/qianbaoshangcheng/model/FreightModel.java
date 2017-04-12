package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.yunyouzhiyuan.qianbaoshangcheng.entity.Freight;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.BaseCallback;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.MyOkHttpClent;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by ${王俊强} on 2017/4/7.
 */

public class FreightModel extends IModel {
    /**
     * 54、获取商家起送价和运费
     * get_send_price
     * 传入：store_id
     */
    public void get_send_price(String store_id, final AsyncCallBack callBack) {
        MyOkHttpClent.newBuilder().url(HttpUrl.get_send_price).post().addParam("store_id", store_id)
                .build().enqueue(new BaseCallback.ComonCallback<Freight>() {
            @Override
            protected void onSuccess(Freight s) {
                if (s.getData() != null && !s.getData().isEmpty()) {
                    callBack.onSucceed(s.getData());
                } else {
                    callBack.onError("返回数据异常");
                }

            }

            @Override
            protected void onError(int code, String msg) {
                callBack.onError(msg);
            }

            @Override
            protected void onFailure(Call call, IOException e) {
                callBack.onFailure("是不是网络出问题了");
            }
        });
    }

    /**
     * 53、编辑商家起送价和运费
     * edit_send_price
     * 传入：store_id   shipping_price   send_price
     */
    public void edit_send_price(String store_id, String shipping_price, String send_price, final AsyncCallBack callBack) {
        MyOkHttpClent.newBuilder().url(HttpUrl.edit_send_price).post().addParam("store_id", store_id)
                .addParam("shipping_price", shipping_price).addParam("send_price", send_price)
                .build().enqueue(new BaseCallback.ComonCallback<String>() {
            @Override
            protected void onSuccess(String s) {
                if (GetJsonRetcode.getRetcodeCode(s)) {
                    callBack.onSucceed(GetJsonRetcode.getmsg(s));
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(s));
                }
            }

            @Override
            protected void onError(int code, String msg) {
                callBack.onError(msg);
            }

            @Override
            protected void onFailure(Call call, IOException e) {
                callBack.onFailure("是不是网络出问题了");
            }
        });
    }
}
