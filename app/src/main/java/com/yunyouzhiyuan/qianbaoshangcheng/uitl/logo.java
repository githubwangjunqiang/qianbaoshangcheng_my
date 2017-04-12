package com.yunyouzhiyuan.qianbaoshangcheng.uitl;

import android.util.Log;

import com.yunyouzhiyuan.qianbaoshangcheng.MyApplication;


/**
 * Created by wangjunqiang on 2016/11/13.
 */
public class logo {

    public static void e(String name){
        if(MyApplication.isLog()){
            Log.e("12345", "" +name);
        }
    }
}
