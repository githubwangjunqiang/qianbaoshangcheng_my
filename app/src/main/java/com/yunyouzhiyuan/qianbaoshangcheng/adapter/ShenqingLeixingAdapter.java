package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.JingYingLeixing;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/23.
 */

public class ShenqingLeixingAdapter extends MyAdapter<JingYingLeixing.DataBean> {
    public ShenqingLeixingAdapter(Activity context, List<JingYingLeixing.DataBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CheckedTextView textView;
        if (view == null) {
            textView = (CheckedTextView) getLayoutInflater().inflate(R.layout.shengqingleixingtv, parent, false);
            view = textView;
        }
        textView = (CheckedTextView) view;
        textView.setText(getData().get(position).getMobile_name());
        textView.setChecked(getData().get(position).isChecked());
        return view;
    }
}
