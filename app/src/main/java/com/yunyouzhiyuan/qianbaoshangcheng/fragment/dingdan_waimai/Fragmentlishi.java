package com.yunyouzhiyuan.qianbaoshangcheng.fragment.dingdan_waimai;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.WaimaiDingdanAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.FoodDingdan;
import com.yunyouzhiyuan.qianbaoshangcheng.fragment.BaseFragment;
import com.yunyouzhiyuan.qianbaoshangcheng.model.DingDanModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.xlistview.XListView;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class Fragmentlishi extends BaseFragment {
    private View view;
    @ViewInject(value = R.id.fragment_dingdan_xlistview)
    private XListView listView;
    private WaimaiDingdanAdapter adapter;
    private List<FoodDingdan.DataBean> list;
    private DingDanModel model;
    private int page = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_waimai_zuixin, null);
            x.view().inject(this, view);
            init();
            setadapter();
            setIsok(true);
            lazyLoad();
        }
        return view;
    }

    /**
     * 初始化
     */
    private void init() {
        model = new DingDanModel();
        list = new ArrayList<>();
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                getData(true);
            }

            @Override
            public void onLoadMore() {
                getData(false);
            }
        });
    }

    @Override
    protected void lazyLoad() {
        if (isok()) {
            listView.startRefresh();
        }
    }

    /**
     * 获取信息
     */
    private void getData(final boolean isTop) {
        if (isTop) {
            page = 0;
        }
        model.gets_history_order(SpService.getSP().getStorId(), page, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                if (isTop) {
                    list.clear();
                }
                list.addAll((List<FoodDingdan.DataBean>) object);
                setadapter();
                page++;
                hideRelayout(isTop);
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                if (isTop) {
                    list.clear();
                    setadapter();
                }
                hideRelayout(isTop);
            }

            @Override
            public void onFailure(String string) {
                Too.oo(string);
                if (isTop) {
                    list.clear();
                    setadapter();
                }
                hideRelayout(isTop);
            }
        });
    }

    /**
     * 适配器
     */
    private void setadapter() {
        if (adapter == null) {
            adapter = new WaimaiDingdanAdapter(getActivity(), list);
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 关闭刷新布局
     *
     * @param isTop
     */
    private void hideRelayout(boolean isTop) {
        if (isTop) {
            listView.stopRefresh(true);
        } else {
            listView.stopLoadMore();
        }
    }
}
