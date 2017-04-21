package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.PingjiaActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.DingDan;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.StringReplaceUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class DingdanYouhuijuanAdapter extends MyAdapter<DingDan.DataBean> {
    public DingdanYouhuijuanAdapter(Context context, List<DingDan.DataBean> data) {
        super(context, data);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = getLayoutInflater().inflate(R.layout.itme_youhuijuan_dingdan_lisvierw, null);
            holder.imageView = (ImageView) view.findViewById(R.id.itme_youhuijuan_dingdan_listview_iv);
            holder.tvname = (TextView) view.findViewById(R.id.itme_youhuijuan_dingdan_listview_tvname);
            holder.tvtime = (TextView) view.findViewById(R.id.itme_youhuijuan_dingdan_listview_tvtime);
            holder.tvUser = (TextView) view.findViewById(R.id.itme_youhuijuan_dingdan_listview_tvuser);
            holder.tvCall = (TextView) view.findViewById(R.id.itme_youhuijuan_dingdan_listview_tvcall);
            holder.tvprice = (TextView) view.findViewById(R.id.itme_youhuijuan_dingdan_listview_tvprice);
            holder.tvFenshu = (TextView) view.findViewById(R.id.youhuiquandingdan_tvfenshu);
            holder.tvBtnPingjia = (TextView) view.findViewById(R.id.youhuiquandingdan_tvbtnpingjia);
            holder.bar = (RatingBar) view.findViewById(R.id.youhuiquan_bar);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        x.image().bind(holder.imageView, HttpUrl.IMAGE + getData().get(position).getOriginal_img(),
                new ImageOptions.Builder().setFadeIn(true).setRadius(DensityUtil.dip2px(4))
                        .setFailureDrawableId(R.drawable.t2).setImageScaleType(ImageView.ScaleType.FIT_XY).build());
        holder.tvname.setText(getData().get(position).getGoods_name());

        holder.tvUser.setText("用户电话：" + StringReplaceUtil.idphoneReplaceWithStar(getData().get(position).getMobile()));
        holder.tvtime.setText("有效期限：" + getData().get(position).getStart_time() + ""
                + getData().get(position).getEnd_time());
        holder.tvprice.setText("￥\n" + getData().get(position).getGoods_price());
        holder.tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(getData().get(position).getMobile())) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + getData().get(position).getMobile()));
                    getContext().startActivity(intent);
                }
            }
        });

        if (getData().get(position).getGoods_rank() != null) {
            holder.tvFenshu.setText("客户评分：" + getData().get(position).getGoods_rank() + "分");
            holder.bar.setRating(Float.parseFloat(getData().get(position).getGoods_rank() + ""));
        }
        holder.tvBtnPingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PingjiaActivity.startPingjiaActivity(getContext(), getData().get(position).getOrder_id());
            }
        });


        return view;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView tvname, tvtime, tvprice, tvUser, tvCall, tvFenshu, tvBtnPingjia;
        RatingBar bar;
    }
}
