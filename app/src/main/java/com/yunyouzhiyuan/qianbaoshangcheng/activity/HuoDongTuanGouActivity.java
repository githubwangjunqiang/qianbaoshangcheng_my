package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.SpannerAdapterTuanGou;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HuoDongTuangou;
import com.yunyouzhiyuan.qianbaoshangcheng.model.HuoDongModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HuoDongTuanGouActivity extends BaseActivity {

    @Bind(R.id.top_tvtitle)
    TextView topTvtitle;
    @Bind(R.id.ethuodongtuangoname)
    EditText ethuodongname;
    @Bind(R.id.food_tvstarttime)
    TextView foodTvstarttime;
    @Bind(R.id.food_tvendtime)
    TextView foodTvendtime;
    @Bind(R.id.huodong_spshop)
    Spinner huodongSpshop;
    @Bind(R.id.ethuodongprice)
    EditText ethuodongprice;
    @Bind(R.id.food_ethuodongnumber)
    EditText foodEthuodongnumber;
    @Bind(R.id.activity_fabutuangopu_layout)
    PullRefreshLayout layout;
    private HuoDongModel model;
    private SpannerAdapterTuanGou tuanGouAdapter;
    private List<HuoDongTuangou.DataBean> list = new ArrayList<>();
    private String cat_id1;
    private long currentInMillis;
    private String good_name;
    private boolean istuangou = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabu_huo_dong);
        ButterKnife.bind(this);
        layout.setRefreshing(true);
        init();
        getData();
        setListener();
    }

    /**
     * 获取数据
     */
    private void getData() {
        model.getData(SpService.getSP().getStorId(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                list.clear();
                list.addAll((List<HuoDongTuangou.DataBean>) object);
                setSp0();
                layout.setRefreshing(false);
            }

            @Override
            public void onError(Object object) {
                list.clear();
                setSp0();
                Too.oo(object);
                layout.setRefreshing(false);
            }

            @Override
            public void onFailure(String string) {
                list.clear();
                setSp0();
                Too.oo(string);
                layout.setRefreshing(false);
            }
        });
    }

    /**
     * 设置监听
     */
    private void setListener() {
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void init() {
        istuangou = getIntent().getBooleanExtra("istuangou", false);
        showCookDialog();
        topTvtitle.setText("发布活动");
        model = new HuoDongModel();
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        currentInMillis = calendar.getTimeInMillis();
    }

    /**
     * 显示 警告用户一定要发布 团购
     */
    private void showCookDialog() {
        if (istuangou) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("黔宝商城提醒您：");
            builder.setMessage("您是美食店家，发布商品成功后，一定要及时将此商品参加团购，" +
                    "否则可能不能及时收到订单，为了您的方便着想，请您务必填写完毕进行提交，谢谢您的合作！");
            builder.setPositiveButton("明白，确定去提交", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }

    /**
     * 设置第一个商品分类
     */
    private void setSp0() {
        tuanGouAdapter = new SpannerAdapterTuanGou(this, list);
        huodongSpshop.setAdapter(tuanGouAdapter);
        huodongSpshop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).getGoods_id() != null) {
                    cat_id1 = list.get(position).getGoods_id();
                }
                if (list.get(position).getGoods_name() != null) {
                    good_name = list.get(position).getGoods_name();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Too.oo("onNothingSelected");
            }
        });
    }

    @OnClick({R.id.top_ivback, R.id.btnhuodongok, R.id.food_tvstarttime, R.id.food_tvendtime})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_ivback://返回
                if(istuangou){
                    Too.oo("请您务必提交团购");
                }else{
                    finish();
                }
                break;
            case R.id.btnhuodongok://提交
                subMit();
                break;
            case R.id.food_tvstarttime://开始时间
                showTimeDialog((TextView) view, true);
                break;
            case R.id.food_tvendtime://结束时间
                showTimeDialog((TextView) view, false);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(istuangou){
            Too.oo("请您务必提交团购信息");
        }else{
            super.onBackPressed();
        }
    }

    /**
     * 提交
     */
    private void subMit() {
        if (year == 0 || yearend == 0) {
            Too.oo("请填写时间");
            return;
        }
        String name = ethuodongname.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Too.oo("请填写标题");
            return;
        }
        if (TextUtils.isEmpty(cat_id1)) {
            Too.oo("请选择商品");
            return;
        }
        if (TextUtils.isEmpty(good_name)) {
            Too.oo("请选择商品");
            return;
        }
        String price = ethuodongprice.getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            Too.oo("请填写价格");
            return;
        }
        String number = foodEthuodongnumber.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            Too.oo("请填写数量");
            return;
        }

        loodingShow();
        model.submit(SpService.getSP().getStorId(), cat_id1, name, year + "-" + (month + 1) + "-" + dayOfMonth,
                yearend + "-" + (monthend + 1) + "-" + dayOfMonthend, price, number, good_name, 1, new IModel.AsyncCallBack() {
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
                        Too.oo(string);
                        dismissLooding();
                    }
                });

    }


    private int year, month, dayOfMonth;
    private int yearend, monthend, dayOfMonthend;
    private int currentYear, currentMonth, currentDayOfMonth;

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
                            foodTvstarttime.setText("请选择时间");
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
                            foodTvendtime.setText("请选择时间");
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
