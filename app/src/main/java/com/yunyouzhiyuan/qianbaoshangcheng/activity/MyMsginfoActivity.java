package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MyMsginfoActivity extends BaseActivity {
    @ViewInject(R.id.top_tvtitle)
    private TextView tvtitle;
    @ViewInject(R.id.top_ivback)
    private ImageView ivback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_msginfo);
        x.view().inject(this);
        init();
    }

    private void init() {
        tvtitle.setText(R.string.xitongtongzhi);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
