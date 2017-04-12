package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.SpannerAdapterCuxiao;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HuoDongTuangou;
import com.yunyouzhiyuan.qianbaoshangcheng.model.HuoDongModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.MyListview;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HuoDongCuXiaoActivity extends BaseActivity {

    @Bind(R.id.top_tvtitle)
    TextView topTvtitle;
    @Bind(R.id.cuxiao_etname)
    EditText cuxiaoEtname;
    @Bind(R.id.cuxiao_leixingsp)
    Spinner cuxiaoLeixingsp;
    @Bind(R.id.cuxiao_tvtype)
    TextView cuxiaoTvtype;
    @Bind(R.id.cuxiao_etprice)
    EditText cuxiaoEtprice;
    @Bind(R.id.cuxiao_layout)
    PullRefreshLayout cuxiaoLayout;
    @Bind(R.id.cuxiao_listview)
    MyListview listview;
    @Bind(R.id.cuxiao_starttime)
    TextView cuxiaoStarttime;
    @Bind(R.id.cuxiao_startlasttime)
    TextView cuxiaoStartlasttime;
    //新用户立减 传1  折扣 传0
    private int type = 1;

    private HuoDongModel model;
    private List<HuoDongTuangou.DataBean> list = new ArrayList<>();
    private SpannerAdapterCuxiao tuanGouAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabucuxiao);
        ButterKnife.bind(this);
        init();
        setListener();
    }

    /**
     * 监听
     */
    private void setListener() {
        cuxiaoLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cuxiaoLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cuxiaoLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void init() {
        topTvtitle.setText("发布活动");
        model = new HuoDongModel();
        getData();
        setSp0();
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        currentInMillis = calendar.getTimeInMillis();
    }

    /**
     * 获取商品
     */
    private void getData() {
        cuxiaoLayout.setRefreshing(true);
        model.getData(SpService.getSP().getStorId(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                list.clear();
                list.addAll((List<HuoDongTuangou.DataBean>) object);
                setAdapter();
                cuxiaoLayout.setRefreshing(false);
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                cuxiaoLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String string) {
                Too.oo(string);
                cuxiaoLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 设置listview适配器  商品适配器
     */
    private void setAdapter() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            strings.add(list.get(i).getGoods_name());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_checked, strings);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Too.oo(listview.getCheckedItemPositions().get(position) + "position=" + position);
            }
        });
    }

    /**
     * 设置第一个商品分类
     */
    private void setSp0() {
        List<String> strings = Arrays.asList("新用户立减", "折扣");
        tuanGouAdapter = new SpannerAdapterCuxiao(this, strings);
        cuxiaoLeixingsp.setAdapter(tuanGouAdapter);
        cuxiaoLeixingsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://新用户立减
                        cuxiaoTvtype.setText("立减金额");
                        cuxiaoEtprice.setHint("请输入立减金额");
                        type = 1;
                        break;
                    case 1://折扣
                        cuxiaoTvtype.setText("折扣值");
                        cuxiaoEtprice.setHint("请输入折扣值1～100，例 9折输入90");
                        type = 0;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Too.oo("onNothingSelected");
            }
        });
    }

    @OnClick({R.id.top_ivback, R.id.cuxiao_starttime, R.id.cuxiao_startlasttime, R.id.cuxiao_btnok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_ivback://返回
                finish();
                break;
            case R.id.cuxiao_starttime://开始时间
                showTimeDialog((TextView) view, true);
                break;
            case R.id.cuxiao_startlasttime://结束时间
                showTimeDialog((TextView) view, false);
                break;
            case R.id.cuxiao_btnok://提交
                sunbMit();
                break;
        }
    }

    /**
     * 提交
     */
    private void sunbMit() {
        SparseBooleanArray checkedItemPositions = listview.getCheckedItemPositions();
        if (checkedItemPositions == null && checkedItemPositions.size() < 0) {
            Too.oo("请选择商品");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < checkedItemPositions.size(); i++) {

            if (checkedItemPositions.get(i) && i == checkedItemPositions.size() - 1) {
                buffer.append(list.get(i).getGoods_id());
            } else if (checkedItemPositions.get(i)) {
                buffer.append(list.get(i).getGoods_id()).append(",");
            }
        }
        if(buffer.length()<1){
            Too.oo("请选择商品");
            return;
        }



        if (year == 0 || yearend == 0) {
            Too.oo("请完善时间");
            return;
        }
        String name = cuxiaoEtname.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Too.oo("请填写地址");
            return;
        }
        String price = cuxiaoEtprice.getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            Too.oo("请填写活动优惠价格或者折扣值");
            return;
        }

        loodingShow();
        model.publish_discount_Goods(SpService.getSP().getStorId(), buffer.toString(), name, price,
                year + "-" + (month + 1) + "-" + dayOfMonth, yearend + "-" + (monthend + 1) + "-" +
                        dayOfMonthend, type, new IModel.AsyncCallBack() {
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

    private int year, month, dayOfMonth;
    private int yearend, monthend, dayOfMonthend;
    private int currentYear, currentMonth, currentDayOfMonth;
    private long currentInMillis;

    /**
     * 显示时间选择对话框
     */
    private void showTimeDialog(final TextView tv, final boolean isStartTime) {

        if (isStartTime) {
            if (year == 0) {
                year = currentYear;
                month = currentMonth;
                dayOfMonth = currentDayOfMonth;
            }
        } else {
            if (yearend == 0) {
                yearend = (year == 0 ? currentYear : year);
                monthend = (month == 0 ? currentMonth : month);
                dayOfMonthend = (dayOfMonth == 0 ? currentDayOfMonth : dayOfMonth);
            }
        }
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, null, isStartTime ? year : yearend, isStartTime ? month : monthend, isStartTime ? dayOfMonth : dayOfMonthend);
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatePicker datePicker = datePickerDialog.getDatePicker();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, datePicker.getYear());
                calendar.set(Calendar.MONTH, datePicker.getMonth());
                calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                if (currentInMillis > calendar.getTimeInMillis()) {
                    Too.oo("请不要选择已经过去的日期");
                    return;
                }
                if (isStartTime) {
                    if (yearend > 0) {
                        Calendar calendarend = Calendar.getInstance();
                        calendarend.set(Calendar.YEAR, yearend);
                        calendarend.set(Calendar.MONTH, monthend);
                        calendarend.set(Calendar.DAY_OF_MONTH, dayOfMonthend);
                        if (calendar.getTimeInMillis() >= calendarend.getTimeInMillis()) {
                            Too.oo("请不要选择超过结束时间的日期");
                            year = 0;
                            month = 0;
                            dayOfMonth = 0;
                            cuxiaoStarttime.setText("请选择时间");
                            return;
                        }
                    }
                } else {
                    if (year > 0) {
                        Calendar calendarstart = Calendar.getInstance();
                        calendarstart.set(Calendar.YEAR, year);
                        calendarstart.set(Calendar.MONTH, month);
                        calendarstart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        if (calendar.getTimeInMillis() <= calendarstart.getTimeInMillis()) {
                            Too.oo("请不要选择开始时间以前的日期");
                            yearend = 0;
                            monthend = 0;
                            dayOfMonthend = 0;
                            cuxiaoStartlasttime.setText("请选择时间");
                            return;
                        }
                    }
                }

                if (isStartTime) {
                    year = datePicker.getYear();
                    month = datePicker.getMonth();
                    dayOfMonth = datePicker.getDayOfMonth();
                    tv.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                } else {
                    yearend = datePicker.getYear();
                    monthend = datePicker.getMonth();
                    dayOfMonthend = datePicker.getDayOfMonth();
                    tv.setText(yearend + "-" + (monthend + 1) + "-" + dayOfMonthend);
                }
                dialog.dismiss();
            }
        });
        datePickerDialog.show();
    }
}
