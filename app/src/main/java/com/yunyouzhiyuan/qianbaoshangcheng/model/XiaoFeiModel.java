package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.VerifiCation;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.BaseCallback;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.MyOkHttpClent;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Call;

/**
 * Created by ${王俊强} on 2017/4/10.
 */

public class XiaoFeiModel extends IModel implements Serializable {
    /**
     * 55、获取店铺待验证订单详情
     * verify_consum_list
     * 传入：store_id  page
     */
    public Call verify_consum_list(String store_id, int page, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(HttpUrl.verify_consum_list).post()
                .addParam("store_id", store_id).addParam("page", page)
                .build().enqueue(new BaseCallback.ComonCallback<VerifiCation>() {
                    @Override
                    protected void onSuccess(VerifiCation s) {
                        callBack.onSucceed(s.getData());
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onFailure("网络出错了");
                    }
                });
    }

    /**
     * 56、搜索店铺待验证订单详情
     * search_verify_consum
     * 传入：store_id  keyword
     */
    public Call search_verify_consum(String store_id, String keyword, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(HttpUrl.search_verify_consum).post()
                .addParam("store_id", store_id).addParam("keyword", keyword)
                .build().enqueue(new BaseCallback.ComonCallback<VerifiCation>() {
                    @Override
                    protected void onSuccess(VerifiCation s) {
                        callBack.onSucceed(s.getData());
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onFailure("网络出错了");
                    }
                });
    }

    /**
     * 57、验证店铺订单
     * verify_consum_order
     * 传入： store_id      order_id   order_sn  infostr mobile   头文文件传入 （time   token  ）
     */
    public Call verify_consum_order(String store_id, String order_id, String order_sn, String infostr,
                                    String mobile, String time, String token, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(HttpUrl.verify_consum_order).post()
                .addParam("store_id", store_id).addParam("order_id", order_id)
                .addParam("order_sn", order_sn).addParam("infostr", infostr).addParam("mobile", mobile)
                .addHeader("time", time).addHeader("token", token)
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
                        callBack.onFailure("网络出错了");
                    }
                });
    }


}
