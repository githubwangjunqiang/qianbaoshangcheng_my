package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.FoodDingdan;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/5.
 */

public class FoodDingDanAdapter_Child extends MyAdapter<FoodDingdan.DataBean.GoodsinfoBean> {
    private List<FoodDingdan.DataBean.GoodsinfoBean> list;

    public FoodDingDanAdapter_Child(Context context, List<FoodDingdan.DataBean.GoodsinfoBean> data) {
        super(context, data);
        list = new ArrayList<>();
        list.addAll(data);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * 更新数据
     */
    public void notifyDataChanged(List<FoodDingdan.DataBean.GoodsinfoBean> data) {
        this.list.clear();
        this.list.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.itme_waimai_dingdan_listview, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.itme_waimai_dingdan_listview_ivc);
            holder.tvname = (TextView) convertView.findViewById(R.id.itme_waimai_dingdan_listview_tvnamec);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.itme_waimai_dingdan_listview_tvpricec);
            holder.tvNumber = (TextView) convertView.findViewById(R.id.itme_waimai_dingdan_listview_tvnumberc);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        x.image().bind(holder.imageView, HttpUrl.IMAGE + list.get(position).getOriginal_img(),
                new ImageOptions.Builder().setFadeIn(true).setLoadingDrawableId(R.drawable.t2)
                        .setFailureDrawableId(R.drawable.t2).build());
        holder.tvname.setText("" + list.get(position).getGoods_name());
        holder.tvPrice.setText("单价：￥" + list.get(position).getGoods_price());
        holder.tvNumber.setText("购买数量：" + list.get(position).getGoods_num() + "份");
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView tvname, tvPrice, tvNumber;

    }
}
