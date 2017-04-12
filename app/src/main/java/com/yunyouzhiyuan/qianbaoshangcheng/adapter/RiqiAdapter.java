package com.yunyouzhiyuan.qianbaoshangcheng.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.yunyouzhiyuan.qianbaoshangcheng.R;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.KTVList;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/23.
 */

public class RiqiAdapter extends MyAdapter<KTVList.DataBean.SpecItemListBean> {
    private Callback callback;

    public RiqiAdapter(Activity context, List<KTVList.DataBean.SpecItemListBean> data, Callback callback) {
        super(context, data);
        this.callback = callback;
    }

    public interface Callback {
        void onClick();

        void onClickclear();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CheckedTextView textView = (CheckedTextView) getLayoutInflater().inflate(R.layout.flowlayout_new_riqi, parent, false);

        String name = "";
        if (!TextUtils.isEmpty(getData().get(position).getItem())) {
            switch (getData().get(position).getItem()) {
                case "1":
                    name = "周一";
                    break;
                case "2":
                    name = "周二";
                    break;
                case "3":
                    name = "周三";
                    break;
                case "4":
                    name = "周四";
                    break;
                case "5":
                    name = "周五";
                    break;
                case "6":
                    name = "周六";
                    break;
                case "0":
                    name = "周日";
                    break;
            }
        }
        textView.setText(name);
        textView.setChecked(getData().get(position).isChecked());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getData().get(position).isChecked()) {
                    getData().get(position).setChecked(false);
                } else {
                    getData().get(position).setChecked(true);
                }
                boolean isChecked = false;
                for (int i = 0; i < getData().size(); i++) {
                    if (getData().get(i).isChecked()) {
                        isChecked = true;
                        break;
                    }
                }
                if (callback != null) {
                    if (isChecked) {
                        callback.onClick();
                    } else {
                        callback.onClickclear();
                    }
                }
                notifyDataSetChanged();
            }
        });
        return textView;
    }
}
