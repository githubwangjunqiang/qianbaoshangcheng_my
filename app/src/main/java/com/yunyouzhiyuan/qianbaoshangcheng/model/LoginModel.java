package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.yunyouzhiyuan.qianbaoshangcheng.MyApplication;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.User;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.logo;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class LoginModel extends IModel {
    /**
     * 获取验证码
     *
     * @param phonenum 手机号
     * @param callBack
     */
    public void loadCode(String phonenum, final AsyncCallBack callBack) {
        RequestParams params = new RequestParams(HttpUrl.CODE);
        params.addParameter("phonenum", phonenum);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Too.oo(result);
                if (GetJsonRetcode.getRetcode(result) == 2000) {
                    callBack.onSucceed(GetJsonRetcode.getmsg(result));
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(result));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError("网络错误，获取验证码失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onError("被取消，获取验证码失败");
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 获取验证码(忘记密码界面)
     *
     * @param phonenum 手机号
     * @param callBack
     */
    public void wangjimimaCode(String phonenum, final AsyncCallBack callBack) {
        RequestParams params = new RequestParams(HttpUrl.ADDCODE);
        params.addParameter("mobile", phonenum);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Too.oo(result);
                if (GetJsonRetcode.getRetcode(result) == 2000) {
                    callBack.onSucceed(GetJsonRetcode.getmsg(result));
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(result));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError("网络错误，获取验证码失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onError("被取消，获取验证码失败");
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 新用户注册
     *
     * @param mobile    手机号
     * @param password  密码
     * @param code      验证码
     * @param password2 确认密码
     * @param callBack
     */
    public void register(String mobile, String password, String password2, String code, final AsyncCallBack callBack) {
        RequestParams params = new RequestParams(HttpUrl.REGISTER);
        params.addParameter("mobile", mobile);
        params.addParameter("password", password);
        params.addParameter("password2", password2);
        params.addParameter("code", code);
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
                callBack.onError("网络错误，注册失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onError("被取消，注册失败");
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 用户登陆
     *
     * @param mobile   手机号
     * @param password 密码
     * @param callBack
     */
    public void login(String mobile, String password, String divtoken, final AsyncCallBack callBack) {
        RequestParams params = new RequestParams(HttpUrl.LOGIN);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("password", password);
        params.addBodyParameter("device_token", divtoken);




        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                logo.e(result);
                if (GetJsonRetcode.getRetcode(result) == 2000) {
                    try {//sid   sname  mobile
                        JSONObject object = new JSONObject(result);
                        JSONObject data = object.getJSONObject("data");
                        String sid = data.getString("sid");
                        String flag = data.getString("flag");
                        String stroName = data.getString("stroName");
                        String slogo = data.getString("slogo");
                        String addr = data.getString("addr");
                        callBack.onSucceed(new User(addr, slogo, stroName, sid, flag));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callBack.onError("网络错误，注册失败");
                    }
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(result));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError("网络错误，登陆失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onError("被取消，登陆失败");
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 忘记密码 重置密码
     *
     * @param mobile   手机号
     * @param password 密码
     * @param code     验证码
     * @param callBack
     */
    public void unpasword(String mobile, String password, String code, final AsyncCallBack callBack) {
        RequestParams params = new RequestParams(HttpUrl.UNPASSWORD);
        params.addParameter("mobile", mobile);
        params.addParameter("password", password);
        params.addParameter("code", code);
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
                callBack.onError("网络错误，重置失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onError("被取消，重置失败");
            }

            @Override
            public void onFinished() {

            }
        });
    }
}
