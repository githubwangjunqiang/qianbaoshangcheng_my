package com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.KTVModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class Dialog_addTime extends Dialog {
    private TextView tvok, tvover, tvFistTime, tvLstTime;
    private Callback callback;
    private KTVModel model;
    private String spec_id = "";
    private LoadingDialog loadingDialog;


    public interface Callback {
        void onClick(String name, String id);
    }

    public Dialog_addTime(Context context, Callback callback, KTVModel model, String spec_id) {
        super(context, R.style.Dialog_addtime);
        this.callback = callback;
        this.model = model == null ? new KTVModel() : model;
        this.spec_id = spec_id;
        loadingDialog = new LoadingDialog(getContext());
        setContentView(R.layout.dialog_addtime);
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        tvok = (TextView) findViewById(R.id.dialog_addtime_tvok);
        tvover = (TextView) findViewById(R.id.dialog_addtime_tvover);
        tvFistTime = (TextView) findViewById(R.id.dialog_addtime_tvfist);
        tvLstTime = (TextView) findViewById(R.id.dialog_addtime_tvlast);
        setlistener();
    }

    private void setlistener() {

        tvFistTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime(true);
            }
        });
        tvLstTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime(false);
            }
        });
        tvok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSpecItem();
            }
        });
        tvover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private int fisttime;//开始的时间小时数
    private int lastime;//结束的时间小时数
    private int fisttimeMin;//开始的时间分钟数
    private int lastimeMin;//结束的时间分钟数

    /**
     * 显示时间对话框
     */
    private void showTime(final boolean isFist) {
        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (isFist) {
                    fisttime = hourOfDay;
                    fisttimeMin = minute<30?0:30;
                    tvFistTime.setText("开始时间:\t\t" + fisttime + ":" + fisttimeMin);
                } else {
                    lastime = hourOfDay;
                    lastimeMin = minute<30?0:30;
                    tvLstTime.setText("结束时间:\t\t" + lastime + ":" + lastimeMin);
                }
            }
        }, isFist ? fisttime : lastime, 0, true).show();
    }

    /**
     * 29、商家发布商品时添加的规格
     */
    private void addSpecItem() {
        if (TextUtils.isEmpty(tvFistTime.getText().toString().trim()) ||
                TextUtils.isEmpty(tvLstTime.getText().toString().trim())) {
            Too.oo("开始时间和结束时间不能为空");
            return;
        }
        loadingDialog.show();
        final StringBuilder builder = new StringBuilder().append(fisttime < 10 ? "0" + fisttime :
                fisttime).append(":").append(fisttimeMin < 10 ? "0" + fisttimeMin : fisttimeMin)
                .append("-").append(lastime < 10 ? "0" + lastime : lastime).append(":").
                        append(lastimeMin < 10 ? "0" + lastimeMin : lastimeMin);
        model.addSpecItem(SpService.getSP().getStorId(), spec_id, builder.toString(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                if (callback != null) {
                    callback.onClick(builder.toString(), (String) object);
                }
                loadingDialog.dismiss();
                dismiss();
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
            }

            @Override
            public void onFailure(String string) {
                Too.oo(string);
            }
        });
    }
}
