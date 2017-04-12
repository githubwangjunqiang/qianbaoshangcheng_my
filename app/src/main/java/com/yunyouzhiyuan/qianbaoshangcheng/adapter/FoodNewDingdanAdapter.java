package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.FoodDingdan_New;
import com.yunyouzhiyuan.qianbaoshangcheng.model.DingDanModel;
import com.yunyouzhiyuan.qianbaoshangcheng.model.IModel;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.Too;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.Dialog_ReleaseErrands;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.StringReplaceUtil;

import java.util.List;

import okhttp3.Call;

/**
 * Created by wangjunqiang on 2016/11/24.
 */
public class FoodNewDingdanAdapter extends MyAdapter<FoodDingdan_New.DataBean> {
    private DingDanModel model;
    private LoadingDialog loadingDialog;
    private Call call;
    private Callback callback;

    public interface Callback {
        void onRefresh();
    }

    public FoodNewDingdanAdapter(Context context, List<FoodDingdan_New.DataBean> data, DingDanModel model, LoadingDialog loadingDialog, Callback callback) {
        super(context, data);
        this.model = model;
        this.loadingDialog = loadingDialog;
        this.callback = callback;
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (call != null && !call.isCanceled()) {
                    call.cancel();
                }
                return false;
            }
        });
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
            holder.tvbtnpaotui = (TextView) view.findViewById(R.id.itme_waimai_dingdan_listview_tvbtnpatui);
            holder.adapter_child = new FoodDingDanAdapter_New(getContext(),
                    getData().get(position).getGoodsinfo());
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

        setTextandListener(position, holder.tvbtnJIedan, holder.tvbtnpaotui);
        holder.adapter_child.notifyDataChanged(getData().get(position).getGoodsinfo());

        return view;
    }

    /**
     * 设置显示文字（订单的状态） 和设置按扭监听器
     * "pay_status": "1",       // 0未付款   1已付款
     * "        shipping_status":"0",   //0 未发货   1 已发货
     * "        order_status": "0",     //0 未接单  1 已接单
     */
    private void setTextandListener(final int position, TextView tvbtnJIedan, TextView tvbtnpaotui) {
        if (!TextUtils.equals("1", getData().get(position).getShipping_status())) {//未发货
            if (!TextUtils.equals("1", getData().get(position).getOrder_status())) {//未接单
                if (TextUtils.equals("1", getData().get(position).getPay_status())) {//已付款,待接单
                    tvbtnJIedan.setText(R.string.jiedan);
                    tvbtnpaotui.setVisibility(View.GONE);
                } else {//待付款
                    tvbtnJIedan.setText(R.string.daifukuan);
                    tvbtnpaotui.setVisibility(View.GONE);
                }
            } else {//已接单，代发货
                tvbtnJIedan.setText(R.string.daifahuo);
                if (TextUtils.equals("0", getData().get(position).getIs_send())) {//未发布跑腿
                    tvbtnpaotui.setVisibility(View.VISIBLE);
                } else {//已发布跑腿
                    tvbtnpaotui.setVisibility(View.GONE);
                }

            }
        } else {//已发货
            tvbtnJIedan.setText(R.string.yifahuo);
            tvbtnpaotui.setVisibility(View.GONE);
        }

        tvbtnJIedan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subMit((TextView) v, position);
            }
        });
        tvbtnpaotui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReleaseErrands(getData().get(position).getMobile(), getData().get(position).getOrder_amount()
                        , getData().get(position).getOrder_id());
            }
        });
    }

    /**
     * 发布跑腿
     */
    private void ReleaseErrands(String phone, String price, String orderid) {
        new Dialog_ReleaseErrands(getContext(), new Dialog_ReleaseErrands.Callback() {
            @Override
            public void onSuccess() {
                if (callback != null) {
                    callback.onRefresh();
                }
            }
        }, model, phone, price, orderid).show();
    }

    /**
     * 点击接单
     *
     * @param v
     * @param position
     */
    private void subMit(TextView v, int position) {
        String trim = v.getText().toString().trim();
        if (TextUtils.equals(getContext().getString(R.string.jiedan), trim)) {//接单按钮
            String order_id = getData().get(position).getOrder_id();
            loadingDialog.show();
            call = model.orDers(order_id, new IModel.AsyncCallBack() {
                @Override
                public void onSucceed(Object object) {
                    if (callback != null) {
                        callback.onRefresh();
                    }
                    loadingDialog.dismiss();
                }

                @Override
                public void onError(Object object) {
                    Too.oo(object);
                    loadingDialog.dismiss();
                }

                @Override
                public void onFailure(String string) {
                    Too.oo(string);
                    loadingDialog.dismiss();
                }
            });
        }
    }

    private class ViewHolder {
        ListView listView;
        TextView tvname, tvphone, tvprice, tvtime, tvbtnPhone, tvbtnJIedan, tvbtnpaotui;
        FoodDingDanAdapter_New adapter_child;
    }
}
