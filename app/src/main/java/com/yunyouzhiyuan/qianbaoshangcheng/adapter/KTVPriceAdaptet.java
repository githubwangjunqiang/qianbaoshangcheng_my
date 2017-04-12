package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.KTVPrice;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.MyListview;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/30.
 */

public class KTVPriceAdaptet extends BaseExpandableListAdapter {
    private List<KTVPrice.DataBean> list;
    private Context context;
    private LayoutInflater inflater;

    public KTVPriceAdaptet(List<KTVPrice.DataBean> list, Context context) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getHouse().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getHouse().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        ViewHolderChild holderChild;
        if (view == null) {
            holderChild = new ViewHolderChild();
            view = inflater.inflate(R.layout.ktvprice_list_group, null);
            holderChild.tvName = (TextView) view.findViewById(R.id.ktvpricelist_grounp_tvname);
            view.setTag(holderChild);
        }
        holderChild = (ViewHolderChild) view.getTag();
        switch (list.get(groupPosition).getWeek()) {
            case "0":
                holderChild.tvName.setText("周日");
                break;
            case "1":
                holderChild.tvName.setText("周一");
                break;
            case "2":
                holderChild.tvName.setText("周二");
                break;
            case "3":
                holderChild.tvName.setText("周三");
                break;
            case "4":
                holderChild.tvName.setText("周四");
                break;
            case "5":
                holderChild.tvName.setText("周五");
                break;
            case "6":
                holderChild.tvName.setText("周六");
                break;
        }
        holderChild.tvName.setSelected(isExpanded);
        return view;
    }

    private class ViewHolderChild {
        TextView tvName;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
                             ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.ktvprice_list_child, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        holder.listview.setPressed(false);
        holder.listview.setFocusable(false);
        holder.listview.setEnabled(false);
        holder.tvFangxing.setText(list.get(groupPosition).getHouse().get(childPosition).getName());
        holder.adapter = new KTVpriceChildAdapter(inflater, list.get(groupPosition).getHouse().get(childPosition).getTime()
                , context);
        holder.listview.setAdapter(holder.adapter);

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class ViewHolder {
        TextView tvFangxing;
        MyListview listview;
        KTVpriceChildAdapter adapter;

        public ViewHolder(View view) {
            tvFangxing = (TextView) view.findViewById(R.id.ktvprice_list_child_tvfangxing);
            listview = (MyListview) view.findViewById(R.id.ktvprice_list_child_listview);
        }
    }
}
