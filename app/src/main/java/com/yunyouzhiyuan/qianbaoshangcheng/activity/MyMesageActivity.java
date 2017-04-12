package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.MyMessgareListvewAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.xlistview.XListView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MyMesageActivity extends BaseActivity {
    @ViewInject(value = R.id.activity_my_message_listview)
    private XListView listView;
    @ViewInject(R.id.top_tvtitle)
    private TextView tvtitle;
    @ViewInject(R.id.top_ivback)
    private ImageView ivback;
    private MyMessgareListvewAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mesage);
        x.view().inject(this);
        init();
        setadapter();
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                listView.stopRefresh(true);
            }

            @Override
            public void onLoadMore() {
                listView.stopLoadMore();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MyMesageActivity.this,MyMsginfoActivity.class));

            }
        });
    }

    /**
     * 适配器
     */
    private void setadapter() {
        if (adapter == null) {
            list.add("");
            list.add("");
            list.add("");
            list.add("");
            list.add("");
            list.add("");
            adapter = new MyMessgareListvewAdapter(this,list);
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化
     */
    private void init() {
        tvtitle.setText(R.string.wodexiaoxi);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
        }
        return super.onKeyDown(keyCode, event);
    }
}
