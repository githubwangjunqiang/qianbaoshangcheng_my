package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.MessageInfo;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.StringReplaceUtil;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/12.
 */

public class MessageAdapter extends MyAdapter<MessageInfo.DataBean> {
    public MessageAdapter(Context context, List<MessageInfo.DataBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.itme_messagelistview, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        holder.setData(getData().get(position));
        return view;
    }

    private class ViewHolder {
        public TextView tvName, tvPrice, tvUser, tvPhone, tvTime;

        public ViewHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.itme_goodsname);
            tvPrice = (TextView) view.findViewById(R.id.itme_price);
            tvUser = (TextView) view.findViewById(R.id.itme_tvusername);
            tvPhone = (TextView) view.findViewById(R.id.itme_tvphone);
            tvTime = (TextView) view.findViewById(R.id.itme_tvtime);
        }

        public void setData(final MessageInfo.DataBean dataBean) {
            tvName.setText("商品名称：" + dataBean.getGoods_name());
            tvPrice.setText("订单价格：￥" + dataBean.getOrder_amount());
            tvUser.setText("顾客名称：" + dataBean.getNickname());
            tvPhone.setText("顾客联系方式：" +
                    (StringReplaceUtil.idphoneReplaceWithStar(dataBean.getMobile())) + "长按拨打");
            tvTime.setText("下单时间：" + dataBean.getPay_time());
            tvPhone.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + dataBean.getMobile()));
                    getContext().startActivity(intent);
                    return false;
                }
            });
        }


    }
}
