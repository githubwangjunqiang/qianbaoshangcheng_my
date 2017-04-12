package com.yunyouzhiyuan.qianbaoshangcheng.uitl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class GetJsonRetcode {
    public static int getRetcode(String s) {
        int retcode = 0;
        try {
            JSONObject obj = new JSONObject(s);
            retcode = obj.getInt("retcode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retcode;
    }

    public static boolean getRetcodeCode(String s) {
        boolean returnCode = false;
        try {
            JSONObject obj = new JSONObject(s);
            int retcode = obj.getInt("retcode");
            returnCode = obj.getInt("retcode") == 2000;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnCode;
    }

    public static String getmsg(String s) {
        String msg = null;
        try {
            JSONObject obj = new JSONObject(s);
            msg = obj.getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return msg;
    }

    public static String getname(String name, String result) {
        String tr = null;
        try {
            JSONObject obj = new JSONObject(result);
            tr = obj.getString(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tr;
    }


}
