package com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.DialogBeijingAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.City;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.StorShenqingModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class Dialog_date_se_3 extends Dialog {
    private Activity context;
    private XListView xListView0;
    private XListView xListView1;
    private XListView xListView2;
    private List<City.DataBean> list;
    private List<City.DataBean> list1;
    private List<City.DataBean> list2;
    private CallBack callBack;
    private DialogBeijingAdapter adapter;
    private DialogBeijingAdapter adapter1;
    private DialogBeijingAdapter adapter2;
    private ProgressBar bar;
    private ImageView ivback;
    private StringBuffer stringBuffer = new StringBuffer();
    private StorShenqingModel model;
    private TextView tvtitle, tvok;
    private String province_id;
    private String city_id;
    private String district;

    public interface CallBack {
        void callBack(String name, String province_id, String city_id, String district);
    }

    public Dialog_date_se_3(Activity context, CallBack callBack) {
        super(context, R.style.WinDialog);
        this.context = context;
        this.callBack = callBack;
        this.list = new ArrayList<>();
        this.list1 = new ArrayList<>();
        this.list2 = new ArrayList<>();
        this.model = new StorShenqingModel();
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0x00000000));
        View dialogView = View.inflate(context, R.layout.dialog_date_se_3, null);
        window.setContentView(dialogView);
        WindowManager.LayoutParams wlp = window.getAttributes();
        //wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        setViews(dialogView);
        setadapter();
        setListener();
    }

    private void setadapter() {
        adapter = new DialogBeijingAdapter(context, list);
        adapter1 = new DialogBeijingAdapter(context, list1);
        adapter2 = new DialogBeijingAdapter(context, list2);
        xListView0.setAdapter(adapter);
        xListView1.setAdapter(adapter1);
        xListView2.setAdapter(adapter2);
    }

    private void setViews(View dialogView) {
        xListView0 = (XListView) dialogView.findViewById(R.id.dialog_date_se_x0);
        xListView1 = (XListView) dialogView.findViewById(R.id.dialog_date_se_x1);
        xListView2 = (XListView) dialogView.findViewById(R.id.dialog_date_se_x2);
        ivback = (ImageView) dialogView.findViewById(R.id.dialog_date_se_back);
        bar = (ProgressBar) dialogView.findViewById(R.id.dialog_data_se);
        tvok = (TextView) dialogView.findViewById(R.id.dialog_data_se_3_tvbtnok);
        tvtitle = (TextView) dialogView.findViewById(R.id.dialog_data_se_3_tvtitle);
        getdata(null, 1);
    }

    /**
     * 获取数据
     *
     * @param id
     */
    private void getdata(String id, final int one) {
        bar.setVisibility(View.VISIBLE);
        model.getCity(id, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                if (one == 1) {
                    list.clear();
                    list.addAll((List<City.DataBean>) object);
                    adapter.notifyDataSetChanged();
                } else if (one == 2) {
                    list1.clear();
                    list1.addAll((List<City.DataBean>) object);
                    adapter1.notifyDataSetChanged();
                    list2.clear();
                    adapter2.notifyDataSetChanged();
                } else if (one == 3) {
                    list2.clear();
                    list2.addAll((List<City.DataBean>) object);
                    adapter2.notifyDataSetChanged();
                }
                bar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                if (one == 2) {
                    list1.clear();
                    adapter1.notifyDataSetChanged();
                    list2.clear();
                    adapter2.notifyDataSetChanged();
                    b = null;
                    city_id = null;
                    c = null;
                    district = null;
                }
                if (one == 3) {
                    list2.clear();
                    adapter2.notifyDataSetChanged();
                    c = null;
                    district = null;
                }
                bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String string) {

            }
        });
    }


    private String a;
    private String b;
    private String c;

    private void setListener() {
        xListView0.setPullLoadEnable(false);
        xListView0.setPullRefreshEnable(false);
        xListView1.setPullLoadEnable(false);
        xListView1.setPullRefreshEnable(false);
        xListView2.setPullLoadEnable(false);
        xListView2.setPullRefreshEnable(false);
        xListView0.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getdata(list.get(position - 1).getRegion_id(), 2);
                a = list.get(position - 1).getRegion_name();
                province_id = list.get(position - 1).getRegion_id();
                tvtitle.setText(a);
            }
        });
        xListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getdata(list1.get(position - 1).getRegion_id(), 3);
                b = list1.get(position - 1).getRegion_name();
                city_id = list1.get(position - 1).getRegion_id();
                tvtitle.setText(a + " " + b);
            }
        });
        xListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                c = list2.get(position - 1).getRegion_name();
                district = list2.get(position - 1).getRegion_id();
                stringBuffer.delete(0, stringBuffer.length());
                stringBuffer.append(a + " " + b + " " + c);
                tvtitle.setText(stringBuffer.toString());
                callBack.callBack(stringBuffer.toString(), province_id, city_id, district);
                Dialog_date_se_3.this.dismiss();
            }
        });

        tvok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringBuffer.delete(0, stringBuffer.length());
                if (a != null) {
                    stringBuffer.append(a);
                }
                if (b != null) {
                    stringBuffer.append(" " + b);
                }
                if (c != null) {
                    stringBuffer.append(" " + c);
                }
                if (TextUtils.isEmpty(province_id)) {
                    Too.oo("请选择省份");
                    return;
                }
                if (TextUtils.isEmpty(city_id)) {
                    Too.oo("请选择市");
                    return;
                }
                if (TextUtils.isEmpty(district)) {
                    Too.oo("请选择区县，没有区县的身份暂不支持");
                    return;
                }


                callBack.callBack(stringBuffer.toString(), province_id, city_id, district);
                Dialog_date_se_3.this.dismiss();
            }
        });
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_date_se_3.this.dismiss();
            }
        });
    }

}
