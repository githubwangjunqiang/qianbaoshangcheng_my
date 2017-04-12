package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangcheng.MyApplication;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.ActivityMainGvadapter;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HomeData;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.MainModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.MyGridView;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.Dialog_Dingdan;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.Dialog_Fabu;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    @ViewInject(value = R.id.activity_main_gv)
    private MyGridView gv;
    private ActivityMainGvadapter activityMainGvadapter;
    private Dialog_Fabu dialog_fabu;
    private Dialog_Dingdan dialog_dingdan;
    @ViewInject(value = R.id.activity_main_ivimage)
    private ImageView imageView;
    @ViewInject(value = R.id.activity_main_tvname)
    private TextView tvname;
    @ViewInject(value = R.id.activity_main_tvadress)
    private TextView tvadress;
    @ViewInject(value = R.id.mainlayout)
    private PullRefreshLayout layout;
    private MainModel model;
    private List<HomeData.DataBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        init();
        setadapter();
        setListener();
        getdata();
        MyApplication.setLogin(true);
    }


    /**
     * 获取店铺信息
     */
    private void getdata() {
        layout.setRefreshing(true);
        model.getStorData(SpService.getSP().getUid(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                list.clear();
                list.addAll((List<HomeData.DataBean>) object);

                if (!list.isEmpty()) {
                    x.image().bind(imageView, HttpUrl.IMAGE + list.get(0).getSlogo(),
                            new ImageOptions.Builder().setCircular(true).setFadeIn(true)
                                    .setFailureDrawableId(R.mipmap.t1).
                                    setLoadingDrawableId(R.mipmap.t1).build());
                    tvname.setText(list.get(0).getStroname() + "");
                    tvadress.setText(list.get(0).getXx_addr() + "");
                }
                hideLayout();

            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                hideLayout();
            }

            @Override
            public void onFailure(String string) {
                hideLayout();
            }
        });
    }

    private void hideLayout() {
        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (layout != null) {
                    layout.setRefreshing(false);
                }
            }
        }, 1500);
    }

    /**
     * 初始化
     */
    private void init() {
        model = new MainModel();
        dialog_fabu = new Dialog_Fabu(this, model, loadingDialog);
        dialog_dingdan = new Dialog_Dingdan(this);
        x.image().bind(imageView, HttpUrl.IMAGE + MyApplication.getUser().getSlogo(), new ImageOptions.Builder().setCircular(true).setFadeIn(true).setFailureDrawableId(R.mipmap.t1).setLoadingDrawableId(R.mipmap.t1).build());
        tvname.setText(MyApplication.getUser().getStroName());
        tvadress.setText(MyApplication.getUser().getAddr());
    }

    /**
     * 监听器
     */
    private void setListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://点击发布
                        dialog_fabu.show();
                        break;
                    case 1://点击我的订单
                        new Dialog_Dingdan(MainActivity.this).show();
                        break;
                    case 2: //点击消息
                        startActivity(new Intent(MainActivity.this, MyMesageActivity.class));
                        overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
                        break;
                    case 3: // 个人中心
                        startActivity(new Intent(MainActivity.this, MyActivity.class));
                        overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
                        break;
                    case 4:   //消费验证
                        startActivity(new Intent(MainActivity.this, XiaofeiYanzhengActivity.class));
                        overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
                        break;
                    case 5:    // 商品管理
                        startActivity(new Intent(MainActivity.this, ShoppingGuanliActivity.class));
                        overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
                        break;
                }

            }
        });
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();
            }
        });
    }

    /**
     * 适配器
     */
    private void setadapter() {
        activityMainGvadapter = new ActivityMainGvadapter(this);
        gv.setAdapter(activityMainGvadapter);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onDestroy() {
        MyApplication.setLogin(false);
        Process.killProcess(Process.myPid());
        System.exit(0);
        super.onDestroy();
    }

}
