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
import com.yunyouzhiyuan.qianbaoshangcheng.activity.FabuKTVHoTelActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.FoodFabuActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.FoodTakeoutActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.HuoDongCuXiaoActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.HuoDongTuanGouActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.ShenqingleixingActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.yunfeiActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.Freight;
import com.yunyouzhiyuan.qianbaoshangcheng.model.FreightModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.MainModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.MyAnimtion;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class Dialog_Fabu extends Dialog {
    private TextView tvok, tvover;
    private RadioGroup radioGroup;
    private int type = -1;
    private MainModel model;
    private LoadingDialog loadingDialog;

    public Dialog_Fabu(Context context, MainModel model, LoadingDialog loadingDialog) {
        super(context, R.style.Dialog);
        this.model = model;
        this.loadingDialog = loadingDialog == null ? new LoadingDialog(context) : loadingDialog;
        setContentView(R.layout.dialog_fabu);
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
        tvok = (TextView) findViewById(R.id.dialog_fabu_tvok);
        tvover = (TextView) findViewById(R.id.dialog_fabu_tvover);
        radioGroup = (RadioGroup) findViewById(R.id.dialog_fabu_rg);
        setlistener();
    }

    private void setlistener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.dialog_fabu_rbshenqing://申请类型
                        type = 0;
                        break;
                    case R.id.dialog_fabu_rb0://团购
                        type = 1;
                        break;
                    case R.id.dialog_fabu_rbcuxiao://促销
                        type = 5;
                        break;
                    case R.id.dialog_fabu_rb1://酒店ktv
                        type = 2;
                        break;
                    case R.id.dialog_fabu_rb2://商品
                        type = 3;
                        break;
                    case R.id.dialog_fabu_rb3://设置运费
                        type = 4;
                        break;
                    case R.id.dialog_fabu_rbfood://外卖
                        type = 6;
                        break;
                }
            }
        });
        tvok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case -1:
                        MyAnimtion.getAnimator_DX(radioGroup).start();
                        break;
                    case 0://申请类型
                        getContext().startActivity(new Intent(getContext(), ShenqingleixingActivity.class));
                        dismiss();
                        break;
                    case 1://团购
                        String scid2 = SpService.getSP().getScid();
                        if (TextUtils.equals("4", scid2)) {//ktv
                            Too.oo("您经营的是ktv类型，直接发布ktv");
                        } else if (TextUtils.isEmpty(scid2)) {
                            Too.oo("店铺类型 未获取到，请重启应用亲");
                        } else if (TextUtils.equals("2", scid2)) {
                            Too.oo("您经营的是外卖类型，直接发布外卖");
                        } else {
                            loadIsChecked(1);
                        }
                        break;
                    case 2://酒店ktv
                        String scid = SpService.getSP().getScid();
                        if (TextUtils.equals("4", scid)) {//ktv
                            loadIsChecked(2);
                        } else if (TextUtils.isEmpty(scid)) {
                            Too.oo("店铺类型 未获取到，请重启应用亲");
                        } else {
                            Too.oo("您经营的店铺类型不是此类型");
                        }
                        break;
                    case 3://商品
                        String scidview = SpService.getSP().getScid();
                        if (TextUtils.isEmpty(scidview)) {//
                            Too.oo("店铺类型 未获取到，请重启应用亲");
                        } else if (TextUtils.equals("4", scidview)
                                || TextUtils.equals("2", scidview)) {
                            Too.oo("您经营的店铺类型不是此类型");
                        } else {
                            loadIsChecked(3);
                        }
                        break;
                    case 4://设置运费
                        String scid6 = SpService.getSP().getScid();
                        if (TextUtils.equals("2", scid6)) {//是外卖 可以发布跑腿
                            getContext().startActivity(new Intent(getContext(), yunfeiActivity.class));
                        } else {
                            Too.oo("您经营的店铺类型不是此类型");
                        }
                        break;
                    case 5://促销
                        String scid3 = SpService.getSP().getScid();
                        if (TextUtils.equals("4", scid3)) {//ktv
                            Too.oo("您经营的是ktv类型，直接发布ktv");
                        } else if (TextUtils.isEmpty(scid3)) {
                            Too.oo("店铺类型 未获取到，请重启应用亲");
                        } else if (TextUtils.equals("2", scid3)) {//是外卖
                            Too.oo("您经营的是外卖类型，直接发布外卖");
                        } else {
                            loadIsChecked(5);
                        }
                        break;
                    case 6://外卖
                        String scid4 = SpService.getSP().getScid();
                        if (TextUtils.equals("2", scid4)) {//是外卖店铺
                            isDistribution();
                        } else {
                            Too.oo("您不是此店铺类型");
                        }
                        break;
                    default:
                        break;
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

    /**
     * 店铺是否已经申请经营类型
     *
     * @param position
     */
    private void loadIsChecked(final int position) {
        loadingDialog.show();
        model.isChecked(SpService.getSP().getStorId(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                switch (position) {
                    case 1://活动团购
                        getContext().startActivity(new Intent(getContext(), HuoDongTuanGouActivity.class));
                        break;
                    case 2://发布ktv酒店
                        getContext().startActivity(new Intent(getContext(), FabuKTVHoTelActivity.class));
                        break;
                    case 3://发布商品
                        getContext().startActivity(new Intent(getContext(), FoodFabuActivity.class));
                        break;
                    case 4://设置运费

                        break;
                    case 5://发布促销
                        getContext().startActivity(new Intent(getContext(), HuoDongCuXiaoActivity.class));
                        break;
                    case 6://发布外卖
                        getContext().startActivity(new Intent(getContext(), FoodTakeoutActivity.class));
                        break;
                }
                loadingDialog.dismiss();
                dismiss();
            }

            @Override
            public void onError(Object object) {
                loadingDialog.dismiss();
                Too.oo(object);
            }

            @Override
            public void onFailure(String string) {
                loadingDialog.dismiss();
                Too.oo(string);
            }
        });
    }

    /**
     * 是否设置过运费
     */
    private void isDistribution() {
        loadingDialog.show();
        new FreightModel().get_send_price(SpService.getSP().getStorId(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                List<Freight.DataBean> list = (List<Freight.DataBean>) object;
                if (Double.parseDouble(list.get(0).getSend_price()) > 0) {
                    loadIsChecked(6);
                } else {
                    Too.oo("请先去设置好配送费用相关，发布外卖下/设置运费");
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(String string) {
                Too.oo(string);
                loadingDialog.dismiss();
            }
        });
    }
}
