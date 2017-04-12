package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.ui.SquareImageView;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/1.
 */

public class PingJiaAdapter extends MyAdapter<String> {
    public PingJiaAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        SquareImageView imageView;
        if (view == null) {
            imageView = new SquareImageView(getContext());
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = (parent.getWidth() - (DensityUtil.dip2px(24))) / 3;
                imageView.setLayoutParams(layoutParams);
            }
            view = imageView;
        }
        imageView = (SquareImageView) view;
        x.image().bind(imageView, HttpUrl.IMAGE + getData().get(position), new ImageOptions.Builder()
                .setFadeIn(true).setLoadingDrawableId(R.drawable.t2).setFailureDrawableId(R.drawable.t2).build());
        return view;
    }
}
