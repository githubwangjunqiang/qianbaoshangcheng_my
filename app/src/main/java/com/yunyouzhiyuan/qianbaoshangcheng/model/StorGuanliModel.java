package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.StorList;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.BaseCallback;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.MyOkHttpClent;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.logo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by wangjunqiang on 2016/11/25.
 */
public class StorGuanliModel extends IModel {
    /**
     * 43、商家端获取店铺商品信息列表
     * gets_store_goods_lists
     * 传入：store_id
     */
    public void getdata(String store_id, int page, final AsyncCallBack callBack) {
        LogUtils.d("商家端获取店铺商品信息列表="+store_id+"/page="+page);
        MyOkHttpClent.newBuilder().url(HttpUrl.gets_store_goods_lists).post().addParam("page", page)
                .addParam("store_id", store_id)
                .build().enqueue(new BaseCallback.ComonCallback<StorList>() {
            @Override
            protected void onSuccess(StorList storList) {
                callBack.onSucceed(storList.getData());
            }

            @Override
            protected void onError(int code, String msg) {
                callBack.onError(msg);
            }

            @Override
            protected void onFailure(Call call, IOException e) {
                callBack.onFailure("网络是不是有问题呢？");
            }
        });
    }

    /**
     * 商品上下架
     * 传入：sid   page
     */
    public void tounStorStas(String goods_id, final AsyncCallBack callBack) {
        RequestParams params = new RequestParams(HttpUrl.STORSHANGXIAJIA);
        params.addParameter("goods_id", goods_id);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (GetJsonRetcode.getRetcode(result) == 2000) {
                    callBack.onSucceed(GetJsonRetcode.getmsg(result));
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(result));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError("网络是不是有问题呢？");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onError("网络是不是有问题呢？");
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 搜索商品
     */
    public void sousuo(String keyword, String sid, final AsyncCallBack callBack) {
        RequestParams params = new RequestParams(HttpUrl.STORSOUSUO);
        params.addParameter("keyword", keyword);
        params.addParameter("sid", sid);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                logo.e(result);
                if (GetJsonRetcode.getRetcode(result) == 2000) {
                    callBack.onSucceed(new Gson().fromJson(result, StorList.class).getData());
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(result));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError("网络是不是有问题呢？");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onError("网络是不是有问题呢？");
            }

            @Override
            public void onFinished() {

            }
        });
    }
}
