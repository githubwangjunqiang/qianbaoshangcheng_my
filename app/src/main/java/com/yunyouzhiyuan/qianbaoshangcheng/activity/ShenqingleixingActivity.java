package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.ShenqingLeixingAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.JingYingLeixing;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.LeixingModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShenqingleixingActivity extends BaseActivity {

    @Bind(R.id.top_tvtitle)
    TextView topTvtitle;
    @Bind(R.id.sheqing_listview)
    ListView listView;
    @Bind(R.id.sheqing_listview1)
    ListView listView1;
    @Bind(R.id.sheqing_listview2)
    ListView listView2;
    private ShenqingLeixingAdapter adapter, adapter1, adapter2;
    private LeixingModel model;
    private List<JingYingLeixing.DataBean> list = new ArrayList<>();
    private List<JingYingLeixing.DataBean> list1 = new ArrayList<>();
    private List<JingYingLeixing.DataBean> list2 = new ArrayList<>();
    private String class1, class2, class3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shenqingleixing);
        ButterKnife.bind(this);
        init();
        getDate(0, "0");
        setListener();
    }

    /**
     * 初始化
     */
    private void init() {
        topTvtitle.setText("选择经营类型");
        model = new LeixingModel();
    }

    /**
     * 设置适配器1
     */
    private void setAdapter1() {
        if (adapter1 != null) {
            adapter1.notifyDataSetChanged();
        } else {
            if (!list1.isEmpty()) {
                list1.get(0).setChecked(true);
                class2 = list1.get(0).getId();
            }
            adapter1 = new ShenqingLeixingAdapter(this, list1);
            listView1.setAdapter(adapter1);
        }
    }

    /**
     * 设置适配器2
     */
    private void setAdapter2() {
        if (adapter2 != null) {
            adapter2.notifyDataSetChanged();
        } else {
            if (!list2.isEmpty()) {
                list2.get(0).setChecked(true);
                class3 = list2.get(0).getId();
            }
            adapter2 = new ShenqingLeixingAdapter(this, list2);
            listView2.setAdapter(adapter2);
        }
    }

    /**
     * 获取信息
     */
    private void getDate(final int position, String id) {
        loodingShow();
        model.getData(SpService.getSP().getStorId(), id, SpService.getSP().getScid(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                switch (position) {
                    case 0://第一级
                        list.clear();
                        list.addAll((List<JingYingLeixing.DataBean>) object);
                        setAdapter();
                        if (!list.isEmpty()) {
                            getDate(1, list.get(0).getId());
                        }
                        break;
                    case 1://第二级
                        list1.clear();
                        list1.addAll((List<JingYingLeixing.DataBean>) object);
                        setAdapter1();
                        if (!list1.isEmpty()) {
                            getDate(2, list1.get(0).getId());
                        }
                        break;
                    case 2://第三极
                        list2.clear();
                        list2.addAll((List<JingYingLeixing.DataBean>) object);
                        setAdapter2();
                        break;
                }

                dismissLooding();
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                switch (position) {
                    case 0://第一级
                        list.clear();
                        list1.clear();
                        list2.clear();
                        setAdapter();
                        setAdapter1();
                        setAdapter2();
                        class1 = null;
                        class2 = null;
                        class3 = null;
                        break;
                    case 1://第二级
                        list1.clear();
                        list2.clear();
                        setAdapter1();
                        setAdapter2();
                        class2 = null;
                        class3 = null;
                        break;
                    case 2://第三极
                        list2.clear();
                        class3 = null;
                        setAdapter2();
                        break;
                }
                dismissLooding();
            }

            @Override
            public void onFailure(String string) {

            }
        });
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            if (!list.isEmpty()) {
                list.get(0).setChecked(true);
                class1 = list.get(0).getId();
            }
            adapter = new ShenqingLeixingAdapter(this, list);
            listView.setAdapter(adapter);
        }
    }

    /**
     * 监听器
     */
    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (JingYingLeixing.DataBean data : list) {
                    data.setChecked(false);
                }
                list.get(position).setChecked(true);
                class1 = list.get(position).getId();
                class2 = null;
                class3 = null;
                setAdapter();
                getDate(1, list.get(position).getId());
            }
        });
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (JingYingLeixing.DataBean data : list1) {
                    data.setChecked(false);
                }
                list1.get(position).setChecked(true);
                class2 = list1.get(position).getId();
                class3 = null;
                setAdapter1();
                getDate(2, list1.get(position).getId());
            }
        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (JingYingLeixing.DataBean data : list2) {
                    data.setChecked(false);
                }
                list2.get(position).setChecked(true);
                class3 = list2.get(position).getId();
                setAdapter2();
                Too.oo("您选择了：" + list2.get(position).getMobile_name());
            }
        });
    }

    @OnClick({R.id.top_ivback, R.id.sheqing_btnok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_ivback://返回
                finish();
                break;
            case R.id.sheqing_btnok://确定
                toTiJiao();
                break;
        }
    }

    /**
     * 提交
     */
    private void toTiJiao() {
        if (TextUtils.isEmpty(class1)) {
            Too.oo("请选择一级分类");
            return;
        }
        if (TextUtils.isEmpty(class2)) {
            Too.oo("请选择二级分类");
            return;
        }
        if (TextUtils.isEmpty(class3)) {
            Too.oo("请选择三级分类");
            return;
        }

        loodingShow();
        model.toOKToijiao(SpService.getSP().getStorId(), class1, class2, class3, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                Too.oo(object);
                dismissLooding();
                finish();
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                dismissLooding();
            }

            @Override
            public void onFailure(String string) {

            }
        });
    }


}
