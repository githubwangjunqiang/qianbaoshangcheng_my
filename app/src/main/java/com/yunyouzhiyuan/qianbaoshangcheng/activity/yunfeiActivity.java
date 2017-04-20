package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.Freight;
import com.yunyouzhiyuan.qianbaoshangcheng.model.FreightModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class yunfeiActivity extends BaseActivity {

    @Bind(R.id.top_tvtitle)
    TextView topTvtitle;
    @Bind(R.id.yunfei_etprice)
    EditText yunfeiEtprice;
    @Bind(R.id.yunfei_etqishiprice)
    EditText yunfeiEtqishiprice;
    @Bind(R.id.freight_layout)
    PullRefreshLayout freightLayout;
    private FreightModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yunfei);
        ButterKnife.bind(this);
        init();
        setListener();
        freightLayout.setRefreshing(true);
        getData();
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        freightLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    /**
     * 获取运费信息
     */
    private void getData() {
        model.get_send_price(SpService.getSP().getStorId(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                try {
                    List<Freight.DataBean> object1 = (List<Freight.DataBean>) object;
                    if (Double.parseDouble(object1.get(0).getSend_price()) > 0) {
                        yunfeiEtprice.setText(object1.get(0).getSend_price());
                        yunfeiEtprice.setSelection(object1.get(0).getSend_price().length());
                    }
                    if (Double.parseDouble(object1.get(0).getShipping_price()) > 0) {
                        yunfeiEtqishiprice.setText(object1.get(0).getShipping_price());
                        yunfeiEtqishiprice.setSelection(object1.get(0).getShipping_price().length());
                    }

                } catch (RuntimeException e) {
                    e.printStackTrace();
                }

                freightLayout.setRefreshing(false);
            }

            @Override
            public void onError(Object object) {
                freightLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String string) {
                freightLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 初始化数据操作
     */
    private void init() {
        topTvtitle.setText("设置运费");
        model = new FreightModel();
    }

    @OnClick(R.id.top_ivback)
    public void onClick() {
        finish();
    }

    /**
     * 提交
     */
    public void subMit(View view) {
        String price = yunfeiEtprice.getText().toString().trim();
        String startingPrice = yunfeiEtqishiprice.getText().toString().trim();

        if (TextUtils.isEmpty(price)) {
            Too.oo("请填写配送价格");
            return;
        }
        loodingShow();
        model.edit_send_price(SpService.getSP().getStorId(), price, startingPrice, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                Too.oo(object);
                finish();
                dismissLooding();
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                dismissLooding();
            }

            @Override
            public void onFailure(String string) {
                Too.oo(string);
                dismissLooding();
            }
        });

    }
}
