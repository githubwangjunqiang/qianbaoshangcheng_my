package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.yunyouzhiyuan.qianbaoshangcheng.fragment.BaseFragment;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;
    private List<String> list;
    public MyFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> list) {
        super(fm);
        this.fragments = fragments;
        this.list = list;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
