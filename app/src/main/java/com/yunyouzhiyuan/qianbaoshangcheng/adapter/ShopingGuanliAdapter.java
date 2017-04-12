package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.FabuKTVHoTelActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.StorList;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class ShopingGuanliAdapter extends MyAdapter<StorList.DataBean> {
    private CallBack callBack;

    public ShopingGuanliAdapter(Activity context, List<StorList.DataBean> data, CallBack callBack) {
        super(context, data);
        this.callBack = callBack;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        final StorList.DataBean data = getData().get(position);
        if (view == null) {
            holder = new ViewHolder();
            view = getLayoutInflater().inflate(R.layout.itme_shoping_guanli_listvew, null);
            holder.imageView = (ImageView) view.findViewById(R.id.itme_shoping_gaunzli_listview_ivimage);
            holder.tvname = (TextView) view.findViewById(R.id.itme_shoping_gaunzli_listview_tvname);
            holder.tvprice = (TextView) view.findViewById(R.id.itme_shoping_gaunzli_listview_tvprice);
            holder.tvyuexiaonumber = (TextView) view.findViewById(R.id.itme_shoping_gaunzli_listview_tvyuexiaoliang);
            holder.tvbtnxiajia = (TextView) view.findViewById(R.id.itme_shoping_gaunzli_listview_tvxiajia);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        x.image().bind(holder.imageView, HttpUrl.IMAGE + data.getOriginal_img(),
                new ImageOptions.Builder().setFadeIn(true).setLoadingDrawableId(R.drawable.t2)
                        .setLoadingDrawableId(R.drawable.t2).setSize(0, 0).setRadius(DensityUtil.dip2px(4))
                        .setImageScaleType(ImageView.ScaleType.FIT_XY).build());
        holder.tvname.setText("" + getData().get(position).getGoods_name());
        holder.tvprice.setText("￥" + data.getShop_price() + "/份");
        holder.tvyuexiaonumber.setText("月销量：" + getData().get(position).getMonth_sales());
        holder.tvbtnxiajia.setText(TextUtils.equals("1", data.getIs_on_sale()) ? "下架" : "出售");
        holder.tvbtnxiajia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onClick(position);
            }
        });
        return view;
    }

    /**
     * 编辑商品
     *
     * @param goods_id 商品id
     */
    private void editShop(String goods_id, String prom_type) {
        String storId = SpService.getSP().getStorId();
        if (!TextUtils.isEmpty(storId)) {
            switch (storId) {
                case "4"://ktv
                    if (TextUtils.equals("3", prom_type)) {//团购
                        getContext().startActivity(new Intent(getContext(), FabuKTVHoTelActivity.class));
                    }
                    break;
                default://其他店铺
                    if (TextUtils.equals("3", prom_type)) {//团购
                        getContext().startActivity(new Intent(getContext(), FabuKTVHoTelActivity.class));
                    }
                    break;
            }
        }

    }

    private class ViewHolder {
        ImageView imageView;
        TextView tvname, tvprice, tvyuexiaonumber, tvbtnxiajia;
    }

    public interface CallBack {
        void onClick(int postion);
    }
}
