package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.LoginModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.MyAnimtion;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.Time_down;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class WangPasswordActivity extends BaseActivity {
    @ViewInject(value = R.id.top_tvtitle)
    private TextView tvtitle;
    @ViewInject(value = R.id.top_ivback)
    private ImageView ivback;
    @ViewInject(value = R.id.activity_wangjimima_tvbtnok)
    private TextView tvbtnok;
    @ViewInject(value = R.id.activity_wangjimima_tvcode)
    private TextView tvcode;
    @ViewInject(value = R.id.activity_wangjimima_etcode)
    private EditText etcode;
    @ViewInject(value = R.id.activity_wangjimima_etphone)
    private EditText etphone;
    @ViewInject(value = R.id.activity_wangjimima_etpas)
    private EditText etpas;
    @ViewInject(value = R.id.activity_wangjimima_etpas2)
    private EditText etpas2;
    private LoginModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wang_password);
        x.view().inject(this);
        init();
        setlistener();
    }

    /**
     * 初始化
     */
    private void init() {
        tvtitle.setText(R.string.wangjimima);
        model = new LoginModel();
    }

    /**
     * 监听器
     */
    private void setlistener() {
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
            }
        });
    }

    /**
     * 监听器
     *
     * @param view
     */
    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.activity_wangjimima_tvbtnok://点击提交
                toTiJiao();
                break;
            case R.id.activity_wangjimima_tvcode://点验证码
                toCode();
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void toCode() {
        String phone = etphone.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || phone.length() < 1) {
            MyAnimtion.getAnimator_DX(etphone).start();
            Too.oo("请输入手机号");
            return;
        }
        loodingShow();
        model.wangjimimaCode(phone, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                dismissLooding();
//                Too.oo(object);
                new Time_down(60 * 1000, 1001, tvcode).start();
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

    /**
     * 注册
     */
    private void toTiJiao() {
        final String phone = etphone.getText().toString().trim();
        String code = etcode.getText().toString().trim();
        final String pas = etpas.getText().toString().trim();
        String pas1 = etpas2.getText().toString().trim();

        if (TextUtils.isEmpty(phone) || phone.length() < 1) {
            MyAnimtion.getAnimator_DX(etphone).start();
            Too.oo("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(code) || code.length() < 1) {
            MyAnimtion.getAnimator_DX(etcode).start();
            Too.oo("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(pas) || pas.length() < 1) {
            MyAnimtion.getAnimator_DX(etpas).start();
            Too.oo("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(pas1) || pas1.length() < 1) {
            MyAnimtion.getAnimator_DX(etpas2).start();
            Too.oo("请输入确认密码");
            return;
        }
        if (!TextUtils.equals(pas, pas1)) {
            MyAnimtion.getAnimator_DX(etpas).start();
            MyAnimtion.getAnimator_DX(etpas2).start();
            Too.oo("请确认两次密码相同");
            return;
        }
        loodingShow();
        model.unpasword(phone, pas, code, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object object) {
                dismissLooding();
                Too.oo(object);
                Intent intent = getIntent();
                intent.putExtra("phone", phone);
                intent.putExtra("pas", pas);
                setResult(1, intent);
                finish();
                overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
        }
        return super.onKeyDown(keyCode, event);

    }
}
