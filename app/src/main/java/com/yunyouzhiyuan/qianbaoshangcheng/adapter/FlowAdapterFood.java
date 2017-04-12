package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.FoodCuisine;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;


/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class FlowAdapterFood extends TagAdapter<FoodCuisine.DataBean> {
    private Context context;

    public FlowAdapterFood(List<FoodCuisine.DataBean> datas, Context context) {
        super(datas);
        this.context = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, FoodCuisine.DataBean s) {
        TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.flowlayout_new, null);
        if (!TextUtils.isEmpty(s.getCat_name())) {
            tv.setText(s.getCat_name());
        }
        return tv;
    }

    @Override
    public boolean setSelected(int position, FoodCuisine.DataBean s) {
        return super.setSelected(position, s);
    }
}
