package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.yunyouzhiyuan.qianbaoshangcheng.entity.DingDan;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.FoodDingdan;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.FoodDingdan_New;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.BaseCallback;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.MyOkHttpClent;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by ${王俊强} on 2017/3/31.
 */

public class DingDanModel extends IModel {
    /**
     * 38、优惠券订单
     * get_coupon_order
     * 传入：store_id       type （0 最新订单  1 已使用订单  2 过期订单）     page
     */
    public void get_coupon_order(String store_id, String type, int page, final AsyncCallBack callBack) {
        LogUtils.d("优惠券订单参数" + HttpUrl.get_coupon_order + "?store_id=" + store_id + "&type=" + type + "&page=" + page);
        MyOkHttpClent.newBuilder().url(HttpUrl.get_coupon_order).post().addParam("store_id", store_id)
                .addParam("type", type).addParam("page", page).build().
                enqueue(new BaseCallback.ComonCallback<DingDan>() {
                    @Override
                    protected void onSuccess(DingDan s) {
                        callBack.onSucceed(s.getData());
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onFailure("网络原因");
                    }
                });
    }

    /**
     * 47、外卖退款订单
     * gets_return_order
     * 传入：store_id
     */
    public void gets_return_order(String store_id, int page, final AsyncCallBack callBack) {
        LogUtils.d("优惠券订单参数" + HttpUrl.gets_return_order + "?store_id=" + store_id + "&page=" + page);
        MyOkHttpClent.newBuilder().url(HttpUrl.gets_return_order).post().addParam("store_id", store_id)
                .addParam("page", page).build().
                enqueue(new BaseCallback.ComonCallback<FoodDingdan>() {
                    @Override
                    protected void onSuccess(FoodDingdan s) {
                        callBack.onSucceed(s.getData());
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onFailure("网络原因");
                    }
                });
    }

    /**
     * 48、外卖历史订单
     * gets_history_order
     * 传入：store_id   page
     */
    public void gets_history_order(String store_id, int page, final AsyncCallBack callBack) {
        LogUtils.d("外卖历史订单" + HttpUrl.gets_history_order + "?store_id=" + store_id + "&page=" + page);
        MyOkHttpClent.newBuilder().url(HttpUrl.gets_history_order).post().addParam("store_id", store_id)
                .addParam("page", page).build().
                enqueue(new BaseCallback.ComonCallback<FoodDingdan>() {
                    @Override
                    protected void onSuccess(FoodDingdan s) {
                        callBack.onSucceed(s.getData());
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onFailure("网络原因");
                    }
                });
    }

    /**
     * 44、 获取外卖最新订单
     * gets_takeaway_order
     * 传入：store_id   page
     */
    public void gets_takeaway_order(String store_id, int page, final AsyncCallBack callBack) {
        LogUtils.d("获取外卖最新订单" + HttpUrl.gets_takeaway_order + "?store_id=" + store_id + "&page=" + page);
        MyOkHttpClent.newBuilder().url(HttpUrl.gets_takeaway_order).post().addParam("store_id", store_id)
                .addParam("page", page).build().
                enqueue(new BaseCallback.ComonCallback<FoodDingdan_New>() {
                    @Override
                    protected void onSuccess(FoodDingdan_New s) {
                        callBack.onSucceed(s.getData());
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onFailure("网络原因");
                    }
                });
    }

    /**
     * 49、商家端 商家接单
     * store_order_taking
     * 传入：order_id
     *
     * @return
     */
    public Call orDers(String order_id, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(HttpUrl.store_order_taking).post().addParam("order_id", order_id).build()
                .enqueue(new BaseCallback.ComonCallback<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if (GetJsonRetcode.getRetcode(s) == 2000) {
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
                        callBack.onFailure("网络是不是出错了");
                    }
                });

    }

    /**
     * 52、商家端 跑腿发布
     * send_express_order
     * 传入：order_id    store_id      remark
     */
    public Call send_express_order(String order_id, String store_id, String remark, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(HttpUrl.send_express_order).post().addParam("order_id", order_id)
                .addParam("store_id", store_id).addParam("remark", remark).build()
                .enqueue(new BaseCallback.ComonCallback<String>() {
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
                        callBack.onFailure("网络是不是出问题了");
                    }
                });
    }
}
