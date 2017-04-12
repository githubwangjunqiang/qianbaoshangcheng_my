package com.yunyouzhiyuan.qianbaoshangcheng.uitl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.yunyouzhiyuan.qianbaoshangcheng.MyApplication;


public class SpService {
    private Context context;
    private static SpService sp;

    private SpService(Context context) {
        this.context = context;
    }

    public static SpService getSP() {
        if (sp == null) {
            sp = new SpService(MyApplication.getContext());
            return sp;
        } else {
            return sp;
        }
    }

    /**
     * 存入登陆后 返回的信息
     *
     * @param uid
     * @param phone
     * @param pas
     * @return
     */
    public boolean saveUid(String uid, String phone, String pas) {
        SharedPreferences preferences = context.getSharedPreferences("qianbao_app", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("uid", uid);
        editor.putString("phone", phone);
        editor.putString("pas", pas);
        return editor.commit();
    }

    /**
     * 写入店铺id
     * @param storId
     * @return
     */
    public boolean saveStorId(String storId) {
        SharedPreferences preferences = context.getSharedPreferences("qianbao_app", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("storId", storId);
        return editor.commit();
    }
    /**
     * 写入店铺类型id
     * @param scid
     * @return
     */
    public boolean saveScid(String scid) {
        SharedPreferences preferences = context.getSharedPreferences("qianbao_app", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("scid", scid);
        return editor.commit();
    }

    /**
     * 写入位置信息
     * @param lat
     * @param log
     * @return
     */
    public boolean saveLocation(String lat,String log) {
        SharedPreferences preferences = context.getSharedPreferences("qianbao_app", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("lat", lat);
        editor.putString("log", log);
        return editor.commit();
    }

    /**
     * 是否第一次使用
     *
     * @param isFist true是第一次使用  false 不是第一次
     * @return
     */
    public boolean saveAILI(boolean isFist) {
        SharedPreferences preferences = context.getSharedPreferences("qianbao_app", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean("isFist", isFist);
        return editor.commit();
    }

    /**
     * 单独写入密码
     *
     * @param pas
     * @return
     */
    public boolean savepass(String pas) {
        SharedPreferences preferences = context.getSharedPreferences("qianbao_app", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("pas", pas);
        return editor.commit();
    }

    /**
     * 获取uid
     *
     * @return
     */
    public String getUid() {
        return getStringName("uid", "qianbao_app");
    }
    /**
     * 获取经营类型scid
     *
     * @return
     */
    public String getScid() {
        return getStringName("scid", "qianbao_app");
    }

    /**
     * 获取 维度
     *
     * @return
     */
    public String getLat() {
        return getStringName("lat", "qianbao_app");
    }
    /**
     * 获取 经度
     *
     * @return
     */
    public String getLng() {
        return getStringName("log", "qianbao_app");
    }

    /**
     * 获取店铺id
     *
     * @return
     */
    public String getStorId() {
        return getStringName("storId", "qianbao_app");
    }

    /**
     * 获取是否第一次登陆 保存的信息
     * @return
     */
    public boolean isFist() {
        SharedPreferences preferences = context.getSharedPreferences("qianbao_app", Context.MODE_PRIVATE);
        return preferences.getBoolean("isFist", true);
    }

    /**
     * 获取密码
     *
     * @return
     */
    public String getpas() {
        return getStringName("pas", "qianbao_app");
    }

    /**
     * 获取电话号码
     */
    public String getphone() {
        return getStringName("phone", "qianbao_app");
    }

    /**
     * 获取融云token
     */
    public String getR_Token() {
        return getStringName("r_token", "qianbao_app");
    }

    /**
     * 根据名称 获取值
     *
     * @param name
     * @param content
     * @return
     */
    private String getStringName(String name, String content) {
        SharedPreferences preferences = context.getSharedPreferences(content, Context.MODE_PRIVATE);
        String string = preferences.getString(name, "");
        return string;
    }

    /**
     * clear 存入的 user信息
     */
    public void clearUser() {
        SharedPreferences preferences = context.getSharedPreferences("qianbao_app", Context.MODE_PRIVATE);
        preferences.edit().clear();
    }

}
