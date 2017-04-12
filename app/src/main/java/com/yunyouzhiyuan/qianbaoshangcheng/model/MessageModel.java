package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.MessageInfo;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.BaseCallback;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.MyOkHttpClent;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by ${王俊强} on 2017/4/12.
 */

public class MessageModel extends IModel {
    /**
     * 61、商家端  消息列表
     * store_message_list
     * 传入：store_id   page
     */
    public Call store_message_list(String store_id, int page, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(HttpUrl.store_message_list).post().addParam("store_id", store_id)
                .addParam("page", page).build().enqueue(new BaseCallback.ComonCallback<MessageInfo>() {
                    @Override
                    protected void onSuccess(MessageInfo bean) {
                        callBack.onSucceed(bean.getData());
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
