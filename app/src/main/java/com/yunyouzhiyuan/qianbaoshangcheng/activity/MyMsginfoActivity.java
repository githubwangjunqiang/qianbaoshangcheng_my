package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.MessageAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.MessageInfo;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.MessageModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.xlistview.XListView;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MyMsginfoActivity extends BaseActivity {
    @ViewInject(R.id.message_listview)
    private XListView listView;
    @ViewInject(R.id.top_tvtitle)
    private TextView tvtitle;
    @ViewInject(R.id.top_ivback)
    private ImageView ivback;
    private MessageModel model;
    private int page;
    private MessageAdapter adapter;
    private List<MessageInfo.DataBean> list = new ArrayList<>();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("page", page);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_msginfo);
        x.view().inject(this);
        if (savedInstanceState != null) {
            page = savedInstanceState.getInt("page", 0);
        }
        init();
        setListener();
        setAdapter();
        listView.startRefresh();
    }

    /**
     * 适配器
     */
    private void setAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new MessageAdapter(this, list);
            listView.setAdapter(adapter);
        }
    }

    /**
     * 设置监听器
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
     * @param isRefresh
     */
    private void getData(final boolean isRefresh) {
        if (isRefresh) {
            page = 0;
        }
        setCalls(model.store_message_list(SpService.getSP().getStorId(), page, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                if (isFinishing()) {
                    return;
                }
                if (isRefresh) {
                    list.clear();
                }
                list.addAll((List<MessageInfo.DataBean>) object);
                setAdapter();
                page++;
                stopListview(isRefresh);

            }

            @Override
            public void onError(Object object) {
                if (isFinishing()) {
                    return;
                }
                if (isRefresh) {
                    list.clear();
                    setAdapter();
                }
                stopListview(isRefresh);
            }

            @Override
            public void onFailure(String string) {
                if (isFinishing()) {
                    return;
                }
                if (isRefresh) {
                    list.clear();
                    setAdapter();
                }
                stopListview(isRefresh);
            }
        }));
    }

    /**
     * 停止刷新
     *
     * @param isRefresh
     */
    private void stopListview(boolean isRefresh) {
        if (isRefresh) {
            listView.stopRefresh(true);
        } else {
            listView.stopLoadMore();
        }
    }

    private void init() {
        tvtitle.setText(R.string.xitongtongzhi);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        model = new MessageModel();
    }

}
