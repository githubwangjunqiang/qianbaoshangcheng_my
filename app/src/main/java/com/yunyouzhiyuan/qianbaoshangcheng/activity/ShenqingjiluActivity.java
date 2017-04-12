package com.yunyouzhiyuan.qianbaoshangcheng.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.adapter.MyFragmentPagerAdapter;
import com.yunyouzhiyuan.qianbaoshangcheng.fragment.BaseFragment;
import com.yunyouzhiyuan.qianbaoshangcheng.fragment.stor_jilu.FragmentStorjilu1;
import com.yunyouzhiyuan.qianbaoshangcheng.fragment.stor_jilu.FragmentStorjilu2;
import com.yunyouzhiyuan.qianbaoshangcheng.fragment.stor_jilu.FragmentStorjilu3;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class ShenqingjiluActivity extends BaseActivity {
    @ViewInject(value = R.id.activity_shenqingjulu_viewpager)
    private ViewPager viewPager;
    @ViewInject(value = R.id.top_ivback)
    private ImageView ivback;
    @ViewInject(value = R.id.top_tvtitle)
    private TextView tvtitle;
    @ViewInject(value = R.id.top_tvtitle_ri)
    private TextView tvtitle_ri;
    @ViewInject(value = R.id.activity_shenqingjulu_tablayout)
    private TabLayout tabLayout;
    private List<BaseFragment> fragments = new ArrayList<>();
    private List<String> strings = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shenqingjilu);
        x.view().inject(this);
        init();
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.alpha_scale_in1,R.anim.alpha_scale_out1);
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        tvtitle.setText(R.string.shenqingjilu);
        fragments.add(new FragmentStorjilu1());
        fragments.add(new FragmentStorjilu2());
        fragments.add(new FragmentStorjilu3());
        strings.add(getResources().getString(R.string.shenhezhong));
        strings.add(getResources().getString(R.string.tongguoshenhe));
        strings.add(getResources().getString(R.string.weitongguo));
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments,strings));
        tabLayout.setupWithViewPager(viewPager,false);
        viewPager.setOffscreenPageLimit(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.alpha_scale_in1,R.anim.alpha_scale_out1);
        }
        return super.onKeyDown(keyCode, event);
    }
}
