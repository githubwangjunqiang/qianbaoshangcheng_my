package com.yunyouzhiyuan.qianbaoshangcheng.ui;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static List<Activity> getActivities() {
        return activities;
    }

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (activity!= null&&!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
