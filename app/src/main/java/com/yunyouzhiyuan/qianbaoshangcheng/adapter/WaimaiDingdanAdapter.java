package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.FoodDingdan;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.StringReplaceUtil;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class WaimaiDingdanAdapter extends MyAdapter<FoodDingdan.DataBean> {
    public WaimaiDingdanAdapter(Activity context, List<FoodDingdan.DataBean> data) {
        super(context, data);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = getLayoutInflater().inflate(R.layout.itme_waimai_dingdan_listview_top, null);
            holder.listView = (ListView) view.findViewById(R.id.itme_waimai_dingdan_listview_iv);
            holder.tvname = (TextView) view.findViewById(R.id.itme_waimai_dingdantvname);
            holder.tvphone = (TextView) view.findViewById(R.id.itme_waimai_dingdan_listview_tvphone);
            holder.tvprice = (TextView) view.findViewById(R.id.itme_waimai_dingdan_listview_price);
            holder.tvtime = (TextView) view.findViewById(R.id.itme_waimai_dingdan_listview_tvtime);
            holder.tvbtnPhone = (TextView) view.findViewById(R.id.itme_waimai_dingdan_listview_tvbtnphone);
            holder.tvbtnJIedan = (TextView) view.findViewById(R.id.itme_waimai_dingdan_listview_tvbtnjiedanc);
            holder.adapter_child = new FoodDingDanAdapter_Child(getContext(), getData().get(position).getGoodsinfo());
            holder.listView.setAdapter(holder.adapter_child);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        holder.tvname.setText("顾客昵称：" + getData().get(position).getNickname());
        holder.tvphone.setText("顾客电话：" + StringReplaceUtil.idphoneReplaceWithStar(
                getData().get(position).getMobile()));



        holder.tvtime.setText("添加时间：" + getData().get(position).getPay_time());
        holder.tvprice.setText("订单总价：￥" + getData().get(position).getOrder_amount());
        holder.tvbtnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + getData().get(position).getMobile()));
                getContext().startActivity(intent);
            }
        });
        holder.tvbtnJIedan.setVisibility(View.GONE);
        holder.adapter_child.notifyDataChanged(getData().get(position).getGoodsinfo());

        return view;
    }

    private class ViewHolder {
        ListView listView;
        TextView tvname, tvphone, tvprice, tvtime, tvbtnPhone, tvbtnJIedan;
        FoodDingDanAdapter_Child adapter_child;
    }
}
