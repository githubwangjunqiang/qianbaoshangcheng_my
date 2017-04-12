package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.Mingxi;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/11.
 */

public class MingxiAdapter extends MyAdapter<Mingxi.DataBean> {

    public MingxiAdapter(Context context, List<Mingxi.DataBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = getLayoutInflater().inflate(R.layout.itme_mingxi_listview, null);
            holder.textView = (TextView) view.findViewById(R.id.itme_mingxi_listiew_tvname);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        String stats = null;
        /**
         * ("0"申请中  "1"申请通过  "2"申请失败)
         */
        switch (getData().get(position).getStatus()) {
            case "0":
                stats = "申请中";
                break;
            case "1":
                stats = "申请通过";
                break;
            case "2":
                stats = "申请失败";
                break;
        }
        holder.textView.setText("提现时间:\t" + getData().get(position).getCreate_time() +
                "\n提现金额:\t￥" + getData().get(position).getMoney() + "\n提现状态:\t" + stats);
        return view;
    }

    private class ViewHolder {
        TextView textView;
    }
}
