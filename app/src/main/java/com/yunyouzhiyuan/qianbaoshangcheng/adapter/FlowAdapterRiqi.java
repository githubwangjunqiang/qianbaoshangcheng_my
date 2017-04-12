package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;


/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class FlowAdapterRiqi extends TagAdapter<String> {
    private Context context;
    public FlowAdapterRiqi(List<String> datas, Context context) {
        super(datas);
        this.context = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.flowlayout_new_riqi,null);
        tv.setText(s);
        return tv;
    }

    @Override
    public boolean setSelected(int position, String s) {
        return super.setSelected(position, s);
    }
}
