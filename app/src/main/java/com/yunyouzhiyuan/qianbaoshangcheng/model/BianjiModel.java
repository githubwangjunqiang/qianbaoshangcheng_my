package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.StorInfo;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by wangjunqiang on 2016/11/26.
 */
public class BianjiModel extends IModel {
    /**
     * 获取店铺信息
     *
     * @param sid
     * @param callBack
     */
    public void getdata(String sid, final AsyncCallBack callBack) {
        RequestParams params = new RequestParams(HttpUrl.STORINFO);
        params.addParameter("sid", sid);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtils.e(result);
                if (GetJsonRetcode.getRetcode(result) == 2000) {
                    callBack.onSucceed(new Gson().fromJson(result, StorInfo.class));
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
     * 修改店铺信息
     * 传入：stroName  店铺名称,slogo  店铺logo,xx_addr  详细地址,
     * connecter  联系人,cphone 联系电话（可选，至少传一个）
     */
    public void setdata(String sid,String stroName,String slogo,String xx_addr,String connecter,String cphone, final AsyncCallBack callBack) {
        RequestParams params = new RequestParams(HttpUrl.SETSTOR);
        params.addParameter("stroName", stroName);
        params.addParameter("slogo", slogo);
        params.addParameter("xx_addr", xx_addr);
        params.addParameter("connecter", connecter);
        params.addParameter("cphone", cphone);
        params.addParameter("sid", sid);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (GetJsonRetcode.getRetcode(result) == 2000) {
                    callBack.onSucceed(GetJsonRetcode.getmsg(result));
                } else if(GetJsonRetcode.getRetcode(result) == 4000){
                    callBack.onError(R.string.ninmeizuorenhexiugi);
                }else{
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
