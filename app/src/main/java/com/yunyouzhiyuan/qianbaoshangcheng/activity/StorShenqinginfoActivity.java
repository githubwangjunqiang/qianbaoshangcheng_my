package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.MyApplication;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.ShenqingInfo;
import com.yunyouzhiyuan.qianbaoshangcheng.fragment.stor_info.FragmentStorinfo1;
import com.yunyouzhiyuan.qianbaoshangcheng.fragment.stor_info.FragmentStorinfo2;
import com.yunyouzhiyuan.qianbaoshangcheng.fragment.stor_info.FragmentStorinfo3;
import com.yunyouzhiyuan.qianbaoshangcheng.fragment.stor_info.FragmentStorinfo4;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.StorShenqingModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class StorShenqinginfoActivity extends BaseActivity {
    @ViewInject(value = R.id.activity_stor_info_iv1)
    private ImageView iv1;
    @ViewInject(value = R.id.top_ivback)
    private ImageView ivback;
    @ViewInject(value = R.id.activity_stor_info_iv2)
    private ImageView iv2;
    @ViewInject(value = R.id.activity_stor_info_iv3)
    private ImageView iv3;
    @ViewInject(value = R.id.activity_stor_info_iv4)
    private ImageView iv4;
    @ViewInject(value = R.id.activity_stor_info_tv1)
    private TextView tv1;
    @ViewInject(value = R.id.activity_stor_info_tv2)
    private TextView tv2;
    @ViewInject(value = R.id.activity_stor_info_tv3)
    private TextView tv3;
    @ViewInject(value = R.id.activity_stor_info_tv4)
    private TextView tv4;
    @ViewInject(value = R.id.top_tvtitle)
    private TextView tvtitle;
    @ViewInject(value = R.id.top_tvtitle_ri)
    private TextView tvtitle_ri;
    private Fragment[] fragments = new Fragment[4];
    private int fIndex;
    public static StorShenqinginfoActivity instanse;
    private ShenqingInfo shenqingInfo;

    public ShenqingInfo getShenqingInfo() {
        return shenqingInfo;
    }

    private boolean isxiugai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stor_shenqinginfo);
        x.view().inject(this);
        instanse = this;
        model = new StorShenqingModel();
        isxiugai = getIntent().getBooleanExtra("isxiugai", false);
        if (isxiugai) {
            getdata();
        } else {
            setview();
        }
    }

    /**
     * 初始化所有数据
     */
    private void setview() {
        init();
        setView(-1);
        setListener();
    }

    /**
     * 如果是修改资料 要去获取信息
     */
    private void getdata() {
        loodingShow();
        model.getdata(MyApplication.getUser().getSid(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                shenqingInfo = (ShenqingInfo) object;
                setview();
                dismissLooding();
            }

            @Override
            public void onError(Object object) {
                Too.oo(object);
                setview();
                dismissLooding();
            }

            @Override
            public void onFailure(String string) {

            }
        });
    }

    /**
     * 监听器
     */
    private void setListener() {
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        tvtitle_ri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backOut();
            }
        });
    }

    /**
     * 点击了 关于返回键的代码
     */
    private void back() {
        switch (fIndex) {
            case 0:
                backOut();
                break;
            case 1:
                setView(0);
                break;
            case 2:
                setView(1);
                break;
            case 3:
                setView(2);
                break;
        }
    }

    /**
     * 退出界面
     */
    private void backOut() {
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(this);
        alterDialog.setMessage("确定退出申请界面？您所填写的信息将被全部清空！");
        alterDialog.setCancelable(true);

        alterDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
            }
        });
        alterDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alterDialog.show();
    }

    /**
     * 切换fragment
     */
    public void setView(int index) {
        if (fIndex == index) {
            return;
        }
        // 获取管理对象
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
        // 显示对应的fragment 以前的fragment 要hide
        if (index != -1) {
            transaction.hide(fragments[fIndex]);
        } else {
            index = 0;
        }
        Fragment showFragment = fragments[index];
        // 判断是否添加过
        if (!showFragment.isAdded()) {
            transaction.add(R.id.activity_stor_info_ll, showFragment);
        }
        transaction.show(showFragment);
        transaction.commit();
        if (index != -1) {
            fIndex = index;
        }
        if (fIndex == 1) {
            tv2.setSelected(true);
            iv2.setSelected(true);
        }
        if (fIndex == 2) {
            tv3.setSelected(true);
            iv3.setSelected(true);
        }
        if (fIndex == 3) {
            tv4.setSelected(true);
            iv4.setSelected(true);
        }

    }

    /**
     * 初始化
     */
    private void init() {
        tvtitle.setText(R.string.activity_stor_info_title);
        tvtitle_ri.setText(R.string.top_back);
        fragments[0] = new FragmentStorinfo1();
        fragments[1] = new FragmentStorinfo2();
        fragments[2] = new FragmentStorinfo3();
        fragments[3] = new FragmentStorinfo4();
        tv1.setSelected(true);
        iv1.setSelected(true);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private String stroName;//  店铺名称
    private String connecter;//  店铺负责人
    private String cphone;// 联系电话
    private String addr;//  省市区地址串
    private String xx_addr;//   详细地址
    private String stype;//  店铺类型
    private String introducetext;//  店铺介绍
    private String outpicture;//  店铺外景
    private String insidepicture;// 店铺内景
    private String slogo;//   店铺logo


    private String province_id;//省id
    private String city_id;//市id
    private String district;//区id


    /**
     * 写入第一页信息
     */
    public void setOneParams(String stroName, String connecter, String cphone,
                             String addr, String xx_addr, String stype, String introducetext,
                             String outpicture, String insidepicture, String slogo
            , String province_id, String city_id, String district) {
        this.stroName = stroName;
        this.connecter = connecter;
        this.cphone = cphone;
        this.addr = addr;
        this.xx_addr = xx_addr;
        this.stype = stype;
        this.introducetext = introducetext;
        this.outpicture = outpicture;
        this.insidepicture = insidepicture;
        this.slogo = slogo;
        this.province_id = province_id;
        this.city_id = city_id;
        this.district = district;
    }

    private String IDname;//  身份证姓名
    private String IDcard;//  身份证号
    private String hand_card;//  手持身份证照
    private String lic_name;// 执照名称
    private String lic_code;// 执照注册号
    private String lic_addr;//  执照所在地
    private String lic_endtime;//  执照有效期
    private String lic_picture;// 执照图片
    private String per_name;// 执照名称
    private String per_code;// 执照注册号
    private String per_addr;// 执照所在地
    private String per_endtime;// 执照有效期
    private String per_picture;//  执照图片

    /**
     * @param per_picture
     * @param per_endtime
     * @param per_addr
     * @param per_code
     * @param per_name
     * @param lic_picture
     * @param lic_endtime
     * @param lic_addr
     * @param lic_code
     * @param lic_name
     * @param hand_card
     * @param IDcard
     * @param IDname
     */
    public void setTowParams(String per_picture, String per_endtime, String per_addr, String per_code, String per_name, String lic_picture, String lic_endtime, String lic_addr, String lic_code, String lic_name, String hand_card, String IDcard, String IDname) {
        this.per_picture = per_picture;
        this.per_endtime = per_endtime;
        this.per_addr = per_addr;
        this.per_code = per_code;
        this.per_name = per_name;
        this.lic_picture = lic_picture;
        this.lic_endtime = lic_endtime;
        this.lic_addr = lic_addr;
        this.lic_code = lic_code;
        this.lic_name = lic_name;
        this.hand_card = hand_card;
        this.IDcard = IDcard;
        this.IDname = IDname;
    }

    private String bank_name;//  商户收款银行名称
    private String bank_num;// 商户收款银行卡号
    private String bank_uname;//   商户账户名

    public void setThreeParams(String bank_name, String bank_num, String bank_uname) {
        this.bank_name = bank_name;
        this.bank_num = bank_num;
        this.bank_uname = bank_uname;
    }

    private String gpicture;//  商品示例图
    private String gprice;//  商品价格
    private String avgprice;//   人均消费

    public void setfourParams(String gpicture, String gprice, String avgprice) {
        this.gpicture = gpicture;
        this.gprice = gprice;
        this.avgprice = avgprice;
    }

    private StorShenqingModel model;

    /**
     * 去注册店铺
     */
    public void toZhece() {

        String id = null;
        if (null != shenqingInfo) {
            id = shenqingInfo.getData().getId();
        }
        String jingdu = SpService.getSP().getLng();
        String weidu = SpService.getSP().getLat();
        if(TextUtils.isEmpty(jingdu) || TextUtils.isEmpty(weidu)){
            Too.oo("手机定位尚未成功");
            return;
        }
        model.tozhuceStor(id, SpService.getSP().getUid(), stroName, connecter, cphone, addr,
                xx_addr, stype, introducetext, outpicture, insidepicture, slogo, province_id,
                city_id, district,
                IDname, IDcard, hand_card, lic_name, lic_code, lic_addr, lic_endtime, lic_picture,
                per_name, per_code, per_addr, per_endtime, per_picture, bank_name, bank_num,
                bank_uname, gpicture, gprice, avgprice, jingdu, weidu, new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object object) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(StorShenqinginfoActivity.this);
                        builder.setMessage(object.toString());
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (!isxiugai) {
                                    StorShenqingActivity.instanse.finish();
                                    LoginActivity.instanse.finish();
                                    startActivity(new Intent(StorShenqinginfoActivity.this, ShenqingjiluActivity.class));
                                }
                                finish();
                                overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);


                            }
                        });
                        builder.show();
                    }

                    @Override
                    public void onError(Object object) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(StorShenqinginfoActivity.this);
                        builder.setMessage(object.toString());
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }

                    @Override
                    public void onFailure(String string) {

                    }
                });


    }

}
