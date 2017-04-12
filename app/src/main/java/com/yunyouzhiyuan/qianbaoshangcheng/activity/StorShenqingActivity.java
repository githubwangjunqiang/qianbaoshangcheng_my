package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.StorShenGvAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.Buzhou;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.StorShenqingModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class StorShenqingActivity extends BaseActivity {
    @ViewInject(value = R.id.top_ivback)
    private ImageView ivback;
    @ViewInject(value = R.id.top_tvtitle)
    private TextView tvtitle;
    @ViewInject(value = R.id.activity_shenqing_stor_tvbtn)
    private TextView tvbtnok;
    @ViewInject(value = R.id.top_tvtitle_ri)
    private TextView tvtitleri;
    @ViewInject(value = R.id.activity_stor_shenqing_gv)
    private GridView gv;
    @ViewInject(value = R.id.swipeRefreshLayout)
    private PullRefreshLayout layout;
    private StorShenGvAdapter adapter;
    public static StorShenqingActivity instanse;
    private StorShenqingModel model;
    private List<Buzhou.DataBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stor_shenqing);
        x.view().inject(this);
        instanse = this;
        init();
        setListener();
        setadapter();
        layout.setRefreshing(true);
    }

    /**
     * 适配器
     */
    private void setadapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new StorShenGvAdapter(this, list);
            gv.setAdapter(adapter);
        }
    }

    /**
     * 监听器
     */
    private void setListener() {
        tvtitleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backOut();
            }
        });
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
              getdata();
            }
        });

        tvbtnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StorShenqingActivity.this, StorShenqinginfoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
            }
        });
    }

    /**
     * 获取信息
     */
    private void getdata() {
        model.getshenqingbuzhou(new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                list.clear();
                list.addAll((List<Buzhou.DataBean>) object);
                setadapter();
                layout.setRefreshing(false);
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                layout.setRefreshing(false);
            }

            @Override
            public void onFailure(String string) {

            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        tvtitle.setText(R.string.stor_shenqing_title);
        ivback.setVisibility(View.GONE);
        tvtitleri.setText(R.string.top_back);
        model = new StorShenqingModel();
        getdata();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backOut();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出应用
     */
    private void backOut() {
        finish();
        overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
    }
}
