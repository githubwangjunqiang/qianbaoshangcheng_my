package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.BaseCallback;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.MyOkHttpClent;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by ${王俊强} on 2017/4/12.
 */

public class MyModel extends IModel {
    /**
     * 63、商家端   反馈
     * feed_back
     * 传入：store_id   f_content
     */
    public Call feed_back(String store_id, String f_content, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(HttpUrl.feed_back).post().addParam("store_id", store_id)
                .addParam("f_content", f_content).build().enqueue(new BaseCallback.ComonCallback<String>() {
                    @Override
                    protected void onSuccess(String bean) {
                        if (GetJsonRetcode.getRetcodeCode(bean)) {
                            callBack.onSucceed(GetJsonRetcode.getmsg(bean));
                        } else {
                            callBack.onError(GetJsonRetcode.getmsg(bean));
                        }

                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onFailure("网络断了");
                    }
                });

    }
}
