package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.KTVList;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;


/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class FlowAdapter extends TagAdapter<KTVList.DataBean.SpecItemListBean> {
    private Context context;

    public FlowAdapter(List<KTVList.DataBean.SpecItemListBean> datas, Context context) {
        super(datas);
        this.context = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, KTVList.DataBean.SpecItemListBean s) {
        TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.flowlayout_new, null);
        if (!TextUtils.isEmpty(s.getItem())) {
            tv.setText(s.getItem());
        }
        return tv;
    }

    @Override
    public boolean setSelected(int position, KTVList.DataBean.SpecItemListBean s) {
        return super.setSelected(position, s);
    }
}
