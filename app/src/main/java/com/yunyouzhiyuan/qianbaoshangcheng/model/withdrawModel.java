package com.yunyouzhiyuan.qianbaoshangcheng.model;

import android.os.SystemClock;

import com.yunyouzhiyuan.qianbaoshangcheng.entity.BankInfo;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.Mingxi;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.BaseCallback;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.MyOkHttpClent;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.MD5Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;

/**
 * Created by ${王俊强} on 2017/4/11.
 */

public class withdrawModel extends IModel {
    /**
     * 59、商家端  获取店铺余额
     * gets_store_money
     * 传入：store_id
     */
    public Call getData(String store_id, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(HttpUrl.gets_store_money).get().addParam("store_id",
                store_id).build().enqueue(new BaseCallback.ComonCallback<BankInfo>() {
            @Override
            protected void onSuccess(BankInfo bean) {
                callBack.onSucceed(bean);
            }

            @Override
            protected void onError(int code, String msg) {
                callBack.onError(msg);
            }

            @Override
            protected void onFailure(Call call, IOException e) {
                callBack.onFailure("网络链接不稳定");
            }
        });
    }

    /**
     * 60、商家端  获取提现记录
     * store_withdrawals_list
     * 传入：store_id
     */
    public Call getDetails(String store_id, int page, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(HttpUrl.store_withdrawals_list).get()
                .addParam("store_id", store_id).addParam("page", page)
                .build().enqueue(new BaseCallback.ComonCallback<Mingxi>() {
                    @Override
                    protected void onSuccess(Mingxi s) {
                        callBack.onSucceed(s.getData());
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onFailure("网络链接不稳定");
                    }
                });
    }

    /**
     * 58、店铺申请提现
     * store_withdrawals
     * 传入：store_id   头文件传入（time   token ）  money
     */
    public Call loadDetails(String store_id, String money, final AsyncCallBack callBack) {
        long timeMillis = SystemClock.currentThreadTimeMillis();
        List<String> list = new ArrayList<>();
        list.add("" + timeMillis);
        list.add(store_id);
        list.add("store_withdrawals");
        Collections.sort(list);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            buffer.append(list.get(i));
        }
        LogUtils.d("排序后=" + buffer.toString());
        String s = MD5Utils.md5Code(buffer.toString());
        return MyOkHttpClent.newBuilder().url(HttpUrl.store_withdrawals).post()
                .addParam("store_id", store_id).addParam("money", money)
                .addHeader("time", timeMillis).addHeader("token", s)
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
                        callBack.onFailure("网络链接不稳定");
                    }
                });
    }
}
