package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.Buzhou;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.City;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.LeiXing;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.ShenqingInfo;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by wangjunqiang on 2016/11/25.
 */
public class StorShenqingModel extends IModel {
    /**
     * 获取申请步骤
     *
     * @param callBack
     */
    public void getshenqingbuzhou(final AsyncCallBack callBack) {
        RequestParams params = new RequestParams(HttpUrl.SHENQINGBUZHOU);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtils.d("获取申请步骤="+result);
                if (GetJsonRetcode.getRetcode(result) == 2000) {
                    callBack.onSucceed(new Gson().fromJson(result, Buzhou.class).getData());
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(result));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError(R.string.huoqushibai);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onError(R.string.huoqushibai);
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 城市三级联动
     *
     * @param parent_id
     * @param callBack
     */
    public void getCity(String parent_id, final AsyncCallBack callBack) {
        RequestParams parms = new RequestParams(HttpUrl.CITY);
        if (null != parent_id) {
            parms.addParameter("parent_id", parent_id);
        }
        x.http().get(parms, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (2000 == GetJsonRetcode.getRetcode(result)) {
                    callBack.onSucceed(new Gson().fromJson(result, City.class).getData());
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(result));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError(R.string.huoqushibai);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Too.oo(R.string.beiquxiao);
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 获取店铺分类
     */
    public void getStorLeiXing(final AsyncCallBack callBack) {
        x.http().get(new RequestParams(HttpUrl.STOR_LEIXING), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (GetJsonRetcode.getRetcode(result) == 2000) {
                    callBack.onSucceed(new Gson().fromJson(result, LeiXing.class).getData());
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(result));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError(R.string.huoqushibai);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onError(R.string.huoqushibai);
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 获取银行卡名称
     */
    public void getBankname(String bank_num, final AsyncCallBack callBack) {
        RequestParams params = new RequestParams(HttpUrl.BANKNAME);
        params.addParameter("account_bank", bank_num);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtils.d("获取银行卡名称" + result);
                if (GetJsonRetcode.getRetcode(result) == 2000) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject data = jsonObject.getJSONObject("data");
                        String bank_name = data.getString("bank_name");
                        callBack.onSucceed(bank_name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callBack.onError(R.string.huoqushibai);
                    }
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(result));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError(R.string.huoqushibai);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onError(R.string.huoqushibai);
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 注册商家
     * province_id  省id
     * city_id    市id
     * district   区id
     * jingdu  经度
     * weidu   纬度
     */
    public void tozhuceStor(String id, String sid, String stroName, String connecter, String cphone, String addr,
                            String xx_addr, String stype, String introducetext, String outpicture, String insidepicture,
                            String slogo, String province_id, String city_id, String district,
                            String IDname, String IDcard, String hand_card, String lic_name,
                            String lic_code, String lic_addr, String lic_endtime, String lic_picture,
                            String per_name, String per_code, String per_addr, String per_endtime,
                            String per_picture, String bank_name, String bank_num, String bank_uname,
                            String gpicture, String gprice, String avgprice,
                            String jingdu,String weidu,
                            final AsyncCallBack callBack) {
        RequestParams params = new RequestParams(HttpUrl.ZHUCE);

        if (id != null) {
            params.addParameter("id", id);
        }
        params.addParameter("jingdu", jingdu);
        params.addParameter("weidu", weidu);

        params.addParameter("sid", sid);
        params.addParameter("stroname", stroName);
        params.addParameter("connecter", connecter);
        params.addParameter("cphone", cphone);
        params.addParameter("addr", addr);
        params.addParameter("xx_addr", xx_addr);
        params.addParameter("stype", stype);
        params.addParameter("introducetext", introducetext);
        params.addParameter("outpicture", outpicture);
        params.addParameter("insidepicture", insidepicture);
        params.addParameter("slogo", slogo);


        params.addParameter("province_id", province_id);
        params.addParameter("city_id", city_id);
        params.addParameter("district", district);


        params.addParameter("idname", IDname);
        params.addParameter("idcard", IDcard);
        params.addParameter("lic_name", lic_name);
        params.addParameter("hand_card", hand_card);
        params.addParameter("lic_code", lic_code);
        params.addParameter("lic_addr", lic_addr);
        params.addParameter("lic_endtime", lic_endtime);
        params.addParameter("lic_picture", lic_picture);
        params.addParameter("per_name", per_name);
        params.addParameter("per_addr", per_addr);
        params.addParameter("per_endtime", per_endtime);
        params.addParameter("per_picture", per_picture);
        params.addParameter("bank_name", bank_name);
        params.addParameter("per_code", per_code);
        params.addParameter("bank_num", bank_num);
        params.addParameter("bank_uname", bank_uname);
        params.addParameter("gpicture", gpicture);
        params.addParameter("gprice", gprice);
        params.addParameter("avgprice", avgprice);


        LogUtils.i("注册商家参数=" + params);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtils.d("注册商家返回=" + result);
                if (GetJsonRetcode.getRetcode(result) == 2000) {
                    callBack.onSucceed(GetJsonRetcode.getmsg(result));
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(result));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError(R.string.huoqushibai);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onError(R.string.huoqushibai);
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 获取店铺申请信息
     *
     * @param sid
     * @param callBack
     */
    public void getdata(final String sid, final AsyncCallBack callBack) {
        RequestParams params = new RequestParams(HttpUrl.STORSHENQINGINFO);
        params.addParameter("sid", sid);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtils.d("获取店铺申请信息参数sid=" + sid);
                LogUtils.d("获取店铺申请信息" + result);
                if (GetJsonRetcode.getRetcode(result) == 2000) {
                    callBack.onSucceed(new Gson().fromJson(result, ShenqingInfo.class));
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
