package com.yunyouzhiyuan.qianbaoshangcheng.uitl;

import android.os.Environment;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class SDisTrue {
    /**
     * 判断SD卡是否可用
     */
    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
