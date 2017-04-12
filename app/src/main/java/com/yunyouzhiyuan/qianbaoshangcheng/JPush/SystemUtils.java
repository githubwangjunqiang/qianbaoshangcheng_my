package com.yunyouzhiyuan.qianbaoshangcheng.JPush;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/19.
 */
public class SystemUtils {
    public SystemUtils() {
    }

    /**
     *  应用进程是否存活
     * @param context
     * @param name
     * @return
     */
    public  boolean isAppRunning(Context context, String name) {
        if(TextUtils.isEmpty(name)) {
            return false;
        } else {
            ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            List infos = am.getRunningAppProcesses();
            if(infos == null) {
                return false;
            } else {
                Iterator i$ = infos.iterator();

                ActivityManager.RunningAppProcessInfo info;
                do {
                    if(!i$.hasNext()) {
                        return false;
                    }

                    info = (ActivityManager.RunningAppProcessInfo)i$.next();
                } while(!info.processName.equals(name));

                return true;
            }
        }
    }

    /**
     * 获取当前进程的名字
     * @param context
     * @return
     */
    public  String getCurrentProcessName(Context context) {
        int pid = Process.myPid();
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List infos = am.getRunningAppProcesses();
        if(infos == null) {
            return null;
        } else {
            Iterator i$ = infos.iterator();

            ActivityManager.RunningAppProcessInfo info;
            do {
                if(!i$.hasNext()) {
                    return null;
                }

                info = (ActivityManager.RunningAppProcessInfo)i$.next();
            } while(info.pid != pid);

            return info.processName;
        }
    }

    /**
     * 应用程序 是否在前台
     * @param context
     * @param packageName
     * @return
     */
    public  boolean isAppRunningOnTop(Context context, String packageName ) {
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List runningTaskInfo = activityManager.getRunningTasks(1);
        if(runningTaskInfo != null && runningTaskInfo.size() != 0) {
            String topAppPackageName = ((ActivityManager.RunningTaskInfo)runningTaskInfo.get(0)).topActivity.getPackageName();
            return !TextUtils.isEmpty(packageName) && packageName.equals(topAppPackageName);
        } else {
            return false;
        }
    }
}
