package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.PingJiaAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.Pingjia;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.PingjiaModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.SquareImageView;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PingjiaActivity extends BaseActivity {

    @Bind(R.id.top_tvtitle)
    TextView topTvtitle;
    @Bind(R.id.pingjia_ivimage)
    SquareImageView pingjiaIvimage;
    @Bind(R.id.pingjia_tvname)
    TextView pingjiaTvname;
    @Bind(R.id.pingjia_tvtime)
    TextView pingjiaTvtime;
    @Bind(R.id.pingjia_tvcontent)
    TextView pingjiaTvcontent;
    @Bind(R.id.pingjia_gv)
    GridView pingjiaGv;
    @Bind(R.id.pingjia_layout)
    PullRefreshLayout pingjiaLayout;
    private PingjiaModel model;
    private PingJiaAdapter adapter;
    private Pingjia pingjia;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingjia);
        ButterKnife.bind(this);
        if (TextUtils.isEmpty(getIntent().getStringExtra("orderid"))) {
            Too.oo("订单id 为空");
            finish();
            return;
        }
        init();
        getData();
    }

    public static void startPingjiaActivity(Context context, String orderid) {
        Intent intent = new Intent(context, PingjiaActivity.class);
        intent.putExtra("orderid", orderid);
        context.startActivity(intent);
    }

    /**
     * 获取信息
     */
    private void getData() {
        pingjiaLayout.setRefreshing(true);
        model.get_comment_by_orderid(getIntent().getStringExtra("orderid"), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                pingjia = (Pingjia) object;
                setadapter();
                pingjiaLayout.setRefreshing(false);
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                pingjiaLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String string) {
                Too.oo(string);
                pingjiaLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 设置适配器
     */
    private void setadapter() {
        x.image().bind(pingjiaIvimage, HttpUrl.IMAGE + pingjia.getData().getHead_pic(),
                new ImageOptions.Builder().setCircular(true).setFailureDrawableId(R.mipmap.t1).build());
        pingjiaTvname.setText(pingjia.getData().getNickname() + "");
        pingjiaTvtime.setText(pingjia.getData().getAdd_time() + "");
        pingjiaTvcontent.setText(pingjia.getData().getContent() + "");
        list.clear();
        list.addAll(pingjia.getData().getImg());
        if (adapter == null) {
            adapter = new PingJiaAdapter(this, list);
            pingjiaGv.setAdapter(adapter);
            pingjiaGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TapViewpagerActivity.startActivity(PingjiaActivity.this, list, position);
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化
     */
    private void init() {
        topTvtitle.setText("我的订单评价");
        model = new PingjiaModel();
        pingjiaLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    @OnClick(R.id.top_ivback)
    public void onClick() {
        finish();
    }
}
