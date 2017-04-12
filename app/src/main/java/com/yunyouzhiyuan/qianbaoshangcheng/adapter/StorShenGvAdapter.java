package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.Buzhou;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetWinDowHeight;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/19.
 */
public class StorShenGvAdapter extends BaseAdapter {
    private Context context;
    private List<Buzhou.DataBean> list;

    public StorShenGvAdapter(Context context, List<Buzhou.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        Buzhou.DataBean dataBean = list.get(position);
        if(view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.itme_activity_stor_shenqing_gv,null);
            holder.iv = (ImageView) view.findViewById(R.id.itme_activity_stor_shenqing_gv_iv);
            holder.iv.setMaxHeight((int) (GetWinDowHeight.getScreenWeight(context)/2.5));
            holder.iv.setMinimumHeight((int) (GetWinDowHeight.getScreenWeight(context)/2.5-1));
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
       x.image().bind(holder.iv, HttpUrl.IMAGE+dataBean.getPath(),new ImageOptions.Builder().setSize(0,0).setLoadingDrawableId(R.drawable.t2).setFailureDrawableId(R.drawable.t2).setFadeIn(true).setImageScaleType(ImageView.ScaleType.FIT_XY).build());
        return view;
    }
    private class ViewHolder{
        ImageView iv;
    }
}
