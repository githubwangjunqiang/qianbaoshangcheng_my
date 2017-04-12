package com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.DindanYouhuijuanActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.DingdanWaimaiActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.MyAnimtion;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class Dialog_Dingdan extends Dialog {
    private TextView tvok, tvover;
    private RadioGroup radioGroup;
    private int type = 0;

    public Dialog_Dingdan(Context context) {
        super(context, R.style.Dialog);
        setContentView(R.layout.dialog_dingdan);
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
        tvok = (TextView) findViewById(R.id.dialog_dingdan_tvok);
        tvover = (TextView) findViewById(R.id.dialog_dingdan_tvover);
        radioGroup = (RadioGroup) findViewById(R.id.dialog_dingdan_rg);
        setlistener();
    }

    private void setlistener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.dialog_dingdan_rb1) {//优惠订单
                    type = 1;
                } else if (checkedId == R.id.dialog_dingan_rb2) {//外卖订单
                    type = 2;
                }
            }
        });
        tvok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    MyAnimtion.getAnimator_DX(radioGroup).start();
                    return;
                }
                if (type == 1) {//优惠券订单
                    String scid = SpService.getSP().getScid();
                    if (TextUtils.equals("2", scid)) {
                        Too.oo("你是外卖经营者，请进入外卖订单");
                        return;
                    }
                    getContext().startActivity(new Intent(getContext(), DindanYouhuijuanActivity.class));
                    dismiss();
                }
                if (type == 2) {//外卖订单
                    String scid = SpService.getSP().getScid();
                    if (TextUtils.isEmpty(scid)) {
                        Too.oo("本地未获取到您的经营类型，请见谅重启本应用");
                        dismiss();
                        return;
                    }
                    if (TextUtils.equals("2", scid)) {//是外卖 可以进入
                        getContext().startActivity(new Intent(getContext(), DingdanWaimaiActivity.class));
                        dismiss();
                    } else {
                        Too.oo("您的店铺不是此经营类型");
                    }
                }
            }
        });
        tvover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
