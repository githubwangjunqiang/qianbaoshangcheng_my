package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetWinDowHeight;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.logo;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class ActivityMainGvadapter extends BaseAdapter {
    private Activity activity;

    public ActivityMainGvadapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(activity).inflate(R.layout.itme_activity_main_gv, null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.itme_activity_main_gv_iv);
        imageView.setMaxHeight((int) (GetWinDowHeight.getScreenWeight(activity) / 2.5));
        imageView.setMinimumHeight((int) (GetWinDowHeight.getScreenWeight(activity) / 2.5 - 1));

        switch (position) {
            case 0:
                imageView.setImageResource(R.mipmap.main_fabu);
                break;
            case 1:
                imageView.setImageResource(R.mipmap.dingdanguanli);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.wodexiaoxi);
                break;
            case 3:
                imageView.setImageResource(R.mipmap.gerenzhongxin);
                break;
            case 4:
                imageView.setImageResource(R.mipmap.xiaofeiyanzheng);
                break;
            case 5:
                imageView.setImageResource(R.mipmap.shangpingguanli);
                break;
        }
        return convertView;
    }
}
