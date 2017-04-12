package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.Jilui;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.logo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by wangjunqiang on 2016/11/25.
 */
public class ShenQingjiluModel extends IModel {
    /**
     * 获取店铺申请记录信息
     *
     * @param sid
     * @param status   传入：sid  status（0审核中  1通过审核 2、未通过）
     * @param callBack
     */
    public void getjilu(String sid, int status, final AsyncCallBack callBack) {
        RequestParams params = new RequestParams(HttpUrl.STORJILU);
        params.addParameter("sid", sid);
        params.addParameter("status", status);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                logo.e("222"+result);
                if (GetJsonRetcode.getRetcode(result) == 2000) {
                    callBack.onSucceed(new Gson().fromJson(result, Jilui.class));
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
