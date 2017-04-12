package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/19.
 */
public class MyMessgareListvewAdapter extends MyAdapter<String> {
    public MyMessgareListvewAdapter(Activity context, List<String> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if(view == null){
            holder = new ViewHolder();
            view = getLayoutInflater().inflate(R.layout.itme_activity_my_msg,null);
            holder.tvmsg = (TextView) view.findViewById(R.id.itme_activity_myage_tvmsg);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();

        return view;
    }
    private class ViewHolder{
        TextView tvmsg;
    }
}
