package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.LoginActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.MainActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.ShenqingjiluActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.activity.StorShenqinginfoActivity;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.Jilui;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.SpService;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class ActivityJiluListviewAdapter extends MyAdapter<Jilui> {
    public ActivityJiluListviewAdapter(Activity context, List<Jilui> data) {
        super(context, data);
    }

    private int status;

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        final Jilui.DataBean jilui = getData().get(position).getData();
        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.itme_activity_shenqingjilu_listview, null);
            holder = new ViewHolder();
            holder.ivimage = (ImageView) view.findViewById(R.id.itme_activity_shenqing_listview_ivimage);
            holder.tvbtn = (TextView) view.findViewById(R.id.itme_activity_shenqing_listview_tvwanshan);
            holder.tvtime = (TextView) view.findViewById(R.id.itme_activity_shenqing_listview_tvtime);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        LogUtils.d("申请记录店铺lo="+HttpUrl.IMAGE + jilui.getSlogo());
        x.image().bind(holder.ivimage, HttpUrl.IMAGE + jilui.getSlogo(),
                new ImageOptions.Builder().setSize(0, 0).setFadeIn(true).
                setFailureDrawableId(R.drawable.t2).setLoadingDrawableId(R.drawable.t2).build());
        holder.tvtime.setText(jilui.getTime());
        if (status == 0) {
            holder.tvbtn.setText(R.string.shenhezhong);
        } else if (status == 1) {
            holder.tvbtn.setText(R.string.jinru);
        } else if (status == 2) {
            holder.tvbtn.setText(R.string.xiugaixinxi);
        }

        holder.tvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == 1) {
                    if (!TextUtils.isEmpty(jilui.getStore_id())) {
                        SpService.getSP().saveStorId(jilui.getStore_id());
                        SpService.getSP().saveScid(jilui.getSc_id());
                    }
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(intent);
                    LoginActivity.instanse.finish();
                    if (getContext() instanceof Activity) {
                        ((ShenqingjiluActivity) getContext()).finish();
                    }
                } else if (status == 2) {
                    Intent intent = new Intent(getContext(), StorShenqinginfoActivity.class);
                    intent.putExtra("isxiugai", true);
                    getContext().startActivity(intent);
                }
            }
        });
        return view;
    }

    private class ViewHolder {
        ImageView ivimage;
        TextView tvtime, tvbtn;
    }
}
