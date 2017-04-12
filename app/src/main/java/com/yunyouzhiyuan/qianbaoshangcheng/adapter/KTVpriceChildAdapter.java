package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.KTVPrice;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.dialog.Dialog_addktvprice;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/30.
 */

public class KTVpriceChildAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<KTVPrice.DataBean.HouseBean.TimeBean> list;
    private Dialog_addktvprice dialog;
    private Context context;

    public KTVpriceChildAdapter(LayoutInflater inflater, List<KTVPrice.DataBean.HouseBean.TimeBean> list, Context context) {
        this.inflater = inflater;
        this.list = list;
        this.context = context;
    }

    public void notyData(List<KTVPrice.DataBean.HouseBean.TimeBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolderChild holderChild;
        if (view == null) {
            view = inflater.inflate(R.layout.ktvprice_list_child_listview, parent, false);
            holderChild = new ViewHolderChild(view);
            view.setTag(holderChild);
        }
        holderChild = (ViewHolderChild) view.getTag();
        holderChild.tvTime.setText(list.get(position).getTimedata() + "\t时间段：");
        holderChild.tvPrice.setText(list.get(position).getPrice());
        holderChild.tvPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setSelected(true);
                dialog = new Dialog_addktvprice(context, new Dialog_addktvprice.Callback() {
                    @Override
                    public void onClick(String name) {
                        ((TextView) v).setText(name);
                        list.get(position).setPrice(name);
                    }

                    @Override
                    public void onClickDismiss() {
                        v.setSelected(false);
                    }
                }, "请输入本时间段价格");
                dialog.show();
            }
        });
        holderChild.tvNumber.setText(list.get(position).getNumber());
        holderChild.tvNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setSelected(true);
                dialog = new Dialog_addktvprice(context, new Dialog_addktvprice.Callback() {
                    @Override
                    public void onClick(String name) {
                        ((TextView) v).setText(name);
                        list.get(position).setNumber(name);
                    }

                    @Override
                    public void onClickDismiss() {
                        v.setSelected(false);
                    }
                }, "请输入本时间段库存");
                dialog.show();
            }
        });
        return view;
    }

    private class ViewHolderChild {
        TextView tvTime;
        TextView tvPrice;
        TextView tvNumber;

        public ViewHolderChild(View view) {
            tvTime = (TextView) view.findViewById(R.id.ktvprice_list_child_tvtime);
            tvPrice = (TextView) view.findViewById(R.id.ktvprice_list_child_et);
            tvNumber = (TextView) view.findViewById(R.id.ktvprice_list_child_tvnumber);
        }
    }
}
