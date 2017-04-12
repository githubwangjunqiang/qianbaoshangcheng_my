package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.ActivityCollector;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.Dialog_SubmitOpinion;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MyActivity extends BaseActivity {
    @ViewInject(value = R.id.top_tvtitle)
    private TextView tvtitle;
    @ViewInject(value = R.id.top_ivback)
    private ImageView ivback;
    @ViewInject(value = R.id.activity_my_bianji)
    private TextView tvbianji;
    @ViewInject(value = R.id.activity_my_tvtongji)
    private TextView tvtongji;
    @ViewInject(value = R.id.activity_my_yijian)
    private TextView tvyijian;
    @ViewInject(value = R.id.activity_my_guanyuwomen)
    private TextView tvguanyu;
    @ViewInject(value = R.id.activity_my_tuichudenglu)
    private TextView tvtuichu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        x.view().inject(this);
        init();
    }

    /**
     * 出事啊胡
     */
    private void init() {
        tvtitle.setText(R.string.grrenzhongxin);
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
     */
    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.activity_my_bianji://点击编辑
                startActivity(new Intent(MyActivity.this, ZhuYeBianjiActivity.class));
                break;
            case R.id.activity_my_tvtongji://点击统计
                startActivity(new Intent(MyActivity.this, TongjiActivity.class));
                break;
            case R.id.activity_my_yijian://点击意见
                new Dialog_SubmitOpinion(this).show();
                break;
            case R.id.activity_my_guanyuwomen://点关于我们
                WebViewActivity.startWebViewActivity(this, HttpUrl.storeaboutus,null);
                break;
            case R.id.activity_my_tuichudenglu://点击退出
                ActivityCollector.finishAll();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
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
