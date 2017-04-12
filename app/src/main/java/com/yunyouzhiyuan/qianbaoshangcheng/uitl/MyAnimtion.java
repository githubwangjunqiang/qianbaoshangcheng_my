package com.yunyouzhiyuan.qianbaoshangcheng.uitl;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by wangjunqiang on 2016/10/24.
 */
public class MyAnimtion {

    /**
     * 抖动
     *
     * @param views
     * @return
     */
    public static ObjectAnimator getAnimator_DX(View views) {
        float xx = views.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(views, "translationX", xx, -10f, 10f, -20f, 20f, -10f, 10f, -5f, xx);
        animator.setDuration(400);
        return animator;


    }
}
