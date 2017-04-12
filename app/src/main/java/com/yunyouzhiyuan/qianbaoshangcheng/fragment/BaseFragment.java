package com.yunyouzhiyuan.qianbaoshangcheng.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by wangjunqiang on 2016/11/22.
 */
public abstract class BaseFragment extends Fragment {

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;
    private boolean isok;
    private boolean isR;

    public boolean isR() {
        return isR;
    }

    public void setR(boolean r) {
        isR = r;
    }

    public boolean isok() {
        return isok;
    }

    public void setIsok(boolean isok) {
        this.isok = isok;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isok && isR) {
            lazyLoad();
        }
        setR(true);
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */

    protected abstract void lazyLoad();
}

