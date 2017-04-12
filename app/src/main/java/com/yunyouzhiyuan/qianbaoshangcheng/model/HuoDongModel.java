package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HuoDongTuangou;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.BaseCallback;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.MyOkHttpClent;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by ${王俊强} on 2017/4/1.
 */

public class HuoDongModel extends IModel {
    /**
     * 40、发布活动获取店铺商品列表
     * get_store_goodslist
     * 传入：store_id
     */
    public void getData(String store_id, final AsyncCallBack callBack) {
        MyOkHttpClent.newBuilder().url(HttpUrl.get_store_goodslist).post().addParam("store_id", store_id)
                .build().enqueue(new BaseCallback.ComonCallback<HuoDongTuangou>() {
            @Override
            protected void onSuccess(HuoDongTuangou s) {
                LogUtils.d("发布活动获取店铺商品列表" + s);
                callBack.onSucceed(s.getData());
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

    /**
     * add_group_buying_Goods
     * 传入：store_id （必传）  goods_id产品id   title标题   start_time开始时间   end_time结束时间
     * price团购价格   goods_num 参团数量    goods_name    type(1 新增     2 更新 )
     */
    public void submit(String store_id, String goods_id, String title, String start_time, String end_time
            , String price, String goods_num, String goodsname, int type, final AsyncCallBack callBack) {
        MyOkHttpClent.newBuilder().url(HttpUrl.add_group_buying_Goods).post().addParam("store_id", store_id)
                .addParam("goods_id", goods_id).addParam("title", title).addParam("start_time", start_time)
                .addParam("end_time", end_time).addParam("price", price).addParam("goods_num", goods_num)
                .addParam("goods_name", goodsname).addParam("type", type).build()
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
                        callBack.onFailure("网络不稳定哦");
                    }
                });
    }

    /**
     * 37、商家端发布促销
     * publish_discount_Goods
     * 传入：store_id   goods_id 产品id（id以‘，’隔开的字符串形式）   name 活动名称
     * expression 优惠力度（折扣以50代表5折）  start_time  开始时间
     * end_time  结束时间（以2017-2-15格式）
     */
    public void publish_discount_Goods(String store_id, String goods_id, String name, String expression,
                                       String start_time, String end_time, int type, final AsyncCallBack callBack) {
        MyOkHttpClent.newBuilder().url(HttpUrl.publish_discount_Goods).post().addParam("store_id", store_id)
                .addParam("goods_id", goods_id).addParam("name", name).addParam("expression", expression)
                .addParam("start_time", start_time).addParam("end_time", end_time).addParam("type", type).build()
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
                        callBack.onFailure("网络错误");
                    }
                });

    }
}
