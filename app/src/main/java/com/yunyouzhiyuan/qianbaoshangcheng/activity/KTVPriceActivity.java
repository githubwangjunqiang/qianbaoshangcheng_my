package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.KTVPriceAdaptet;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.KTVPrice;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.SubMitKTV;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.KTVModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.ScrollExpandableListView;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class KTVPriceActivity extends BaseActivity {

    @Bind(R.id.top_tvtitle)
    TextView topTvtitle;
    @Bind(R.id.ktvprice_listview)
    ScrollExpandableListView ktvpriceListview;
    private KTVPriceAdaptet adaptet;
    private KTVModel model;
    private List<KTVPrice.DataBean> list = new ArrayList<>();
    private String goodsid;
    private LoadingDialog loadingDialog1;

    /**
     * 启动activity
     */
    public static void startPriceActivity(Context context, List<KTVPrice.DataBean> list,
                                          String number, String price, String id) {
        Intent intent = new Intent(context, KTVPriceActivity.class);
        intent.putExtra("list", (Serializable) list);
        intent.putExtra("number", number);
        intent.putExtra("price", price);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ktvprice);
        ButterKnife.bind(this);
        if (getIntent().getSerializableExtra("list") == null) {
            Too.oo("本地传参有误");
            finish();
            return;
        }
        list = (List<KTVPrice.DataBean>) getIntent().getSerializableExtra("list");
        number = getIntent().getStringExtra("number");
        price = getIntent().getStringExtra("price");
        goodsid = getIntent().getStringExtra("id");
        if (TextUtils.isEmpty(number) || TextUtils.isEmpty(price)) {
            Too.oo("传参有误");
            finish();
            return;
        }
        loadingDialog1 = new LoadingDialog(this);
        init();
        loadingDialog1.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (myTast != null && !myTast.isCancelled()) {
                        myTast.cancel(false);
                    }
                    if (call != null && !call.isCanceled()) {
                        call.cancel();
                    }
                    myTast = null;
                    call = null;
                }

                return false;
            }
        });
    }

    private String number, price;


    /**
     * 初始化
     */
    private void init() {
        model = new KTVModel();
        topTvtitle.setText("请查看和修改每个时间段的价格/库存");
        loadingDialog1.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < list.get(i).getHouse().size(); j++) {
                        for (int k = 0; k < list.get(i).getHouse().get(j).getTime().size(); k++) {
                            list.get(i).getHouse().get(j).getTime().get(k).setPrice(price);
                            list.get(i).getHouse().get(j).getTime().get(k).setNumber(number);
                        }
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adaptet = new KTVPriceAdaptet(list, KTVPriceActivity.this);
                        ktvpriceListview.setAdapter(adaptet);
                        for (int i = 0; i < list.size(); i++) {
                            ktvpriceListview.expandGroup(i);
                        }
                        loadingDialog1.dismiss();
                    }
                });

            }
        }.start();
    }

    @OnClick(R.id.top_ivback)//返回按钮
    public void onClick() {
        if (!isSuccess) {
            toSubMit();
            return;
        }
        finish();
    }

    /**
     * 点击提交
     *
     * @param view
     */
    public void subMit(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("黔宝商城提醒您");
        builder.setMessage("请您务必查看修改完毕，保证每个商品的价格，确认提交？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toSubMit();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 上传网络提交数据
     */
    private void toSubMit() {
        if (list == null || list.isEmpty()) {
            Too.oo("数据异常");
            return;
        }
        loadingDialog1.show();
        myTast = new MyTast();
        myTast.execute();
    }

    private boolean isSuccess = false;

    @Override
    public void onBackPressed() {
        if (!isSuccess) {
            toSubMit();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (myTast != null && !myTast.isCancelled()) {
            myTast.cancel(false);
        }
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
        myTast = null;
        call = null;
        super.onDestroy();
    }

    private MyTast myTast;
    private Call call;

    private class MyTast extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            SubMitKTV ktv = new SubMitKTV();
            ktv.setGoods_id(goodsid);
            List<SubMitKTV.Info> infoList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                int weekid = list.get(i).getId();
                for (int j = 0; j < list.get(i).getHouse().size(); j++) {
                    int houseid = list.get(i).getHouse().get(j).getId();
                    for (int k = 0; k < list.get(i).getHouse().get(j).getTime().size(); k++) {
                        StringBuffer buffer = new StringBuffer();
                        SubMitKTV.Info info = new SubMitKTV.Info();
                        int id = list.get(i).getHouse().get(j).getTime().get(k).getId();
                        buffer.append(houseid).append("_").append(id).append("_").append(weekid);
                        info.setKey(buffer.toString());
                        String price = list.get(i).getHouse().get(j).getTime().get(k).getPrice();
                        info.setPrice(TextUtils.isEmpty(price) ? getIntent().getStringExtra("price") : price);
                        String number = list.get(i).getHouse().get(j).getTime().get(k).getNumber();
                        info.setStore_count(TextUtils.isEmpty(number) ? getIntent().getStringExtra("number") : number);
                        infoList.add(info);
                    }
                }
            }
            ktv.setInfo(infoList);
            String s = new Gson().toJson(ktv);
            LogUtils.d(s);
            if (s != null && s.length() > 0) {
                return s;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String aBoolean) {
            super.onPostExecute(aBoolean);
            if (!TextUtils.isEmpty(aBoolean)) {
                call = model.eidt_addgoods_info(aBoolean, new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object object) {
                        isSuccess = true;
                        AlertDialog.Builder builder = new AlertDialog.Builder(KTVPriceActivity.this);

                        builder.setMessage((String) object);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    finish();
                                }
                                return false;
                            }
                        });
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                        loadingDialog1.dismiss();
                    }

                    @Override
                    public void onError(Object object) {
                        Too.oo(object);
                        loadingDialog1.dismiss();
                    }

                    @Override
                    public void onFailure(String string) {
                        Too.oo(string);
                        loadingDialog1.dismiss();
                    }
                });
            } else {
                Too.oo("计算失败");
                loadingDialog1.dismiss();
            }
        }
    }
}
