package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;

import org.xutils.common.util.DensityUtil;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/25.
 */
public class SpannerAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    public SpannerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setTextColor(context.getResources().getColor(R.color.text_color));
        textView.setPadding(DensityUtil.dip2px(10),DensityUtil.dip2px(10),DensityUtil.dip2px(10),DensityUtil.dip2px(10));
        textView.setBackgroundColor(context.getResources().getColor(R.color.white));
        textView.setTextSize(12);
        textView.setGravity(Gravity.CENTER);
        textView.setText(list.get(position));
        return textView;
    }
}
