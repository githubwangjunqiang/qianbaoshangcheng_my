package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.MyApplication;
import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.MyFragmentPagerAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.fragment.BaseFragment;
import com.yunyouzhiyuan.qianbaoshangcheng.fragment.dingdan_youhuijuan.Fragmentguoqi;
import com.yunyouzhiyuan.qianbaoshangcheng.fragment.dingdan_youhuijuan.Fragmentnew;
import com.yunyouzhiyuan.qianbaoshangcheng.fragment.dingdan_youhuijuan.Fragmentyishiyong;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class DindanYouhuijuanActivity extends BaseActivity {
    @ViewInject(value = R.id.top_tvtitle)
    private TextView tvtitle;
    @ViewInject(value = R.id.top_tvtitle_ri)
    private TextView tvtitle_ri;
    @ViewInject(value = R.id.top_ivback)
    private ImageView ivback;
    @ViewInject(value = R.id.activity_dingdan_youhuijuan_viewpager)
    private ViewPager viewPager;
    @ViewInject(value = R.id.activity_dingdan_youhuijuan_tablayout)
    private TabLayout tabLayout;
    private List<String> strings = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dindan_youhuijuan);
        x.view().inject(this);
        if (!MyApplication.isLogin()) {
            Intent mainIntent = new Intent(this, LoginActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
            finish();
            return;
        }
        init();
        setlistener();
        setadapter();
    }

    /**
     * 适配
     */
    private void setadapter() {
        strings.add("最新订单");
        strings.add("已使用订单");
        strings.add("过期订单");
        fragments.add(new Fragmentnew());
        fragments.add(new Fragmentyishiyong());
        fragments.add(new Fragmentguoqi());
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, strings));
        tabLayout.setupWithViewPager(viewPager);
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
     * 初始化
     */
    private void init() {
        tvtitle.setText(R.string.youhuijudingdan);
        tvtitle_ri.setText(R.string.bianji);
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
