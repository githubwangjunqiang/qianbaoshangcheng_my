package com.yunyouzhiyuan.qianbaoshangcheng;

import android.app.Application;
import android.content.Context;

import com.yunyouzhiyuan.qianbaoshangcheng.entity.User;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by wangjunqiang on 2016/11/18.
 */
public class MyApplication extends Application {
    private static Context context;
    private static User user;//用户信息

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MyApplication.user = user;
    }

    private static boolean isLogin;

    public static boolean isLogin() {
        return isLogin;
    }

    public static void setLogin(boolean login) {
        isLogin = login;
    }

    /**
     * 是否输出日志
     */
    private static boolean isLog = true;


    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        x.Ext.init(this);
        JPushInterface.setDebugMode(true);//是否输出日志
        JPushInterface.init(this);//激光注册
    }

    public static boolean isLog() {
        return isLog;
    }

    public static void setLog(boolean log) {
        isLog = log;
    }
}
