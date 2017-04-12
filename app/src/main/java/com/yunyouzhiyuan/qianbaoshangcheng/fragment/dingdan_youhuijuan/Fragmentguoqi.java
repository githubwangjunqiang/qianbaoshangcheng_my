package com.yunyouzhiyuan.qianbaoshangcheng.fragment.dingdan_youhuijuan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.DingdanYouhuijuanAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.DingDan;
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
public class Fragmentguoqi extends BaseFragment {
    private View view;
    @ViewInject(value = R.id.fragment_dingdan_xlistview)
    private XListView listView;
    private DingdanYouhuijuanAdapter adapter;
    private List<DingDan.DataBean> list = new ArrayList<>();
    private DingDanModel model;
    private int page = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_waimai_zuixin,null);
            x.view().inject(this,view);
            model = new DingDanModel();
            setadapter();
            setListener();
            setIsok(true);
            lazyLoad();
        }
        return view;
    }

    /**
     * 监听卡
     */
    private void setListener() {
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
    /**
     * 获取订单信息
     */
    private void getData(final boolean refresh) {
        page = refresh ? 0 : page;
        model.get_coupon_order(SpService.getSP().getStorId(), "2", page, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                if (refresh) {
                    list.clear();
                }
                list.addAll((List<DingDan.DataBean>) object);
                setadapter();
                hideReFresh(refresh);
                page++;
            }

            @Override
            public void onError(Object object) {
                if (refresh) {
                    list.clear();
                    setadapter();
                }
                Too.oo(object);
                hideReFresh(refresh);
            }

            @Override
            public void onFailure(String string) {
                Too.oo(string);
                hideReFresh(refresh);
            }
        });
    }
    /**
     * 关闭刷新布局
     *
     * @param refresh
     */
    private void hideReFresh(boolean refresh) {
        if (refresh) {
            listView.stopRefresh(true);
        } else {
            listView.stopLoadMore();
        }
    }
    /**
     * 适配器
     */
    private void setadapter() {
        if(adapter == null){
            adapter = new DingdanYouhuijuanAdapter(getActivity(),list);
            listView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void lazyLoad() {
        if(isok()){
            listView.startRefresh();
        }
    }
}
