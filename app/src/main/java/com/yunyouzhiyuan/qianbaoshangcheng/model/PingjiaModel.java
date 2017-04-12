package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.Pingjia;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.BaseCallback;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.MyOkHttpClent;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by ${王俊强} on 2017/4/1.
 */

public class PingjiaModel extends IModel {
    /**
     * 42、商家端通过订单id查看订单评论
     * get_comment_by_orderid
     * 传入：order_id
     */
    public void get_comment_by_orderid(String order_id, final AsyncCallBack callBack) {
        MyOkHttpClent.newBuilder().url(HttpUrl.get_comment_by_orderid).post().addParam("order_id", order_id)
                .build().enqueue(new BaseCallback.ComonCallback<Pingjia>() {
            @Override
            protected void onSuccess(Pingjia s) {
                callBack.onSucceed(s);
                LogUtils.d("商家端通过订单id查看订单评论" + s);
            }

            @Override
            protected void onError(int code, String msg) {
                callBack.onError(msg);
            }

            @Override
            protected void onFailure(Call call, IOException e) {
                callBack.onFailure("网络错误");
            }
        });
    }
}
