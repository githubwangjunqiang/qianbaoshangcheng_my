package com.yunyouzhiyuan.qianbaoshangcheng.fragment.stor_jilu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunyouzhiyuan.qianbaoshangcheng.MyApplication;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.ActivityJiluListviewAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.Jilui;
import com.yunyouzhiyuan.qianbaoshangcheng.fragment.BaseFragment;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.ShenQingjiluModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.xlistview.XListView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class FragmentStorjilu3 extends BaseFragment {
    private View view;
    @ViewInject(value = R.id.fragment_shenqing_jilu_one_xlistview)
    private XListView listView;
    private ActivityJiluListviewAdapter adapter;
    private List<Jilui> list = new ArrayList<>();
    private ShenQingjiluModel model;
    private int status = 2;//未通过

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_shenqing_jilu_one, null);
            x.view().inject(this, view);
            init();
            setadapter();
            setListener();
            setIsok(true);
            lazyLoad();
        }
        return view;
    }

    /**
     * 初始化
     */
    private void init() {
        listView.setPullLoadEnable(false);
        model = new ShenQingjiluModel();
    }

    /**
     * 适配器
     */
    private void setadapter() {
        if(adapter == null){
            adapter = new ActivityJiluListviewAdapter(getActivity(),list);
            adapter.setStatus(status);
            listView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 监听器
     */
    private void setListener() {
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                getdata();
            }

            @Override
            public void onLoadMore() {
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
    private void getdata() {
        model.getjilu(MyApplication.getUser().getSid(), status, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                list.clear();
                list.add((Jilui) object);
                setadapter();
                listView.stopRefresh(true);
            }

            @Override
            public void onError(Object object) {
                list.clear();
                setadapter();
                listView.stopRefresh(false);
            }

            @Override
            public void onFailure(String string) {

            }
        });
    }
}
