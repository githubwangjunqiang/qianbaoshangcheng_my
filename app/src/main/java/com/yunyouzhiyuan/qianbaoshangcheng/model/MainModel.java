package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.yunyouzhiyuan.qianbaoshangcheng.entity.HomeData;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.BaseCallback;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.MyOkHttpClent;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${王俊强} on 2017/3/22.
 */

public class MainModel extends IModel {
    /**
     * 11、显示店铺信息
     * showshopinfo
     * 传入：sid
     */
    public void getStorData(String sid, final AsyncCallBack callBack) {

        Call sid1 = MyOkHttpClent.newBuilder().url(HttpUrl.showshopinfo).post().addParam("sid", sid).build()
                .enqueue(new BaseCallback.ComonCallback<HomeData>() {
                    @Override
                    protected void onSuccess(HomeData homeData) {
                        List<HomeData.DataBean> data = homeData.getData();
                        callBack.onSucceed(data);
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onFailure(e.getLocalizedMessage());
                    }
                });
    }

    /**
     * 32、查看店铺是否绑定经营项目
     * get_sis_bind
     * 传入：store_id
     */
    public void isChecked(String store_id, final AsyncCallBack callBack) {
        LogUtils.d("查看店铺是否绑定经营项目参数=" + store_id);
        FormBody body = new FormBody.Builder().add("store_id", store_id).build();
        Request request = new Request.Builder().url(HttpUrl.get_sis_bind).post(body).build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("查看店铺是否绑定经营项目" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    runOnUiSuccess(GetJsonRetcode.getmsg(string), callBack);
                } else {
                    runOnUiError(GetJsonRetcode.getmsg(string), callBack);
                }

            }
        });
    }
}
