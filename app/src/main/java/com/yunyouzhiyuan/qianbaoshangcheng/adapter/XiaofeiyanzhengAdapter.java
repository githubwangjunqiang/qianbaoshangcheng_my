package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.VerifiCation;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.StringReplaceUtil;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class XiaofeiyanzhengAdapter extends MyAdapter<VerifiCation.DataBean> {
    private Callback callback;
    public XiaofeiyanzhengAdapter(Activity context, List<VerifiCation.DataBean> data, Callback callback) {
        super(context, data);
        this.callback = callback;
    }

    public interface Callback {
        void onScanning(int position);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = getLayoutInflater().inflate(R.layout.itme_xiaofeiyanaheng_listvew, null);
            holder.imageView = (ImageView) view.findViewById(R.id.itme_xiao_listview_ivimage);
            holder.tvname = (TextView) view.findViewById(R.id.itme_xiao_listview_tvname);
            holder.tvfenshu = (TextView) view.findViewById(R.id.itme_xiaoi_listview_tvfenshu);
            holder.tvprice = (TextView) view.findViewById(R.id.itme_xiao_listview_tvprice);
            holder.tvdanjianumber = (TextView) view.findViewById(R.id.itme_xiao_listview_tvyuexiaoliang);
            holder.tvbtnxiajia = (TextView) view.findViewById(R.id.itme_xiaolistview_tvxiajia);
            holder.tvyanzhengma = (TextView) view.findViewById(R.id.itme_xiao_listview_tvdaijinjuan);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        x.image().bind(holder.imageView, HttpUrl.IMAGE + getData().get(position).getOriginal_img(),
                new ImageOptions.Builder().setFadeIn(true).setLoadingDrawableId(R.drawable.t2)
                        .setFailureDrawableId(R.drawable.t2).build());
        holder.tvname.setText(getData().get(position).getGoods_name() + "");
        holder.tvfenshu.setText("订单状态：已在线支付");
        holder.tvprice.setText("￥" + getData().get(position).getGoods_price() + "/份");
        holder.tvdanjianumber.setText("×" + getData().get(position).getGoods_num() + "\t￥" + getData().get(position)
                .getGroup_price());

        holder.tvyanzhengma.setText("顾客手机号：" +
                StringReplaceUtil.idphoneReplaceWithStar(getData().get(position).getMobile()) + "\t长按拨打");
        holder.tvyanzhengma.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + getData().get(position).getMobile()));
                getContext().startActivity(intent);
                return true;
            }
        });
        holder.tvbtnxiajia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.onScanning(position);
                }
            }
        });
        return view;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView tvname, tvfenshu, tvprice, tvdanjianumber, tvbtnxiajia, tvyanzhengma;
    }
}
