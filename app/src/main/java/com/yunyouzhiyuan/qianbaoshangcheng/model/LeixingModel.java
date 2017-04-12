package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.JingYingLeixing;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${王俊强} on 2017/3/23.
 */

public class LeixingModel extends IModel {
    /**
     * 26、获取商家申请分类下的子分类   用于绑定经营项目
     * get_bind_scategory
     * 传入：store_id   parent_id (默认0)  sc_id  店铺类型id
     */
    public void getData(String store_id, String parent_id, String sc_id,final AsyncCallBack callBack) {
        LogUtils.d("获取商家申请分类下的子分类-参数=" + store_id + "/" + parent_id+"/scid="+sc_id);
        FormBody body = new FormBody.Builder().add("store_id", store_id)
                .add("parent_id", parent_id).add("sc_id",sc_id).build();
        Request request = new Request.Builder().url(HttpUrl.get_bind_scategory).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取商家申请分类下的子分类" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<JingYingLeixing.DataBean> data = new Gson().fromJson(string, JingYingLeixing.class).getData();
                    runOnUiSuccess(data, callBack);
                } else {
                    runOnUiError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
    }

    /**
     * 27、商家申请经营项目类型
     * apply_business
     * 传入：store_id   class_1   class_2  class_3
     */
    public void toOKToijiao(String store_id, String class_1, String class_2, String class_3, final AsyncCallBack callBack) {
        LogUtils.d("商家申请经营项目类型"+store_id);
        FormBody body = new FormBody.Builder().add("store_id", store_id).add("class_1", class_1)
                .add("class_2", class_2).add("class_3", class_3).build();
        Request request = new Request.Builder().url(HttpUrl.apply_business).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取类型数量信息" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    runOnUiSuccess(GetJsonRetcode.getmsg(string), callBack);
                } else {
                    runOnUiError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
    }
}
